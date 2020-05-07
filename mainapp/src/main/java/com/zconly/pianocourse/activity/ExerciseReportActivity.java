package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.mvp.presenter.BasePresenter;
import com.zconly.pianocourse.mvp.presenter.ExercisePresenter;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.AbstractDownloadView;
import com.zconly.pianocourse.mvp.view.DownloadView;
import com.zconly.pianocourse.mvp.view.ExerciseView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.FileUtils;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.Logger;
import com.zconly.pianocourse.util.SysConfigTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * @Description: 练习测评报告
 * @Author: dengbin
 * @CreateDate: 2020/5/4 13:40
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/4 13:40
 * @UpdateRemark: 更新说明
 */
public class ExerciseReportActivity extends BaseMvpActivity<ExercisePresenter> implements ExerciseView {

    private static final int MEDIA_TYPE_EXERCISE = 1;
    private static final int MEDIA_TYPE_VOICE = 2;

    @BindView(R.id.exercise_report_sc)
    ScrollView mScrollView;
    @BindView(R.id.exercise_avatar_iv)
    ImageView avatarIv;
    @BindView(R.id.exercise_report_nickname_tv)
    TextView nicknameTv;
    @BindView(R.id.exercise_report_user_desc_tv)
    TextView userDescTv;
    @BindView(R.id.exercise_report_name_tv)
    TextView nameTv;
    @BindView(R.id.exercise_play_name_tv)
    TextView nameTv2;
    @BindView(R.id.exercise_report_author_tv)
    TextView authorTv;
    @BindView(R.id.exercise_play_author_tv)
    TextView authorTv2;
    @BindView(R.id.exercise_report_desc_tv)
    TextView descTv;
    @BindView(R.id.exercise_report_time_tv)
    TextView timeTv;
    @BindView(R.id.exercise_report_play_iv)
    ImageView playIv;
    @BindView(R.id.exercise_report_sb)
    SeekBar seekBar;
    @BindView(R.id.exercise_report_percent_tv)
    TextView percentTv;

    @BindView(R.id.exercise_report_sheet_iv)
    ImageView sheetIv;

    @BindView(R.id.exercise_report_comment_title_iv)
    TextView commentTitleIv;
    @BindView(R.id.exercise_report_comment_iv)
    ImageView commentIv;
    @BindView(R.id.exercise_report_teacher_title_tv)
    TextView teacherTitleTv;
    @BindView(R.id.exercise_report_teacher_iv)
    ImageView teacherIv;

    @BindView(R.id.exercise_report_voice_rl)
    RelativeLayout voiceRl;
    @BindView(R.id.voice_play_iv)
    ImageView voicePlayIv;
    @BindView(R.id.voice_teacher_iv)
    TextView voiceTeacherTV;
    @BindView(R.id.voice_playing_iv)
    ImageView voicePlayingIv;
    @BindView(R.id.voice_no_read_tv)
    ImageView voiceNoReadIv;
    @BindView(R.id.voice_time_tv)
    TextView voiceTimeTv;

    @BindView(R.id.exercise_report_header_ll)
    LinearLayout scoreLl;
    @BindView(R.id.score_tv_1)
    TextView scoreTv1;
    @BindView(R.id.score_tv_4)
    TextView scoreTv4;
    @BindView(R.id.score_tv_5)
    TextView scoreTv5;
    @BindView(R.id.score_tv_6)
    TextView scoreTv6;

    private ExerciseBean bean;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private int duration; // 练习声音时长
    private int durationVoice; // 老师评价声音时长
    private File mp3File;
    private boolean isSeekBarChanging;
    private File voiceFile;
    private int mediaPlayerFileType;
    private String voiceMp3Url;

    public static void start(Context context, ExerciseBean bean) {
        if (!SysConfigTool.isLogin(context, true))
            return;
        Intent intent = new Intent(context, ExerciseReportActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, bean);
        context.startActivity(intent);
    }

    private void setTime(int time) {
        percentTv.setText(time + "/" + duration);
        seekBar.setProgress(time);
    }

    public int initMediaPlayer(int type, File file) {
        if (file == null)
            return 0;
        if (mediaPlayer == null) {
            MediaPlayer.OnCompletionListener onCompletionListener = mp -> {
                playIv.setSelected(false);
                voicePlayIv.setSelected(false);
                voicePlayingIv.clearAnimation();
            };
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setLooping(false);//设置为循环播放
                mediaPlayer.setOnCompletionListener(onCompletionListener);
            } catch (Exception e) {
                Logger.w(e);
            }
        }

        if (mediaPlayerFileType == type)
            return 0;
        mediaPlayerFileType = type;

