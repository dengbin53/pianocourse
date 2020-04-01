package com.baidu.cloud.videoplayer.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.cloud.media.player.IMediaPlayer;
import com.baidu.cloud.videoplayer.R;
import com.baidu.cloud.videoplayer.widget.BDCloudVideoView;
import com.baidu.cloud.videoplayer.widget.IRenderView;

public class VideoPlayerView extends RelativeLayout implements IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnSeekCompleteListener {

    // 您的ak
    public static String AK = "958ec87f47f8455281435033c76eacfe";

    private static final int GESTURE_MODIFY_PROGRESS = 1;
    private static final int GESTURE_MODIFY_VOLUME = 2;
    private static final float STEP_PROGRESS = 3f;// 设定进度滑动时的步长，避免每次滑动都改变，导致改变过快
    private static final float STEP_VOLUME = 3f;// 协调音量滑动时的步长，避免每次滑动都改变，导致改变过快
    private static final String POWER_LOCK = "VideoPlayerView";

    private String mVideoSource;
    private BDCloudVideoView mVV;
    private BDMediaController controller;
    private RelativeLayout mViewHolder;
    private PowerManager.WakeLock mWakeLock;
    private final Object SYNC_Playing = new Object();
    private boolean firstScroll;// 每次触摸屏幕后，第一次scroll的标志
    private int GESTURE_FLAG = 0;// 1,调节进度，2，调节音量

    // 视频播放时间,视频播放的总时长
    private int playingTime, videoTotalTime;

    private VerticalSeekBar seekbar_volume;
    private LinearLayout ll_volume;
    private View backView;

    private int currentVolume;
    private int maxVolume;
    private AudioManager audioManager;
    private SettingsContentObserver mSettingsContentObserver;

    private boolean isFullScreen;

    // 播放状态
    private enum PLAYER_STATUS {
        PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
    }

    private VideoPlayerView.PLAYER_STATUS mPlayerStatus = VideoPlayerView.PLAYER_STATUS.PLAYER_IDLE;

    private GestureDetector gesture;

    private RelativeLayout rl_video_status;
    private TextView tv_video_status;
    private ImageView img_video_status;

    //    private ImageView cover;
    public VideoPlayerView(Context context) {
        this(context, null);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("InvalidWakeLockTag")
    private void init(Context context) {
        inflate(context, R.layout.view_video_player, this);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);
        initUI();
        // startPlay();
        // cover = (ImageView) findViewById(R.id.video_cover);
        View v = findViewById(R.id.root);
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        lp.height = Utils.getScreenWidth(getContext()) * 3 / 4;
    }

    public void startPlay(Uri uriPath) {
        if (null != uriPath) {
            String scheme = uriPath.getScheme();
            if (null != scheme) {
                mVideoSource = uriPath.toString();
            } else {
                mVideoSource = uriPath.getPath();
            }
        }
        mVV.setVideoPath(mVideoSource);
        mVV.showCacheInfo(true);
        mVV.start();
        mPlayerStatus = VideoPlayerView.PLAYER_STATUS.PLAYER_PREPARING;
    }

    public void setVideoCover(String url) {
        // ImgLoader.disPlayImage(url, cover, R.drawable.img_bg);
    }

