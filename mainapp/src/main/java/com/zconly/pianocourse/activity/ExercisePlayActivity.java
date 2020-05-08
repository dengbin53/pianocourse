package com.zconly.pianocourse.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.mvp.presenter.ExercisePresenter;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.ExerciseView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.FileUtils;
import com.zconly.pianocourse.util.Logger;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.util.seescore.SeeScoreUtil;
import com.zconly.pianocourse.widget.dialog.DialogExerciseSetting;
import com.zconly.pianocourse.widget.dialog.DialogRecordUpload;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import uk.co.dolphin_com.seescoreandroid.CursorView;
import uk.co.dolphin_com.seescoreandroid.Dispatcher;
import uk.co.dolphin_com.seescoreandroid.Player;
import uk.co.dolphin_com.seescoreandroid.SeeScoreView;
import uk.co.dolphin_com.sscore.Component;
import uk.co.dolphin_com.sscore.RenderItem;
import uk.co.dolphin_com.sscore.SScore;
import uk.co.dolphin_com.sscore.Tempo;
import uk.co.dolphin_com.sscore.ex.ScoreException;
import uk.co.dolphin_com.sscore.playdata.Note;
import uk.co.dolphin_com.sscore.playdata.PlayData;
import uk.co.dolphin_com.sscore.playdata.UserTempo;

import static uk.co.dolphin_com.seescoreandroid.Player.State.Paused;
import static uk.co.dolphin_com.seescoreandroid.Player.State.Started;

/**
 * @Description: 曲目播放、录音、上传
 * @Author: dengbin
 * @CreateDate: 2020/5/4 13:40
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/4 13:40
 * @UpdateRemark: 更新说明
 */
public class ExercisePlayActivity extends BaseMvpActivity<ExercisePresenter> implements ExerciseView {

    private static final boolean UseNoteCursorIfPossible = true; // else bar cursor
    private static final boolean ColourPlayedNotes = false; // 播放的音符变色
    private static final boolean PrintContents = false; // use SScore.getBarContents to list all items in bars
    private static RenderItem.Colour kOrange = new RenderItem.Colour(1, 0.5F, 0, 1);
    private static RenderItem.Colour kBlue = new RenderItem.Colour(0, 0, 1, 1);

    @BindView(R.id.exercise_play_favorite_tv)
    TextView favoriteTv;
    @BindView(R.id.exercise_play_name_tv)
    TextView titleTv;
    @BindView(R.id.exercise_play_author_tv)
    TextView authorTv;
    @BindView(R.id.scrollView1)
    ScrollView scoreSv; // 乐谱SV
    @BindView(R.id.scrollViewCursor)
    ScrollView cursorScrollView;
    @BindView(R.id.beatText)
    TextView beatText;
    @BindView(R.id.exercise_play_record_tv)
    TextView playRecordTv;
    @BindView(R.id.exercise_piano_rl)
    RelativeLayout pianoRl; // 显示钢琴琴键按下效果

    private DialogExerciseSetting.ExerciseSettingBean settingBean;
    private SheetBean bean;
    private SeeScoreView ssview;
    private Player player;
    private SScore currentScore;
    private float magnification = 0.56f; // 放大
    private int currentBar;
    // setup looping
    private int loopStart;
    private int loopEnd;

    private static final int kMinTempoBPM = 16;
    private static final int kMaxTempoBPM = 320;
    private static final double kMinTempoScaling = 0.5;
    private static final double kMaxTempoScaling = 2.0;
    private static final int kDefaultTempoBPM = 66;
    private static final double kDefaultTempoScaling = 1.0;
    // we can mute upper (right) or lower (left) staff
    private boolean playingLeft = true;
    private boolean playingRight = true;
    private static final int kPlayLoopRepeats = 7;

