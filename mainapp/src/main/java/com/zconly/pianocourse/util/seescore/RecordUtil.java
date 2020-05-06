package com.zconly.pianocourse.util.seescore;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Description: 录音
 * @Author: dengbin
 * @CreateDate: 2020/5/5 19:59
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/5 19:59
 * @UpdateRemark: 更新说明
 */
public class RecordUtil {

    private static RecordUtil recordUtil;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mMediaRecorder;
    private File file;

    private RecordUtil() {

    }

    public static RecordUtil getInstance() {
        if (recordUtil == null)
            recordUtil = new RecordUtil();
        return recordUtil;
    }

    public int initMediaPlayer(File file) {
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
            mediaPlayer.seekTo(time);
        }
    }

    public File startRecord() {
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            file = new File(FileUtils.getCacheDir(MainApplication.getInstance())
                    + "/" + DateUtils.getTimestampStr() + ".mp3");

            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IllegalStateException | IOException e) {
            Log.i("failed!", e.getMessage());
        }

        return file;
    }

    public void stopRecord() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        } catch (RuntimeException e) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            if (file != null && file.exists())
                file.delete();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}
