package com.zconly.pianocourse.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

/**
 * 倒计时工具类
 *
 * @author DengBin
 */
public class CountDownTimerTool extends CountDownTimer {

    public static int time = -1;
    /**
     * 倒计时结束
     */
    public static final int TIMER_FINISH = 0x999;
    /**
     * 倒计时
     */
    public static final int TIMER_TICK = 0x888;

    private Handler handler;

    public CountDownTimerTool(Handler handler, long millisInFuture,
                              long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.handler = handler;
    }

    private CountDownTimerTool(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
        Message msg = new Message();
        msg.what = TIMER_FINISH;
        time = -1;
        handler.sendMessage(msg);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Message msg = new Message();
        msg.what = TIMER_TICK;
        time = (int) (millisUntilFinished / 1000);
        msg.arg1 = time;
        handler.sendMessage(msg);
    }

}
