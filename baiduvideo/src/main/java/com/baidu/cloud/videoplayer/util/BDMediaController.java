package com.baidu.cloud.videoplayer.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.cloud.videoplayer.R;
import com.baidu.cloud.videoplayer.widget.BDCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BDMediaController extends FrameLayout {
    private BDCloudVideoView videoView;
    private ImageView playController;
    private ImageView img_video_full_screen;
    private TextView currentPosition;
    private TextView totalLength;
    private SeekBar playSeekBar;
    private RelativeLayout rl_model_small_screen;//窗口模式
    private RelativeLayout rl_model_full_screen;//全屏模式
    private TextView full_currentPosition;
    private TextView full_totalLength;
    private TextView full_tv_video_load;
    private SeekBar full_playSeekBar;

    private boolean isPrepared = false;
    private Handler updateProgressHandler = new Handler();
    private Context context;

    public BDMediaController(Context context) {
        this(context, null);
    }

    public BDMediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BDMediaController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public void init() {
        if (null == context)
            return;
        LayoutInflater.from(context).inflate(R.layout.layout_media_controller, this);
        img_video_full_screen = findViewById(R.id.img_video_full_screen);
        img_video_full_screen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rl_model_full_screen.getVisibility() == VISIBLE) {
                    ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
        playController = findViewById(R.id.play_controller_btn);
        currentPosition = findViewById(R.id.current_position);
        totalLength = findViewById(R.id.total_length);
        playSeekBar = findViewById(R.id.play_seekbar);
        playSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        playController.setOnClickListener(playClickListener);
        rl_model_small_screen = findViewById(R.id.rl_model_small_screen);

        //全屏
        full_tv_video_load = findViewById(R.id.full_tv_video_load);
        rl_model_full_screen = findViewById(R.id.rl_model_full_screen);
        full_currentPosition = findViewById(R.id.full_current_position);
        full_totalLength = findViewById(R.id.full_total_length);
        full_playSeekBar = findViewById(R.id.full_play_seekbar);
        full_playSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    public void updatePlayController(boolean pause) {
        if (videoView != null) {
            if (pause) {
                playController.setImageResource(R.drawable.bdvideoview_play);
            } else {
                playController.setImageResource(R.drawable.bdvideoview_pause);

            }
        }
    }

    private OnClickListener playClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (videoView != null && isPrepared) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    playController.setImageResource(R.drawable.bdvideoview_play);
                } else {
                    videoView.start();
                    playController.setImageResource(R.drawable.bdvideoview_pause);
                }
            }
        }
    };

    public void reset() {
        playController.setImageResource(R.drawable.bdvideoview_play);
    }

    public void finish() {
        setProgress(videoView.getDuration());
    }

    public void setVideoView(BDCloudVideoView videoView) {
        this.videoView = videoView;
    }

    public void setVideoPrepared(boolean prepared) {
        this.isPrepared = prepared;
        playController.setImageResource(R.drawable.bdvideoview_pause);
    }

    public void setMax(int maxProgress) {
        if (playSeekBar != null) {
            playSeekBar.setMax(maxProgress);
        }
        totalLength.setText(format(maxProgress / 1000));
        if (full_playSeekBar != null) {
            full_playSeekBar.setMax(maxProgress);
        }
        full_totalLength.setText(format(maxProgress / 1000));
    }

    public void setProgress(int progress) {
        if (playSeekBar != null && progress != playSeekBar.getProgress()) {
            playSeekBar.setProgress(progress);
        }
        currentPosition.setText(format(progress / 1000));
        if (full_playSeekBar != null && progress != full_playSeekBar.getProgress()) {
            full_playSeekBar.setProgress(progress);
        }
        full_currentPosition.setText(format(progress / 1000));
    }

    public void setCache(int cache) {
        if (playSeekBar != null && cache != playSeekBar.getSecondaryProgress()) {
            playSeekBar.setSecondaryProgress(cache);
        }
        if (full_playSeekBar != null && cache != full_playSeekBar.getSecondaryProgress()) {
            full_playSeekBar.setSecondaryProgress(cache);
        }
    }

    public void startUpdateProgress() {
        updateProgress();
    }

    public void stopUpdateProgress() {
        updateProgressHandler.removeCallbacks(UpdateProgressRunnable);
    }

    private void updateProgress() {
        updateProgressHandler.postDelayed(UpdateProgressRunnable, 1000);
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

    public void setSeekProgressChangedListener(OnSeekProgressChangedListener seekProgressChangedListener) {
        this.seekProgressChangedListener = seekProgressChangedListener;
    }

    private OnSeekProgressChangedListener seekProgressChangedListener;

    public interface OnSeekProgressChangedListener {
        void onSeekProgressChanged(int progress, boolean isFastForward);
    }

    private int mProgress = 0;

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                if (null != seekProgressChangedListener) {
                    if (mProgress < progress) {
                        seekProgressChangedListener.onSeekProgressChanged(progress, true);
                    } else {
                        seekProgressChangedListener.onSeekProgressChanged(progress, false);
                    }
                    mProgress = progress;
                }
                setProgress(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.d("VideoPlayerView", "-----onStartTrackingTouch");
            stopUpdateProgress();
            videoView.pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d("VideoPlayerView", "-----onStartTrackingTouch");
            if (videoView != null) {
                int progress = seekBar.getProgress();
                videoView.seekTo(progress);
                videoView.start();
                startUpdateProgress();

            }
        }
    };

    public void startVideo(boolean play) {
        if (videoView == null) return;
        videoView.start();
    }

    Runnable UpdateProgressRunnable = new Runnable() {
        public void run() {
            Log.d("VideoPlayerView", "-----UpdateProgressRunnable" + videoView.getCurrentPosition());
            setProgress(videoView.getCurrentPosition());
            updateProgress();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DownloadListStateChangeEvent event) {
        if (event != null && "DownLoadWithHintChage".equals(event.flag) && full_tv_video_load != null) {
            setDownloadStateGb(full_tv_video_load, event.isMsbRedBg);
            full_tv_video_load.setText(event.hintName);
        }
    }

    /**
     * @param view 设置背景空间
     * @param b    是否是美术宝红
     */
    public void setDownloadStateGb(TextView view, boolean b) {
        if (b) {
            view.setTextColor(Color.parseColor("#FF5722"));
            view.setBackgroundResource(R.drawable.shape_roundcorner_red);
        } else {
            view.setTextColor(Color.parseColor("#666666"));
            view.setBackgroundResource(R.drawable.shape_roundcorner_gray);
        }
    }

    public void initDownLoadState(int downLoadState) {
        if (full_tv_video_load == null) return;
        //0、1、2、3、4表示缓存中状态 100表示初始下载值
        if (downLoadState == 3 || downLoadState == 4) {
            setDownloadStateGb(full_tv_video_load, false);
            full_tv_video_load.setText("已下载");
        } else if (downLoadState == 0) {
            setDownloadStateGb(full_tv_video_load, true);
            full_tv_video_load.setText("已暂停下载");
        } else if (downLoadState == 1) {
            setDownloadStateGb(full_tv_video_load, true);
            full_tv_video_load.setText("下载中");
        } else if (downLoadState == 2) {
            setDownloadStateGb(full_tv_video_load, true);
            full_tv_video_load.setText("下载失败");
        }
        if (downLoadState == 100) {
            setDownloadStateGb(full_tv_video_load, true);
            full_tv_video_load.setText("下载");
        }
        full_tv_video_load.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DownloadListStateChangeEvent("PlayVideoStartDownLoad"));
            }
        });
    }

    public void updateScreenModel(boolean isFullScreen) {
        if (isFullScreen) {
            rl_model_full_screen.setVisibility(VISIBLE);
            rl_model_small_screen.setVisibility(INVISIBLE);
            img_video_full_screen.setImageResource(R.drawable.icon_vedio_exit_full_screen);
        } else {
            rl_model_full_screen.setVisibility(INVISIBLE);
            rl_model_small_screen.setVisibility(VISIBLE);
            img_video_full_screen.setImageResource(R.drawable.icon_vedio_full_screen);
        }
    }

}
