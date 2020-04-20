package com.zconly.pianocourse.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.baidu.cloud.videoplayer.widget.BDCloudVideoView;
import com.xiaoe.shop.webcore.core.XiaoEWeb;
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
                initConstants();
                initUser();
                getXiaoEToken();
                break;
            case "download_ads_image": // 下载广告图
                Logger.i("download_ads_image");
//                downloadAdsImage((ProblemTagResult.StartAdv) intent.getSerializableExtra("adv"));
                break;
            default:
                break;
        }
    }

    private void getXiaoEToken() {
        XiaoEWeb.init(getApplicationContext(), Constants.XIAO_E_APP_ID, Constants.XIAO_E_CLIENT_ID,
                XiaoEWeb.WebViewType.Android);
    }

    private void initUser() {
        // 百度videoPlayer
        BDCloudVideoView.setAK(Constants.BAI_DU_AK);
    }

    // 初始化一些全局参数
    private void initConstants() {
        Constants.CLIENT_VERSION = String.valueOf(DeviceUtils.getAppVersionCode());
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
