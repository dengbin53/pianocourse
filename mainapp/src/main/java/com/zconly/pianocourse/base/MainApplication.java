package com.zconly.pianocourse.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import androidx.multidex.MultiDex;

import com.mvp.utils.RetrofitUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zconly.pianocourse.BuildConfig;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.mvp.MHeaderInterceptor;
import com.zconly.pianocourse.service.InitService;
import com.zconly.pianocourse.util.Logger;

public class MainApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static MainApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Intent intent = new Intent(context, InitService.class);
        intent.putExtra("tag", "init_app");
        startService(intent);

        MultiDex.install(context);

        // 初始化接口地址
        RetrofitUtils.init(BuildConfig.HOST_API, BuildConfig.HOST_H5, new MHeaderInterceptor(), BuildConfig.MSB_DEBUG);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Logger.i("onActivityCreated:" + activity + ";" + savedInstanceState);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Logger.i("onActivityStarted:" + activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Logger.i("onActivityResumed:" + activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.i("onActivityPaused:" + activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Logger.i("onActivityStopped:" + activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Logger.i("onActivitySaveInstanceState:" + activity + ";" + outState);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.i("onActivityDestroyed:" + activity);
            }
        });
    }

    public static MainApplication getInstance() {
        return context;
    }

    static {
        // 设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            // 开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
            layout.setReboundDuration(768);
            layout.setReboundInterpolator(new DecelerateInterpolator());
            layout.setFooterHeight(128);
            layout.setDisableContentWhenLoading(false);
            layout.setPrimaryColorsId(R.color.color_white, R.color.color_text_gray_gray_light);
        });

        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.color_bg_default, R.color.color_text_gray_gray_light); // 全局设置主题颜色

            ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新";
            ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
            ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
            ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";
            ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
            ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
            ClassicsHeader.REFRESH_HEADER_UPDATE = "最近刷新: M-d HH:mm";
            layout.setHeaderHeight(48f);
            ClassicsHeader mHeader = new ClassicsHeader(context);
            mHeader.setTextSizeTitle(14f);
            // mHeader.setProgressResource(R.mipmap.ic_sun);
            return mHeader;
            // .setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });

        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
            ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
            ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
            ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
            ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
            ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
            ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";

            ClassicsFooter mBallPulseFooter = new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            mBallPulseFooter.setTextSizeTitle(14f);
            // mBallPulseFooter.setProgressResource(R.mipmap.ic_sun);
            // 指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

}
