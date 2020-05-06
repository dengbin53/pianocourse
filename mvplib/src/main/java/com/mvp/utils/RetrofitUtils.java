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
    private static Retrofit RETROFIT_DOWNLOAD_H5;
    private static Retrofit RETROFIT_H5;

    public static void init(String host, String hostH5, Interceptor interceptor, boolean debug) {
        HOST = host;
        HOST_H5 = hostH5;
        INTERCEPTOR = interceptor;
        DEBUG = debug;
    }

    public static Retrofit getDownloadRetrofitH5(DownloadInterceptor interceptor) {
        if (RETROFIT_DOWNLOAD_H5 != null)
            return RETROFIT_DOWNLOAD_H5;
        return RETROFIT_DOWNLOAD_H5 = new Retrofit.Builder()
                .baseUrl(HOST_H5)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttp3Utils.getOkHttpClient(interceptor, DEBUG))
                .build();
    }

    public static <T> T create(Class<T> service) {
        return getRetrofit().create(service);
    }

    public static <T> T createH5(Class<T> service) {
        return getRetrofitH5().create(service);
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

}