    private DialogExerciseSetting settingDialog;
    private DialogRecordUpload dialogRecordUpload;
    private File file;
    private File recordFile;
    private boolean destroyed;
    // 琴键位置(rightX) 52个白键（52等分）
    float[] points = new float[]{1f, 1.7f, 2f, 3f, 3.4f, 4f, 4.6f, 5f, 6f, 6.3f, 7f, 7.5f, 8f, 8.7f, 9f, 10f, 10.4f,
            11f, 11.6f, 12f, 13f, 13.3f, 14f, 14.5f, 15f, 15.7f, 16f, 17f, 17.4f, 18f, 18.6f, 19f, 20f, 20.3f, 21f,
            21.5f, 22f, 22.7f, 23f, 24f, 24.4f, 25f, 25.6f, 26f, 27f, 27.3f, 28f, 28.5f, 29f, 29.7f, 30f, 31f, 31.4f,
            32f, 32.6f, 33f, 34f, 34.3f, 35f, 35.5f, 36f, 36.7f, 37f, 38f, 38.4f, 39f, 39.6f, 40f, 41f, 41.3f, 42f,
            42.5f, 43f, 43.7f, 44f, 45f, 45.4f, 46f, 46.6f, 47f, 48f, 48.3f, 49f, 49.5f, 50f, 50.7f, 51f, 52f};
    // 52个白键（52等分）
    float keyItemWidth = DeviceUtils.getScreenWidth() / 52f;
    // 键盘高度
    float getKeyItemHeight = DeviceUtils.dp2px(48f);

    private PlayData.PlayControls playControls = new PlayData.PlayControls() {
        @Override
        public boolean getPartEnabled(int partIndex) {
            // 音乐声
            return false;
        }

        @Override
        public boolean getPartStaffEnabled(int partIndex, int staffIndex) {
            return staffIndex == 0 ? playingRight : playingLeft;
        }

        @Override
        public int getPartMIDIInstrument(int partIndex) {
            // 0 = use default. Return eg 41 for violin (see MIDI spec for standard program change values)
            return 1;
        }

        @Override
        public boolean getMetronomeEnabled() {
            // 节拍器声音
            return true;
        }

        @Override
        public int getMidiKeyForMetronome() {
            // defines voice of metronome - see MIDI spec "Appendix 1.5 - General MIDI Percussion Key Map"
            return 0; // use default voice
        }

        @Override
        public float getPartVolume(int partIndex) { // 音量
            return getMetronomeEnabled() ? 0.5F : 1.0F; // reduce volume of all parts if metronome is enabled
        }

        @Override
        public float getMetronomeVolume() { // 节拍器音量
            return 1.0f;
        }
    };

    static {
        System.loadLibrary("SeeScoreLib");
    }

    public static void start(Context context, SheetBean bean) {
        if (!SysConfigTool.isLogin(context, true))
            return;
        Intent intent = new Intent(context, ExercisePlayActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, bean);
        context.startActivity(intent);
    }

    private void playAndRecord(boolean stop) {
        if (!new RxPermissions(mContext).isGranted(Manifest.permission.RECORD_AUDIO) || currentScore == null)
            return;

        hideBeat();

        if (player == null) {
            player = setupPlayer();
            startPlayer();
        } else {
            switch (player.state()) {
                case NotStarted:
                    startPlayer();
                    break;
                case Started:
                    pauseOrStopPlayer(stop);
                    break;
                case Paused:
                    resumePlayer();
                    break;
                case Stopped:
                case Completed:
                    reStartPlayer();
                    break;
                default:
                    break;
            }
        }
    }

    private void startPlayer() {
        ssview.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
        player.startAt(currentBar, true);
        RecordManager.getInstance().start();
        updatePlayBtnView(); // 播放按钮状态
    }

    private void pauseOrStopPlayer(boolean stop) {
        if (player == null)
            return;
        if (stop) {
            player.stop();
            currentBar = Math.max(0, loopStart);
            RecordManager.getInstance().stop();
            if (ColourPlayedNotes)
                ssview.clearAllColouring();
        } else {
            player.pause();
            currentBar = player.currentBar();
            RecordManager.getInstance().pause();
        }
        updatePlayBtnView(); // 播放按钮状态
    }

    private void resumePlayer() {
        if (player == null || player.state() != Paused)
            return;
        currentBar = player.currentBar();
        player.resume();
        RecordManager.getInstance().resume();
        updatePlayBtnView(); // 播放按钮状态
    }

    private void resetSetPlayer() {
        if (player == null)
            return;
        player.reset();
        currentBar = Math.max(0, loopStart);
        ssview.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
        RecordManager.getInstance().stop();
        updatePlayBtnView(); // 播放按钮状态
    }

    private void reStartPlayer() {
        if (player == null)
            return;
        RecordManager.getInstance().stop();
        player.reset();
        currentBar = Math.max(0, loopStart);
        ssview.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
        player.startAt(currentBar, true);
        RecordManager.getInstance().start();
        updatePlayBtnView(); // 播放按钮状态
    }

