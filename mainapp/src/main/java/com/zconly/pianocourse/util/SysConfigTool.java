package com.zconly.pianocourse.util;


import android.content.Context;
import android.text.TextUtils;

import com.xiaoe.shop.webcore.core.XiaoEWeb;
import com.zconly.pianocourse.activity.SignInActivity;
import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.bean.SysConfig;
import com.zconly.pianocourse.bean.UserBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置信息
 *
 * @author DengBin
 */
public class SysConfigTool {
    private static final String ACCESS_TOKEN = "access_token";
    private static UserBean user;

    // 解决拍照之后调用onPause导致变量mCurrentPhotoFile消失
    public static String getPhotoPath() {
        String photoPath = SPTool.getString("photoPath", null);
        return photoPath;
    }

    public static void setPhotoPath(File gameShareType) {
        SPTool.putString("photoPath", gameShareType + "");
    }

    // 获取文件名
    public static String getFileName(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return "";
        }
        String filename = downloadUrl
                .substring(downloadUrl.lastIndexOf('/') + 1);
        filename = filename.split("[?]")[0];
        return filename;
    }

    public static double getLatitude() {
        return Double.valueOf(SPTool.getString("latitude", "0"));
    }

    public static double getLongitude() {
        return Double.valueOf(SPTool.getString("longitude", "0"));
    }

    public static void saveLatitude(double d) {
        SPTool.putString("latitude", d + "");
    }

    public static void saveLongitude(double e) {
        SPTool.putString("longitude", e + "");
    }

    public static void setLastLoginContact(String phone) {
        SPTool.putString("last_contact", phone);
    }

    public static String getLastLoginContact() {
        return SPTool.getString("last_contact", null);
    }

    public static void saveDeviceId(String d) {
        SPTool.putString("device_id", d);
    }

    public static String getDeviceId() {
        return SPTool.getString("device_id", "");
    }

    public static void setUser(UserBean user) {
        SysConfigTool.user = user;
        SPTool.putObj("user_me", user);
    }

    public static UserBean getUser() {
        if (user == null)
            user = (UserBean) SPTool.getObj("user_me");
        return user;
    }

    public static void saveToken(String token) {
        SPTool.putString(ACCESS_TOKEN, token);
    }

    public static String getToken() {
        return SPTool.getString(ACCESS_TOKEN, null);
    }

    public static SysConfig getSysConfig() {
        return null;
    }

    public static List<String> getSearchCityHistory() {
        return (List<String>) SPTool.getObj("city");
    }

    public static void setSearchCityHistory(ArrayList<String> cities) {
        SPTool.putObj("city", cities);
    }

    public static void logout() {
        saveToken("");
        setUser(null);
        XiaoEWeb.userLogout(MainApplication.getInstance());
    }

    public static long getCid() {
        return SPTool.getLong("cid", 0);
    }

    public static void setCid(long cid) {
        SPTool.putLong("cid", cid);
    }

    public static boolean isLogin(Context context, boolean toLogin) {
        boolean isLogin = getUser() != null;
        if (toLogin && !isLogin) {
            SignInActivity.start(context);
        }
        return isLogin;
    }
}