    public void setDownLoadState(int downLoadState) {
        if (controller != null) {
            controller.initDownLoadState(downLoadState);
        }
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    /**
     * 初始化界面
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private void initUI() {
        this.audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        this.maxVolume = this.audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        this.currentVolume = this.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        backView = findViewById(R.id.back);
        backView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        ll_volume = findViewById(R.id.ll_volume);
        seekbar_volume = findViewById(R.id.seekbar_volume);
        seekbar_volume.setMax(maxVolume);
        seekbar_volume.setVProgress(currentVolume);
        seekbar_volume.setOnSeekBarChangeListener(seekBarChangeListener);

        gesture = new GestureDetector(getContext(), gestureListener);

        mViewHolder = findViewById(R.id.view_holder);

        BDCloudVideoView.setAK(AK);

        // 创建BVideoView和BMediaController
        mVV = new BDCloudVideoView(getContext());
        controller = findViewById(R.id.controller);
        controller.init();
        controller.setVideoView(mVV);
        controller.setSeekProgressChangedListener(new BDMediaController.OnSeekProgressChangedListener() {
            @Override
            public void onSeekProgressChanged(int progress, boolean isFastForward) {
                if (rl_video_status.getVisibility() != VISIBLE) {
                    rl_video_status.setVisibility(VISIBLE);
                }
                if (isFastForward) {
                    img_video_status.setImageResource(R.drawable.icon_video_fast_forward);
                } else {
                    img_video_status.setImageResource(R.drawable.icon_video_rewind);
                }
                tv_video_status.setText(format(progress / 1000) + "/" + format(mVV.getDuration() / 1000));
            }
        });
        mViewHolder.addView(mVV);
        mVV.setOnTouchListener(touchListener);

        // 注册listener
        mVV.setOnPreparedListener(this);
        mVV.setOnCompletionListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnInfoListener(this);
        mVV.setOnSeekCompleteListener(this);

        mVV.setVideoScalingMode(IRenderView.AR_16_9_FIT_PARENT);

        rl_video_status = findViewById(R.id.rl_video_status);
        tv_video_status = findViewById(R.id.tv_video_status);
        img_video_status = findViewById(R.id.img_video_status);

    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            firstScroll = true;
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("VideoPlayerView", "onSingleTapUp");
            if (mPlayerStatus != VideoPlayerView.PLAYER_STATUS.PLAYER_PREPARED) return true;
            if (controller.getVisibility() == VISIBLE) {
                controller.setVisibility(View.GONE);
                ll_volume.setVisibility(View.GONE);
                backView.setVisibility(View.GONE);
            } else {
                controller.setVisibility(VISIBLE);
                if (isFullScreen) {
                    ll_volume.setVisibility(VISIBLE);
                    backView.setVisibility(VISIBLE);
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int y = (int) e2.getRawY();
            Log.d("VideoPlayerView", "onScroll");
            if (firstScroll) {// 以触摸屏幕后第一次滑动为标准，避免在屏幕上操作切换混乱
                // 横向的距离变化大则调整进度，纵向的变化大则调整音量
                if (Math.abs(distanceX) >= Math.abs(distanceY)) {
                    GESTURE_FLAG = GESTURE_MODIFY_PROGRESS;
                } else {
                    GESTURE_FLAG = GESTURE_MODIFY_VOLUME;
                }
            }
            // 如果每次触摸屏幕后第一次scroll是调节进度，那之后的scroll事件都处理音量进度，直到离开屏幕执行下一次操作
            if (GESTURE_FLAG == GESTURE_MODIFY_PROGRESS) {
                // distanceX=lastScrollPositionX-currentScrollPositionX，因此为正时是快进
                if (Math.abs(distanceX) > Math.abs(distanceY)) {// 横向移动大于纵向移动
                    if (distanceX >= Utils.dp2px(getContext(), STEP_PROGRESS)) {// 快退，用步长控制改变速度，可微调
                        if (playingTime > 3) {// 避免为负
                            playingTime -= 3;// scroll方法执行一次快退3秒
                        } else {
                            playingTime = 0;
                        }
                        if (rl_video_status.getVisibility() != VISIBLE) {
                            rl_video_status.setVisibility(VISIBLE);
                        }
                        img_video_status.setImageResource(R.drawable.icon_video_rewind);
                        tv_video_status.setText(format(playingTime) + "/" + format(mVV.getDuration() / 1000));
                    } else if (distanceX <= -Utils.dp2px(getContext(), STEP_PROGRESS)) {// 快进
                        if (playingTime < videoTotalTime - 16) {// 避免超过总时长
                            playingTime += 3;// scroll执行一次快进3秒
                        } else {
                            playingTime = videoTotalTime - 10;
                        }
                        if (rl_video_status.getVisibility() != VISIBLE) {
                            rl_video_status.setVisibility(VISIBLE);
                        }
                        img_video_status.setImageResource(R.drawable.icon_video_fast_forward);
                        tv_video_status.setText(format(playingTime) + "/" + format(mVV.getDuration() / 1000));
                    }
                    if (playingTime < 0) {
                        playingTime = 0;
                    }
                    controller.setProgress(playingTime * 1000);
                }
            }
            // 如果每次触摸屏幕后第一次scroll是调节音量，那之后的scroll事件都处理音量调节，直到离开屏幕执行下一次操作
            else if (GESTURE_FLAG == GESTURE_MODIFY_VOLUME) {
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前值
                if (Math.abs(distanceY) > Math.abs(distanceX)) {// 纵向移动大于横向移动
                    if (distanceY >= Utils.dp2px(getContext(), STEP_VOLUME)) {
                        // 音量调大,注意横屏时的坐标体系,尽管左上角是原点，但横向向上滑动时distanceY为正
                        if (currentVolume < maxVolume) {// 为避免调节过快，distanceY应大于一个设定值
                            currentVolume++;
                        }
                    } else if (distanceY <= -Utils.dp2px(getContext(), STEP_VOLUME)) { // 音量调小
                        if (currentVolume > 0) {
                            currentVolume--;
                            if (currentVolume == 0) { // 静音，设定静音独有的图片
//								gesture_iv_player_volume.setImageResource(R.drawable.souhu_player_silence);
                            }
                        }
                    }
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
                    seekbar_volume.setVProgress(currentVolume);
                }
            }

            firstScroll = false; // 第一次scroll执行完成，修改标志
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };

    private OnTouchListener touchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d("VideoPlayerView", "onTouch" + event.getAction());
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                rl_video_status.setVisibility(View.INVISIBLE);
            }
            // 手势里除了singleTapUp，没有其他检测up的方法
            if (event.getAction() == MotionEvent.ACTION_UP) {
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                if (GESTURE_FLAG == GESTURE_MODIFY_PROGRESS) {
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    mVV.seekTo(playingTime * 1000);
                }
                GESTURE_FLAG = 0;// 手指离开屏幕后，重置调节音量或进度的标志
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                playingTime = mVV.getCurrentPosition() / 1000;
                videoTotalTime = mVV.getDuration() / 1000;
            }
            gesture.onTouchEvent(event);
            return true;
        }
    };

    /**
     * 播放出错
     */
    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = VideoPlayerView.PLAYER_STATUS.PLAYER_IDLE;
        return true;
    }

