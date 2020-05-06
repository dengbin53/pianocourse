package com.mvp.interceptor;

import android.text.TextUtils;

import com.mvp.utils.ProgressResponseBody;
import com.mvp.utils.ProgressResponseListener;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：RR 邮箱：RR@chengantech.com
 * 时间：2018/2/5  下午4:01
 * 描述：
 */
public abstract class DownloadInterceptor implements Interceptor {

    private ProgressResponseListener listener;

    public DownloadInterceptor(ProgressResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        Map<String, String> headers = getHeader();
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            if (!TextUtils.isEmpty(value))
                builder.addHeader(key, value);
        }

        Response response = chain.proceed(request);

        return response.newBuilder().body(new ProgressResponseBody(response.body(), listener)).build();
    }

    public abstract Map<String, String> getHeader();

}
