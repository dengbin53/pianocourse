package com.zconly.pianocourse.widget.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mvp.base.MvpDialog;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.constants.ExtraConstants;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 练习曲目设置
 *
 * @author DengBin
 */
public class DialogExerciseSetting extends MvpDialog {

    @BindView(R.id.dialog_exercise_double_hand_tv)
    TextView doubleHandTv;
    @BindView(R.id.dialog_exercise_left_hand_tv)
    TextView leftHandTv;
    @BindView(R.id.dialog_exercise_right_hand_tv)
    TextView rightHandTv;
    @BindView(R.id.dialog_exercise_sb)
    SeekBar seekBar;
    @BindView(R.id.dialog_exercise_speed_tv)
    TextView speedTv;

    private ExerciseSettingBean bean;
    private ClickListener onClick;

    public static DialogExerciseSetting getInstance(ExerciseSettingBean bean, ClickListener onClick) {
        DialogExerciseSetting dialog = new DialogExerciseSetting();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExtraConstants.EXTRA_DATA, bean);
        bundle.putSerializable(ExtraConstants.EXTRA_LISTENER, onClick);
        dialog.setArguments(bundle);
        return dialog;
    }

    private void setHandView() {
        doubleHandTv.setSelected(bean.getHand() == ExerciseSettingBean.HAND_DOUBLE);
        leftHandTv.setSelected(bean.getHand() == ExerciseSettingBean.HAND_LEFT);
        rightHandTv.setSelected(bean.getHand() == ExerciseSettingBean.HAND_RIGHT);
    }

    @OnClick({R.id.dialog_exercise_double_hand_ll, R.id.dialog_exercise_left_hand_tv, R.id.dialog_exercise_right_hand_tv,
            R.id.dialog_exercise_btn})
    public void onClick(View view) {
        if (bean == null)
            bean = new ExerciseSettingBean();
        switch (view.getId()) {
            case R.id.dialog_exercise_double_hand_ll: // 双手
                bean.setHand(ExerciseSettingBean.HAND_DOUBLE);
                setHandView();
                break;
            case R.id.dialog_exercise_left_hand_tv: // 左手
                bean.setHand(ExerciseSettingBean.HAND_LEFT);
                setHandView();
                break;
            case R.id.dialog_exercise_right_hand_tv: // 右手
                bean.setHand(ExerciseSettingBean.HAND_RIGHT);
                setHandView();
                break;
            case R.id.dialog_exercise_btn: // 确认
                if (onClick != null)
                    onClick.onConfirm(bean);
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentViewID() {
        return R.layout.dialog_exercise_setting;
    }

    @Override
    protected int getStyle() {
        return R.style.dialog_sacle;
    }

    @Override
    protected void initView(Bundle bundle) {
        if (getArguments() != null) {
            bean = (ExerciseSettingBean) getArguments().getSerializable(ExtraConstants.EXTRA_DATA);
            onClick = (ClickListener) getArguments().getSerializable(ExtraConstants.EXTRA_LISTENER);
        }

        setGravity(Gravity.CENTER);

        if (bean != null) {
            seekBar.setProgress(bean.getTempo() - 16);
            speedTv.setText(bean.getTempo() + "");

            setHandView();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;
                if (bean == null)
                    bean = new ExerciseSettingBean();
                bean.setTempo(progress + 16);
                speedTv.setText(bean.getTempo() + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static interface ClickListener extends Serializable {
        void onConfirm(ExerciseSettingBean bean);
    }

    public static class ExerciseSettingBean extends BaseBean {

        public static final int HAND_DOUBLE = 0;
        public static final int HAND_LEFT = 1;
        public static final int HAND_RIGHT = 2;

        private int tempo;
        private int hand;

        public int getHand() {
            return hand;
        }

        public void setHand(int hand) {
            this.hand = hand;
        }

        public int getTempo() {
            return tempo;
        }

        public void setTempo(int tempo) {
            this.tempo = tempo;
        }
    }
}
