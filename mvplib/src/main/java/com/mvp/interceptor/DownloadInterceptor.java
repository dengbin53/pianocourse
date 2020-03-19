package com.mvp.interceptor;

import android.text.TextUtils;

import com.mvp.utils.ProgressResponseBody;
import com.mvp.utils.ProgressResponseListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：RR 邮箱：RR@chengantech.com
 * 时间：2018/2/5  下午4:01
 * 描述：
 */

public class DownloadInterceptor implements Interceptor {

    private ProgressResponseListener listener;

    public DownloadInterceptor(ProgressResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        // TODO: 2019-12-24

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        // String token = SharePreferencesUtil.getStringData("token");
        String token = "";
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("token", token);
        }

        // int userId = SharePreferencesUtil.getIntData("userId");
        int userId = 123;
        if (userId != 0) {
            builder.addHeader("userId", userId + "");
        }

        // int userCode = SharePreferencesUtil.getUserCode();
        int userCode = 123;
        if (userCode != 0) {
            builder.addHeader("userCode", userCode + "");
        }

        Response response = chain.proceed(request);

        return response.newBuilder().body(new ProgressResponseBody(response.body(), listener)).build();
    }

}
