package com.zconly.pianocourse.mvp.service;

import com.zconly.pianocourse.BuildConfig;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.LiveBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Api地址
 * Created by dengbin
 */
public interface H5Service {

    String FILE_HOST = BuildConfig.HOST_H5 + "file/";
    // 服务条款和隐私协议
    String TERMS_OF_SERVICE = BuildConfig.HOST_H5 + "fwtk.html";

    @GET("temp/liveconfig/liveconfig.json")
    Observable<LiveBean> getLiveData();
}