        try {
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            mediaPlayer.prepare();//初始化播放器MediaPlayer
            return mediaPlayer.getDuration();
        } catch (Exception e) {
            Logger.w(e);
        }
        return 0;
    }

    // 如果没在播放中，立刻开始播放。
    public void playMediaPlayer() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            if (mediaPlayerFileType == MEDIA_TYPE_VOICE) {
                releaseTimer();
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale);
                animation.setRepeatCount(4096);
                voicePlayingIv.startAnimation(animation);
            } else {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (seekBar == null)
                            return;
                        seekBar.post(() -> {
                            if (!isSeekBarChanging)
                                setTime(mediaPlayer.getCurrentPosition() / 1000);
                        });
                    }
                }, 500, 500);
            }
        }
    }

    // 如果在播放中，立刻暂停。
    public void pauseMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            voicePlayingIv.clearAnimation();
            mediaPlayer.pause();
        }
    }

    public void stopMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            voicePlayingIv.clearAnimation();
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void seekTo(int time) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(time * 1000);
        }
    }

    private void play() {
        if (mediaPlayer.isPlaying()) {
            pauseMediaPlayer();
        } else {
            playMediaPlayer();
        }
    }

    private void releaseTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    @OnClick({R.id.exercise_report_play_iv, R.id.exercise_cuoyin_tv, R.id.exercise_cuojiezou_tv,
            R.id.voice_play_iv, R.id.voice_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_report_play_iv: // 播放
                initMediaPlayer(MEDIA_TYPE_EXERCISE, mp3File);
                play();
                playIv.setSelected(!playIv.isSelected());
                break;
            case R.id.exercise_cuoyin_tv:

                break;
            case R.id.exercise_cuojiezou_tv:

                break;
            case R.id.voice_play_iv:
            case R.id.voice_bg:
                playVoice();
                voiceNoReadIv.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void playVoice() {
        if (TextUtils.isEmpty(voiceMp3Url))
            return;
        if (voiceFile == null) {
            new BasePresenter<DownloadView>(new AbstractDownloadView() {

                @Override
                public void downloadSuccess() {

                }

                @Override
                public ResponseBody download(ResponseBody responseBody) {
                    voiceFile = new File(FileUtils.getCacheDir(mContext).getAbsolutePath()
                            + File.separator + voiceMp3Url);
                    FileUtils.saveFile(responseBody, voiceFile);
                    doPlayVoice();
                    return responseBody;
                }
            }).downloadFile(H5Service.FILE_HOST, voiceMp3Url);
        } else {
            doPlayVoice();
        }
    }

    private void doPlayVoice() {
        initMediaPlayer(MEDIA_TYPE_VOICE, voiceFile);
        play();
    }

    @Override
    protected boolean initView() {
        bean = (ExerciseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        if (bean == null) {
            finish();
            return false;
        }

        mTitleView.setTitle("胤·悦学院测评报告");
        SheetBean sb = bean.getSheet();
        UserBean ub = bean.getUser();

        ImgLoader.showAvatar(DataUtil.getImgUrl(ub.getAvatar()), avatarIv);
        nicknameTv.setText(ub.getNickname());

        userDescTv.setText(DateUtils.getYearToNow(ub.getBirthday()) + "岁 学琴"
                + DateUtils.getYearToNow(ub.getDuration()) + "年");

        nameTv.setText(sb.getName());
        nameTv2.setText(sb.getName());
        authorTv.setText(sb.getAuthor());
        authorTv2.setText(sb.getAuthor());

        descTv.setText("曲目等级:" + DataUtil.getSheetLevel(sb.getLevel()) + "    演奏速度:" + bean.getBpm() + "    "
                + DataUtil.getStaffStr(bean.getStaff()));

        timeTv.setText("演奏时间:" + DateUtils.getTime2M(bean.getC_time()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;
                setTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarChanging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekBarChanging = false;
                seekTo(seekBar.getProgress());
                setTime(seekBar.getProgress());
            }
        });
        return true;
    }

    @Override
    protected void initData() {
        mPresenter.getEvaluateList(0, 1, bean.getId());
        mPresenter.downloadFile(H5Service.MP3_HOST, bean.getPath());
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_exercise_report;
    }

    @Override
    protected ExercisePresenter getPresenter() {
        return new ExercisePresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        releaseTimer();
        stopMediaPlayer();
        super.onDestroy();
    }

    @Override
    public void uploadExerciseSuccess(ExerciseBean.ExerciseResult response) {

    }

    @Override
    public void favoriteSuccess(BaseBean response) {

    }

    @Override
    public void downloadSuccess() {

    }

    @Override
    public void getEvaluateListSuccess(EvaluateBean.EvaluateListResult response) {
        if (response.getData() == null || ArrayUtil.isEmpty(response.getData().getData()))
            return;
        EvaluateBean evaluateBean = response.getData().getData().get(0);
        String annotationBean = evaluateBean.getAnnotation();
        JSONObject jo;
        String comment = null;
        String sheet = null;
        try {
            jo = new JSONObject(annotationBean);
            comment = jo.getString("comment");
            sheet = jo.getString("sheet");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sheetIv.setVisibility(View.VISIBLE);
        ImgLoader.showImg(DataUtil.getImgUrl(sheet), sheetIv);

        commentTitleIv.setVisibility(View.VISIBLE);
        commentIv.setVisibility(View.VISIBLE);
        ImgLoader.showImg(DataUtil.getImgUrl(comment), commentIv);

        teacherTitleTv.setVisibility(View.VISIBLE);
        teacherIv.setVisibility(View.VISIBLE);

        scoreLl.setVisibility(View.VISIBLE);
        scoreTv1.setText(evaluateBean.getScore1() + "");
        scoreTv4.setText(evaluateBean.getScore4() + "");
        scoreTv5.setText(evaluateBean.getScore5() + "");
        scoreTv6.setText(evaluateBean.getScore6() + "");

        if (!TextUtils.isEmpty(evaluateBean.getVoice())) {
            voiceRl.setVisibility(View.VISIBLE);
            voiceTimeTv.setText(evaluateBean.getDuration() / 1000 + "s");
            voiceMp3Url = evaluateBean.getVoice();
            voiceTeacherTV.setText("");
        }
    }

    @Override
    public ResponseBody download(ResponseBody responseBody) {
        mp3File = new File(FileUtils.getCacheDir(mContext).getAbsolutePath() + "/" + bean.getPath());
        FileUtils.saveFile(responseBody, mp3File);

        duration = initMediaPlayer(MEDIA_TYPE_EXERCISE, mp3File);
        duration /= 1000;
        seekBar.setMax(duration);
        setTime(0);
        return responseBody;
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void uploadSuccess(FileBean response) {

    }
}