    public void stopAndReleasePlayer() {
        hideBeat();
        if (player != null) {
            try {
                player.reset();
                currentBar = Math.max(0, loopStart);
            } catch (Exception e) {
                Logger.w(e);
            }
        }
        player = null;
    }

    private Player setupPlayer() {
        try {
            final Player pl = new Player(currentScore, new UserTempoImpl(), mContext, true, playControls,
                    loopStart, loopEnd, (loopStart >= 0 && loopEnd >= 0) ? kPlayLoopRepeats : 0);
            final int autoScrollAnimationTime = pl.bestScrollAnimationTime();

            // 开始
            pl.setBarStartHandler(new Dispatcher.EventHandler() {

                private int lastIndex = -1;

                public void event(final int index, final boolean ci) {
                    // use bar cursor if bar time is short
                    final boolean useNoteCursor = !pl.needsFastCursor();
                    if (!useNoteCursor || ColourPlayedNotes) {
                        mHandler.post(() -> {
                            if (!useNoteCursor) // use bar cursor
                                ssview.setCursorAtBar(index, SeeScoreView.CursorType.line, autoScrollAnimationTime);

                            if (ColourPlayedNotes) {
                                // if this is a repeat section we clear the colouring from the previous repeat
                                boolean startRepeat = index < lastIndex;
                                if (startRepeat) {
                                    ssview.clearColouringForBarRange(index, currentScore.numBars() - index);
                                }
                            }
                            lastIndex = index;
                        });
                    }
                }
            }, -50); // anticipate so cursor arrives on time

            // 节拍器
            pl.setBeatHandler((index, ci) -> mHandler.post(new Runnable() {
                final int beatNumber = index + 1;
                final boolean countIn = ci;

                public void run() {
                    if (countIn)
                        showBeat(beatNumber);
                    else
                        hideBeat();
                }
            }), 0);

            // 音符
            if (UseNoteCursorIfPossible || ColourPlayedNotes) {
                pl.setNoteHandler(notes -> {
                    // 设置新按键
                    setPianoKey(notes);
                    // disable note cursor if bar time is short
                    final boolean useNoteCursor = !pl.needsFastCursor();
                    if (useNoteCursor) {
                        mHandler.post(new Runnable() {
                            final List<Note> localNotes = notes;

                            public void run() {
                                ssview.moveNoteCursor(localNotes, autoScrollAnimationTime);
                            }
                        });
                    }
                    if (ColourPlayedNotes) {
                        mHandler.post(() -> {
                            for (Note note : notes) {
                                // different colours in different staves
                                ssview.colourItem(note.partIndex, note.startBarIndex, note.item_h,
                                        (note.staffindex > 0) ? kOrange : kBlue, true);
                            }
                        });
                    }
                }, -50);
            }

            // 结束
            pl.setEndHandler((index, countIn) ->
                    mHandler.post(() -> {
                        pauseOrStopPlayer(true);
                        playRecordTv.setSelected(player.state() == Started);
                        cleanPianoKey();
                    }), 0);

            return pl;

        } catch (Player.PlayerException ex) {
            Logger.w(ex);
        }

        return null;
    }

