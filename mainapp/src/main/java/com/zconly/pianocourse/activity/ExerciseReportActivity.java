package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.mvp.presenter.ExercisePresenter;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.ExerciseView;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.FileUtils;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.SysConfigTool;

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

    private ExerciseBean bean;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private int duration;
    private File mp3File;
    private boolean isSeekbarChaning;

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

    public int initMediaPlayer(File file) {
        if (mp3File == null || mediaPlayer != null)
            return 0;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            mediaPlayer.setLooping(false);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer
            return mediaPlayer.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //如果没在播放中，立刻开始播放。
    public void playMediaPlayer() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    //如果在播放中，立刻暂停。
    public void pauseMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stopMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
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
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    seekBar.post(() -> {
                        if (!isSeekbarChaning)
                            setTime(mediaPlayer.getCurrentPosition() / 1000);
                    });
                }
            }, 500, 500);
        }
    }

    @OnClick({R.id.exercise_report_play_iv, R.id.exercise_cuoyin_tv, R.id.exercise_cuojiezou_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_report_play_iv: // 播放
                initMediaPlayer(mp3File);
                play();
                playIv.setSelected(!playIv.isSelected());
                break;
            case R.id.exercise_cuoyin_tv:

                break;
            case R.id.exercise_cuojiezou_tv:

                break;
            default:
                break;
        }
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
                isSeekbarChaning = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekbarChaning = false;
                seekTo(seekBar.getProgress());
                setTime(seekBar.getProgress());
            }
        });
        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void initData() {
        mPresenter.downloadFile(H5Service.MP3_HOST, bean.getPath());
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
        if (timer != null)
            timer.cancel();
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
    public ResponseBody download(ResponseBody responseBody) {
        mp3File = new File(FileUtils.getCacheDir(mContext).getAbsolutePath() + "/" + bean.getPath());
        FileUtils.saveFile(responseBody, mp3File);

        duration = initMediaPlayer(mp3File);
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
