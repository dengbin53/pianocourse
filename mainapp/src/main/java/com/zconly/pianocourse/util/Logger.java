package com.zconly.pianocourse.util;

import android.util.Log;

import com.zconly.pianocourse.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;

/**
 * Log管理工具
 */
public class Logger {

    public static boolean debugFlag = BuildConfig.MSB_DEBUG;
    private static final String LOG_TAG = "msb_log";
    // 根据需要将Log存放到SD卡中
    private static String path;
    private static FileOutputStream outputStream;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void i(String msg) {
        if (debugFlag)
            Log.i(LOG_TAG, msg);
    }

    public static void i(String key, String msg) {
        if (debugFlag)
            Log.i(key, msg);
    }


    public static void d(String msg) {
        if (debugFlag)
            Log.d(LOG_TAG, msg);
    }

    public static void d(String key, String msg) {
        if (debugFlag)
            Log.d(key, msg);
    }

    public static void w(String msg) {
        if (debugFlag)
            Log.w(LOG_TAG, msg);
    }

    public static void w(String key, String msg) {
        if (debugFlag)
            Log.w(key, msg);
    }

    public static void w(Throwable msg) {
        if (debugFlag)
            Log.w(LOG_TAG, msg);
    }

    public static void w(String key, Throwable msg) {
        if (debugFlag)
            Log.w(key, msg);
    }

    public static void e(String msg) {
        if (debugFlag)
            Log.e(LOG_TAG, msg);
    }

    public static void e(String key, String msg) {
        if (debugFlag)
            Log.e(key, msg);
    }


    /**
     * 打印出来Json信息；使用默认tag
     *
     * @param jsonMsg
     */
    public static void printJson(String jsonMsg) {
        printJsonWithHead(LOG_TAG, jsonMsg, "");
    }

    /**
     * 打印出来Json信息；使用自定义tag
     *
     * @param jsonMsg
     */
    public static void printJson(String tag, String jsonMsg) {
        printJsonWithHead(tag, jsonMsg, "");
    }

    /**
     * 打印出来Json信息；使用自定义tag，并且添加Head信息。
     *
     * @param tag
     * @param msg
     * @param headString
     */
    public static void printJsonWithHead(String tag, String msg, String headString) {
        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.i(tag, "║ " + line);
        }
        printLine(tag, false);
    }


    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.i(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.i(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

}
