package com.zconly.pianocourse.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.MainApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统信息工具
 */
public class DeviceUtils {

    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;
    public static int VALID_SCREEN_HEIGHT; // 在部分手机上有返回键时需要处理

    public static int getSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    // 获得设备型号
    public static String getDeviceModel() {
        return StringUtils.trim(Build.MODEL);
    }

    // 获取厂商信息
    public static String getManufacturer() {
        return StringUtils.trim(Build.MANUFACTURER);
    }

    // 判断是否是平板电脑
    public static boolean isTablet() {
        return (MainApplication.getInstance().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    // 检测是否是平板电脑
    public static boolean isHoneycombTablet() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && isTablet();
    }

    public static int getScreenWidth() {
        if (SCREEN_WIDTH > 0)
            return SCREEN_WIDTH;
        Display display = ((WindowManager) MainApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return SCREEN_WIDTH = p.x;
    }

    public static int getScreenHeight() {
        if (SCREEN_HEIGHT > 0)
            return SCREEN_HEIGHT;
        Display display = ((WindowManager) MainApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return SCREEN_HEIGHT = p.y;
    }

    public static int dp2px(float dp) {
        return (int) ((MainApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.dp10) / 10f) * dp);
    }

    public static int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, Resources.getSystem().getDisplayMetrics());
    }

    public static float px2sp(float pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = MainApplication.getInstance().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取虚拟按键的高度
     * 1. 全面屏下
     * 1.1 开启全面屏开关-返回0
     * 1.2 关闭全面屏开关-执行非全面屏下处理方式
     * 2. 非全面屏下
     * 2.1 没有虚拟键-返回0
     * 2.1 虚拟键隐藏-返回0
     * 2.2 虚拟键存在且未隐藏-返回虚拟键实际高度
     */
    public static int getNavigationBarHeightIfRoom(Activity context) {
        if (navigationGestureEnabled(context))
            return 0;
        return getCurrentNavigationBarHeight(context);
    }

    // 全面屏（是否开启全面屏开关 0 关闭  1 开启）
    public static boolean navigationGestureEnabled(Context context) {
        int val = Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0);
        return val != 0;
    }

    // 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
    public static String getDeviceInfo() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min";