    /**
     * 播放完成
     */
    @Override
    public void onCompletion(IMediaPlayer mp) {
        Log.d("VideoPlayerView", "onCompletion");
        rl_video_status.setVisibility(View.INVISIBLE);
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = VideoPlayerView.PLAYER_STATUS.PLAYER_PREPARED;

        controller.finish();
        controller.stopUpdateProgress();
        controller.reset();
        // 播放完成关闭页面
        //finish();
    }

    /**
     * 播放准备就绪
     */
    @Override
    public void onPrepared(IMediaPlayer mp) {
        Log.d("VideoPlayerView", "onPrepared");
        rl_video_status.setVisibility(View.INVISIBLE);
        mPlayerStatus = VideoPlayerView.PLAYER_STATUS.PLAYER_PREPARED;

        controller.post(new Runnable() {
            @Override
            public void run() {
                controller.setVideoPrepared(true);
                controller.setMax(mVV.getDuration());
                controller.setProgress(mVV.getCurrentPosition());
                controller.startUpdateProgress();
            }
        });
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        Log.d("VideoPlayerView", "onInfo");
        return false;
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        Log.d("VideoPlayerView", "onSeekComplete");
        rl_video_status.setVisibility(INVISIBLE);
        controller.updatePlayController(false);
    }

    private String format(int var1) {
        int var2 = var1 / 3600;
        int var3 = var1 % 3600 / 60;
        int var4 = var1 % 60;
        String var5 = null;
        if (0 != var2) {
            var5 = String.format("%02d:%02d:%02d", Integer.valueOf(var2), Integer.valueOf(var3), Integer.valueOf(var4));
        } else {
            var5 = String.format("%02d:%02d", Integer.valueOf(var3), Integer.valueOf(var4));
        }
        return var5;
    }

    public void onActivityDestroy() {
        try {
            unregisterVolumeChangeReceiver();
            controller.stopUpdateProgress();
            mVV.stopPlayback();
            mVV.release();
        } catch (Exception e) {

        }
    }

    //    @Override
//    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
//        super.onVisibilityChanged(changedView, visibility);
//        try {
//            if (visibility!=VISIBLE){
//                controller.updatePlayController(true);
//                mVV.pause();
//            }else {
//                if (null != mWakeLock && (!mWakeLock.isHeld())) {
//                    mWakeLock.acquire();
//                }
//            }
//        }catch (Exception e){
//
//        }
//    }

    public void onPause() {
        try {
            if (mPlayerStatus == VideoPlayerView.PLAYER_STATUS.PLAYER_PREPARED) {
                controller.updatePlayController(true);
                mVV.pause();
            }
        } catch (Exception e) {

        }
    }

    public void onResume() {
        try {
            if (null != mWakeLock && (!mWakeLock.isHeld())) {
                mWakeLock.acquire();
            }
        } catch (Exception e) {

        }
    }

    /**
     * @param isFullScreen true-全屏 false-窗口
     */
    public void setFullScreenModel(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        controller.updateScreenModel(isFullScreen);
        View v = findViewById(R.id.root);
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        if (isFullScreen) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            lp.width = metrics.widthPixels;
            lp.height = metrics.heightPixels;
            backView.setVisibility(VISIBLE);
            ll_volume.setVisibility(VISIBLE);
        } else {
            lp.width = Utils.getScreenWidth(getContext());
            lp.height = Utils.getScreenWidth(getContext()) * 3 / 4;
            backView.setVisibility(INVISIBLE);
            ll_volume.setVisibility(INVISIBLE);
        }
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.d("VideoPlayerView", "onProgressChanged" + "progress" + progress + "fromUser" + fromUser);
            audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC, progress, 0);
            currentVolume = progress;
            seekbar_volume.setVProgress(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.d("VideoPlayerView", "onStartTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d("VideoPlayerView", "onStopTrackingTouch");
        }
    };

    public void registerVolumeChangeReceiver() {
        mSettingsContentObserver = new SettingsContentObserver(getContext(), new Handler());
        getContext().getApplicationContext().getContentResolver().registerContentObserver(
                android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver);
    }

    public void unregisterVolumeChangeReceiver() {
        getContext().getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
    }

    public class SettingsContentObserver extends ContentObserver {
        Context context;

        public SettingsContentObserver(Context c, Handler handler) {
            super(handler);
            context = c;
        }

        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("VideoPlayerView", "SettingsContentObserver--onChange" + selfChange);
            int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (currentVolume != volume) {
                currentVolume = volume;
                seekbar_volume.setVProgress(currentVolume);
                seekbar_volume.requestLayout();
            }
        }
    }
}