    private void setPianoKey(List<Note> notes) {
        cleanPianoKey();
        if (ArrayUtil.isEmpty(notes))
            return;
        runOnUiThread(() -> {
            for (Note note : notes) {
                int po = note.midiPitch - 1;
                if (po < 0 || po >= points.length)
                    return;
                float pos = points[po];
                int w = (int) (pos % 1f == 0.0f ? keyItemWidth : keyItemWidth * 0.72f);
                int h = (int) (pos % 1f == 0.0f ? getKeyItemHeight : getKeyItemHeight * 0.68f);
                int marginStart = (int) (pos * keyItemWidth - w);

                int img = note.staffindex == 0 ? R.mipmap.ic_pianokeyboard_markc1 : R.mipmap.ic_pianokeyboard_markc2;

                ImageView iv = new ImageView(mContext);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, h);
                lp.setMarginStart(marginStart);
                iv.setImageResource(img);
                pianoRl.addView(iv, lp);
            }
        });
    }

    private void cleanPianoKey() {
        runOnUiThread(() -> pianoRl.removeAllViews());
    }

    // display the current zoom value in the TextView label
    private void showZoom(float scale) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
    }

    // 显示乐谱
    private void showScore(SScore score, Runnable completionHandler) {
        if (completionHandler != null) {
            ssview.setLayoutCompletionHandler(completionHandler);
        } else {
            ssview.setLayoutCompletionHandler(() -> {
                // 乐谱加载完成

            });
        }
        hideBeat();
        ArrayList parts = new ArrayList<Boolean>();
        ssview.setScore(score, parts, magnification);

        setupTempoUI(score);
    }

    // 节拍器
    private void setupTempoUI(SScore score) {
        int bpm = kDefaultTempoBPM;
        if (score.hasDefinedTempo()) {
            try {
                Tempo tempo = score.tempoAtStart();
                bpm = tempo.bpm;
            } catch (ScoreException ex) {
                Logger.w(ex);
            }
        }
        settingBean.setTempo(bpm);
        // 显示设置弹框
        showSettingDialog();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setSeeView() {
        loopStart = loopEnd = -1;
        final CursorView cursorView = new CursorView(mContext, () -> scoreSv.getScrollY());
        ssview = new SeeScoreView(mContext, cursorView, getAssets(), scale -> {
            showZoom(scale);
            magnification = scale;
        }, new SeeScoreView.TapNotification() {
            public void tap(int systemIndex, int partIndex, int barIndex, Component[] components) {

                boolean isPlaying = (player != null && player.state() == Started);

                if (player != null) {
                    boolean isPaused = (player.state() == Player.State.Paused);
                    if (isPlaying || isPaused)
                        player.stop();
                }
                if (barIndex < loopStart)
                    barIndex = loopStart;
                else if (loopEnd > 0 && loopEnd > loopStart && barIndex > loopEnd)
                    barIndex = loopEnd;

                ssview.setCursorAtBar(barIndex, SeeScoreView.CursorType.line, 200);

                if (isPlaying) {
                    player.startAt(barIndex, false/*no countIn*/);
                }
                currentBar = barIndex;

                // updatePlayPauseButtonImage();

                for (Component comp : components)
                    System.out.println(comp);
            }

            public void longTap(int systemIndex, int partIndex, int barIndex, Component[] components) {

            }
        });

        scoreSv.addView(ssview);
        cursorScrollView.addView(cursorView);

        cursorScrollView.setOnTouchListener((v, event) -> {
            // pass through the touch events to sv
            return scoreSv.dispatchTouchEvent(event);
        });
        //Sets the cursorScrollView's height and width
        cursorScrollView.getViewTreeObserver().addOnGlobalLayoutListener(()
                -> cursorView.measure(cursorScrollView.getWidth(), cursorScrollView.getHeight()));

        scoreSv.setOnTouchListener((arg0, event) -> ssview.onTouchEvent(event));
    }

    private void showBeat(int beat) {
        beatText.setText("" + beat);
        beatText.setVisibility(TextView.VISIBLE);
    }

    private void hideBeat() {
        beatText.setVisibility(TextView.INVISIBLE);
    }

    // 播放按钮状态
    private void updatePlayBtnView() {
        playRecordTv.setSelected(player != null && player.state() == Started);
        playRecordTv.setText(player != null && player.state() == Started ? "停止录制" : "开始录制");
    }

    // 上传录音提示框
    private void showUploadDialog() {
        if (recordFile == null)
            return;
        dialogRecordUpload = DialogRecordUpload.getInstance(recordFile, (DialogRecordUpload.ClickListener) upload
                -> {
            if (upload) { // 上传
                mPresenter.uploadExercise(recordFile, bean.getId(), settingBean.getTempo(),
                        settingBean.getHand(), 0, dialogRecordUpload.getDuration());
            } else { // 再来一次
                resetSetPlayer();
                recordFile = null;
            }
        });
        dialogRecordUpload.show(getSupportFragmentManager(), "up");
    }

    private void showSettingDialog() {
        if (settingDialog == null) {
            settingDialog = DialogExerciseSetting.getInstance(settingBean, (DialogExerciseSetting.ClickListener) bean
                    -> {
                settingBean = bean;
                updateTempo();
                resumePlayer();
            });
            settingDialog.setGravity(Gravity.CENTER);
            settingDialog.setCancelable(true);
        }
        settingDialog.show(getSupportFragmentManager(), "des");
    }

    private void updateTempo() {
        if (player != null) {
            try {
                player.updateTempo();
            } catch (Player.PlayerException ex) {
                Logger.w(ex);
            }
        }
    }

    @OnClick({R.id.exercise_play_back_iv, R.id.exercise_play_dashike_tv, R.id.exercise_play_favorite_tv,
            R.id.exercise_play_setting_tv, R.id.exercise_play_record_tv, R.id.exercise_play_re_record_tv,
            R.id.exercise_play_accompany_tv, R.id.exercise_play_tiaosu_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_play_back_iv:
            case R.id.exercise_play_dashike_tv:
                finish();
                break;
            case R.id.exercise_play_favorite_tv: // 收藏
                mPresenter.favoriteSheet(bean.getId());
                break;
            case R.id.exercise_play_record_tv: // 录制
                playAndRecord(true);
                break;
            case R.id.exercise_play_re_record_tv: // 重新录制
                resetSetPlayer();
                playAndRecord(false);
                break;
            case R.id.exercise_play_accompany_tv: // 伴奏

                break;
            case R.id.exercise_play_tiaosu_tv: // 调速
            case R.id.exercise_play_setting_tv:
                pauseOrStopPlayer(false);
                showSettingDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        // 录音初始化
        RecordManager.getInstance().init(MainApplication.getInstance(), false);
        RecordManager.getInstance().changeFormat(RecordConfig.RecordFormat.MP3);
        RecordManager.getInstance().changeRecordDir(FileUtils.getCacheDir(mContext) + "/record");
        RecordManager.getInstance().setRecordResultListener(result -> {
            if (destroyed)
                return;
            recordFile = result;
            showUploadDialog();
        });
        mPresenter.downloadFile(H5Service.FILE_HOST, bean.getSheet_mxl());
        mPresenter.addSheetWatch(bean.getId());
    }

    @SuppressLint("CheckResult")
    @Override
    protected boolean initView() {
        bean = (SheetBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        mHandler = new Handler();
        if (bean == null) {
            finish();
            return false;
        }

        titleTv.setText(bean.getName());
        authorTv.setText(bean.getAuthor());
        updateFavoriteView();

        settingBean = new DialogExerciseSetting.ExerciseSettingBean();
        settingBean.setTempo(kDefaultTempoBPM);
        settingBean.setHand(DialogExerciseSetting.ExerciseSettingBean.HAND_DOUBLE);

        setSeeView();

        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO).subscribe(aBoolean -> {
        });
        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_exercise_play;
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
        stopAndReleasePlayer();
        destroyed = true;
        RecordManager.getInstance().stop();
        super.onDestroy();
    }

    @Override
    public void downloadSuccess() {
        new Thread(() -> {
            currentScore = SeeScoreUtil.loadMXLFile(file);
            mHandler.post(() -> showScore(currentScore, null));
        }).start();
    }

    @Override
    public ResponseBody download(ResponseBody responseBody) {
        file = new File(FileUtils.getCacheDir(mContext).getAbsolutePath() + "/" + bean.getSheet_mxl());
        FileUtils.saveFile(responseBody, file);
        return responseBody;
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void uploadSuccess(FileBean response) {

    }

    @Override
    public void uploadExerciseSuccess(ExerciseBean.ExerciseResult response) {
        ToastUtil.toast("上传成功");
    }

    @Override
    public void favoriteSuccess(BaseBean response) {
        bean.setFavorited(bean.getFavorited() > 0 ? 0 : 1);
        updateFavoriteView();
    }

    @Override
    public void getEvaluateListSuccess(EvaluateBean.EvaluateListResult response) {

    }

    private void updateFavoriteView() {
        favoriteTv.setSelected(bean.getFavorited() > 0);
    }

    private class UserTempoImpl implements UserTempo {

        // return the user-defined tempo BPM (if not defined by the score)
        public int getUserTempo() {
            return settingBean.getTempo();
            // return tempoSliderPercentToBPM(settingBean.getTempo());
        }

        // return the user-defined tempo scaling for score embedded tempo values (ie 1.0 => use standard tempo)
        public float getUserTempoScaling() {
            float scaling = settingBean.getTempo() * 1f / bean.getBpm2();
            return Math.abs(scaling - 1.0) < 0.1 ? 1.0f : scaling;
        }
    }

}
