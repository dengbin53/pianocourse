package com.mvp.utils;

import android.text.TextUtils;

import com.mvp.interceptor.DownloadInterceptor;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitUtils {

    private static String HOST;
    private static String HOST_H5;
    private static boolean DEBUG;
    private static Interceptor INTERCEPTOR;
    private static Retrofit RETROFIT_BASE;
    private static Retrofit RETROFIT_DOWNLOAD;
    private static Retrofit RETROFIT_H5;

    public static void init(String host, String hostH5, Interceptor interceptor, boolean debug) {
        HOST = host;
        HOST_H5 = hostH5;
        INTERCEPTOR = interceptor;
        DEBUG = debug;
    }

    private static Retrofit getRetrofit() {
        return RETROFIT_BASE == null ? RETROFIT_BASE = newRetrofit(HOST) : RETROFIT_BASE;
    }

    private static Retrofit getRetrofitH5() {
        return RETROFIT_H5 == null ? RETROFIT_H5 = newRetrofit(HOST_H5) : RETROFIT_H5;
    }

    private static Retrofit newRetrofit(String host) {
        if (TextUtils.isEmpty(host) || INTERCEPTOR == null)
            throw new RuntimeException("please init first!");
        return new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttp3Utils.getOkHttpClient(INTERCEPTOR, DEBUG))
                .build();
    }

    public static Retrofit getDownloadRetrofit(ProgressResponseListener progressResponseListener) {
        if (RETROFIT_DOWNLOAD != null)
            return RETROFIT_DOWNLOAD;
        return RETROFIT_DOWNLOAD = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttp3Utils.getOkHttpClient(new DownloadInterceptor(progressResponseListener), DEBUG))
                .build();
    }

    public static <T> T create(Class<T> service) {
        return getRetrofit().create(service);
    }

    public static <T> T createH5(Class<T> service) {
        return getRetrofitH5().create(service);
    }

}
