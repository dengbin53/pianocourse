package com.baidu.cloud.videoplayer.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.baidu.cloud.videoplayer.R;

public class Utils {
    private static final String TAG = "FullScreenUtils";
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;
    public static int VALID_SCREEN_HEIGHT; // 在部分手机上有返回键时需要处理

    /**
     * 隐藏或显示：状态栏、系统虚拟按钮栏
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     * <p>
     * code is from:
     * https://d.android.com/samples/ImmersiveMode/src/com.example.android.immersivemode/ImmersiveModeFragment.html
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void toggleHideyBar(Activity activity) {
        int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding: Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public static int getScreenWidth(Context context) {
        if (SCREEN_WIDTH > 0)
            return SCREEN_WIDTH;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return SCREEN_WIDTH = p.x;
    }

    public static int dp2px(Context context, float dp) {
        return (int) ((context.getResources().getDimensionPixelSize(R.dimen.dp10) / 10f) * dp);
    }

}
