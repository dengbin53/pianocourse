package com.zconly.pianocourse.widget.dialog;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mvp.base.MvpDialog;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.constants.ExtraConstants;

import java.io.File;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 练习曲目设置
 *
 * @author DengBin
 */
public class DialogRecordUpload extends MvpDialog {

    @BindView(R.id.dialog_exercise_upload_sb)
    SeekBar seekBar;
    @BindView(R.id.dialog_exercise_time_tv)
    TextView timeTv;
    @BindView(R.id.dialog_exercise_play_tv)
    ImageView playIv;

    private File bean;
    private ClickListener onClick;
    private int duration;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private boolean isSeekbarChaning;

    public static DialogRecordUpload getInstance(File fileStr, ClickListener onClick) {
        DialogRecordUpload dialog = new DialogRecordUpload();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExtraConstants.EXTRA_DATA, fileStr.getAbsolutePath());
        bundle.putSerializable(ExtraConstants.EXTRA_LISTENER, onClick);
        dialog.setArguments(bundle);
        return dialog;
    }

    public int getDuration() {
        return duration;
    }

    private void play() {
        if (mediaPlayer.isPlaying()) {
            pauseMediaPlayer();
            playIv.setSelected(false);
        } else {
            playIv.setSelected(true);
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

    @OnClick({R.id.dialog_exercise_retry_tv, R.id.dialog_exercise_upload_tv, R.id.dialog_exercise_play_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_exercise_retry_tv: // 再来一次
                if (onClick != null)
                    onClick.onConfirm(false);
                dismiss();
                break;
            case R.id.dialog_exercise_upload_tv: // 上传
                if (onClick != null)
                    onClick.onConfirm(true);
                dismiss();
                break;
            case R.id.dialog_exercise_play_tv: // 播放
                play();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentViewID() {
        return R.layout.dialog_exercise_upload;
    }

    @Override
    protected int getStyle() {
        return R.style.dialog_sacle;
    }

    @Override
    protected void initView(Bundle bundle) {
        if (getArguments() != null) {
            String fileStr = getArguments().getString(ExtraConstants.EXTRA_DATA);
            onClick = (ClickListener) getArguments().getSerializable(ExtraConstants.EXTRA_LISTENER);
            bean = new File(fileStr);
        }

        if (bean == null || !bean.exists()) {
            dismiss();
            return;
        }

        duration = initMediaPlayer(bean);
        duration /= 1000;

        if (duration < 1) {
            dismiss();
            return;
        }

        setGravity(Gravity.CENTER);

        seekBar.setMax(duration);
        seekBar.setProgress(0);
        setTime(0);

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
    }

    @Override
    public void onDestroy() {
        if (timer != null)
            timer.cancel();
        stopMediaPlayer();
        super.onDestroy();
    }

    private void setTime(int time) {
        timeTv.setText(time + "/" + duration);
        seekBar.setProgress(time);
    }

    public interface ClickListener extends Serializable {
        void onConfirm(boolean bean);
    }

    public int initMediaPlayer(File file) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            mediaPlayer.setLooping(false);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer
            mediaPlayer.setOnCompletionListener(mp -> playIv.setSelected(false));
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

}