        if (brand.equalsIgnoreCase("HUAWEI")) {
            return "navigationbar_is_min";
        } else if (brand.equalsIgnoreCase("XIAOMI")) {
            return "force_fsg_nav_bar";
        } else if (brand.equalsIgnoreCase("VIVO")) {
            return "navigation_gesture_on";
        } else if (brand.equalsIgnoreCase("OPPO")) {
            return "navigation_gesture_on";
        } else {
            return "navigationbar_is_min";
        }
    }

    // 非全面屏下 虚拟键实际高度(隐藏后高度为0)
    public static int getCurrentNavigationBarHeight(Activity activity) {
        if (isNavigationBarShown(activity)) {
            return getNavigationBarHeight(activity);
        } else {
            return 0;
        }
    }

    // 非全面屏下 虚拟按键是否打开
    public static boolean isNavigationBarShown(Activity activity) {
        //虚拟键的view,为空或者不可见时是隐藏状态
        View view = activity.findViewById(android.R.id.navigationBarBackground);
        if (view == null) {
            return false;
        }
        int visible = view.getVisibility();
        if (visible == View.GONE || visible == View.INVISIBLE) {
            return false;
        } else {
            return true;
        }
    }

    // 非全面屏下 虚拟键高度(无论是否隐藏)
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // 显示软键盘
    public static void showSoftInput(EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(edit, 0);
    }

    // 隐藏软键盘
    public static void hideSoftInput(Activity activity, EditText et) {
        View view = et;
        if (view == null)
            view = activity.getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 隐藏软键盘
    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive())
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    // 拷贝进剪切板
    public static void copyToClipBoard(Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("artlive", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }

    // 返回手机品牌
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    // 返回手机型号
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    // 返回系统版本
    public static String getVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    // 返回手机分辨率（宽*高）。注意:返回值手机屏幕是否旋转无关。
    public static String getResolution() {
        int[] info = getDispInfo();
        if (info[0] == -1 || info[1] == -1) return null;
        if (info[2] == Surface.ROTATION_0 || info[2] == Surface.ROTATION_180)
            return info[0] + "x" + info[1];
        else return info[1] + "x" + info[0];
    }

    // 获取手机SIM卡的IMSI地址
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getSimImsi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int grant = ActivityCompat.checkSelfPermission(MainApplication.getInstance(),
                    Manifest.permission.READ_PHONE_STATE);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
        }

        Context context = MainApplication.getInstance();
        if (context == null) return null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null || tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT) return null;
        return tm.getSubscriberId();
    }

    // 获取手机的IMEI地址
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getPhoneImei() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int grant = ActivityCompat.checkSelfPermission(MainApplication.getInstance(),
                    Manifest.permission.READ_PHONE_STATE);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
        }

        Context context = MainApplication.getInstance();
        if (context == null) return null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return null;
        if (tm.getDeviceId() == null || tm.getDeviceId().equals("")) {
            return getWifiMac();
        }
        return tm.getDeviceId();
    }

    // 获取WIFI的MAC地址
    public static String getWifiMac() {
        Context context = MainApplication.getInstance();
        if (context == null) return null;
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) return null;
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) return null;
        return info.getMacAddress();
    }

    // 检查网络是否可用
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int grant = ActivityCompat.checkSelfPermission(MainApplication.getInstance(),
                    Manifest.permission.ACCESS_NETWORK_STATE);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        Context context = MainApplication.getInstance();
        if (context == null) return false;
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn == null) return false;
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 检查ExternalStorage是否可用。
     * 注意:ExternalStorage并不一定就是SD Card，根据不同手机的具体实现不同，ExternalStorage有可能只是手机内部存储。
     *
     * @return true/false
     */
    public static boolean isExternalStorageAvailable() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    // 检查SD Card是否可用
    public static boolean isSdCardAvailable() {
        if (isExternalStorageIsSdCard()) return isExternalStorageAvailable();
        List<String> info = getExternalStorageInfo();
        return info != null && info.size() != 0;
    }

    // 返回Exetrnal Storage是否是SD Card
    @SuppressLint("NewApi")
    public static boolean isExternalStorageIsSdCard() {
        if (Build.VERSION.SDK_INT < 9) return true;
        else return Environment.isExternalStorageRemovable();
    }

    // 返回系统数据分区的文件路径
    public static String getDataDirectory() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    // 返回当前app的data目录路径
    public static String getAppDirectory() {
        return MainApplication.getInstance().getFilesDir().getAbsolutePath();
    }

    // 返回External Storage分区的文件路径
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    // 返回SD Card分区的文件路径
    public static String getSdCardDirectory() {
        if (isExternalStorageIsSdCard()) return getExternalStorageDirectory();
        List<String> info = getExternalStorageInfo();
        if (info == null || info.size() == 0) return null;
        return info.get(0);
    }

    // 内部函数
    private static int[] getDispInfo() {
        Context context = MainApplication.getInstance();
        if (context == null) return new int[]{-1, -1};
        Display dm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (dm == null) return new int[]{-1, -1};
        try {
            Point size = new Point();
            Method method = dm.getClass().getMethod("getRealSize", Point.class);
            method.invoke(dm, size);
            return new int[]{size.x, size.y, dm.getRotation()};
        } catch (Exception e) {
            return new int[]{-1, -1};
        }
    }

    private static List<String> getExternalStorageInfo() {
        BufferedReader reader = null;
        List<String> info = new ArrayList<>(5);
        try {
            Process process = Runtime.getRuntime().exec("mount");
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(" ");
                if (columns == null || columns.length < 4) continue;
                if (columns[1].contains("secure")) continue;
                if (columns[1].contains("asec")) continue;
                if (!columns[2].contains("fat")) continue;
                if (!columns[3].contains("rw")) continue;
                info.add(columns[1]);
            }
        } catch (IOException e) {
            return null;
        } finally {
            FileUtils.close(reader);
        }
        info.remove(getExternalStorageDirectory());
        return info;
    }

    // 获取已安装应用程序的VersionName
    static public String getAppVersionName(String... packageName) {
        PackageInfo pi = getAppPackageInfo(packageName);
        if (pi == null) return null;
        return pi.versionName;
    }

    // 获取已安装应用程序的VersionCode
    static public int getAppVersionCode(String... packageName) {
        PackageInfo pi = getAppPackageInfo(packageName);
        if (pi == null) return -1;
        return pi.versionCode;
    }

    // 获取已安装应用程序对应的PackageInfo
    static public PackageInfo getAppPackageInfo(String... packageName) {
        try {
            String name = packageName.length > 0 ? packageName[0] : null;
            if (name == null || name.length() == 0)
                name = MainApplication.getInstance().getPackageName();
            PackageManager manager = MainApplication.getInstance().getPackageManager();
            return manager.getPackageInfo(name, 0);
        } catch (Exception e) {
            return null;
        }
    }

}
