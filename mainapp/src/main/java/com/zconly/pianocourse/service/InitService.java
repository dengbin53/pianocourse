package com.zconly.pianocourse.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.umeng.commonsdk.UMConfigure;
import com.zconly.pianocourse.BuildConfig;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.Logger;

/**
 * @Description: 初始化组件等
 * @Author: dengbin
 * @CreateDate: 2020-01-03 11:53
 * @UpdateUser: dengbin
 * @UpdateDate: 2020-01-03 11:53
 * @UpdateRemark: 更新说明
 */
public class InitService extends IntentService {

    public InitService() {
        this("");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null)
            return;
        String tag = intent.getStringExtra("tag");
        switch (tag) {
            case "init_app":
                Logger.i("init_app");
                initUmeng();
                initConstants();
                initUser();
                break;
            case "download_ads_image": // 下载广告图
                Logger.i("download_ads_image");
//                downloadAdsImage((ProblemTagResult.StartAdv) intent.getSerializableExtra("adv"));
                break;
            default:
                break;
        }
    }

    private void initUmeng() {
        UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        // 微信
//        PlatformConfig.setWeixin("wx6069a847f3d0f3f4", "aae2b5419a74ec74bb4849307975c52c");
        // 新浪微博
//        PlatformConfig.setSinaWeibo("1488037763", "796dc73d08831af9d5aaf10e7ab4ffc8",
//                "http://sns.whalecloud.com");
//        PlatformConfig.setQQZone("1103288394", "svSMPZvpVbrk8v9v");
    }

    private void initUser() {

    }

    // 初始化一些全局参数
    private void initConstants() {
        Constants.CLIENT_VERSION = String.valueOf(DeviceUtils.getAppVersionCode());
        Constants.SYSTEM_VERSION = "2.0";

        Logger.d("Constants.userid = " + Constants.USER_ID);

        // 取 Umeng 渠道号
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            Constants.UMENG_CHANNEL = appInfo.metaData.get("UMENG_CHANNEL").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 在魅族手机上这个操作会ANR,所以异步执行
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Constants.IMEI = DeviceUtils.getPhoneImei();
    }

}
