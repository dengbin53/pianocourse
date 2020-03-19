package com.zconly.pianocourse.util;

import android.os.Handler;
import android.widget.Toast;

import com.zconly.pianocourse.base.MainApplication;

/**
 * 显示相关的工具类
 *
 * @author hubin
 * @date 2014-11-07
 */
public class ToastUtil {

    /**
     * 显示Toast
     *
     * @param message
     */
    private static Toast toast;

    public static void toast(String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(MainApplication.getInstance(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示Toast
     *
     * @param resID
     */
    public static void toast(int resID) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(MainApplication.getInstance(), resID, Toast.LENGTH_SHORT);
        toast.show();
    }

    private static Handler handler;

    /**
     * 在主线程 显示Toast
     *
     * @param message
     */
    public static void toastMain(final String message) {
        if (handler == null) {
            handler = new Handler(MainApplication.getInstance().getMainLooper());
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast != null)
                    toast.cancel();
                toast = Toast.makeText(MainApplication.getInstance(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}
