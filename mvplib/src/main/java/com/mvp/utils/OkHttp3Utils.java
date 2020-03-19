package com.mvp.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2019-12-24 15:13
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-24 15:13
 * @UpdateRemark: 更新说明
 */
public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;
    private static long TIME_OUT = 24; // 超时时间

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient(Interceptor interceptor, final boolean DEBUG) {
        if (null == mOkHttpClient) {
            // 日志拦截器
            HttpLoggingInterceptor li = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (DEBUG)
                        Log.i("OkHttp", message);
                }
            });
            li.setLevel(HttpLoggingInterceptor.Level.BODY);
            HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(li)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .hostnameVerifier(hv)
                    // .cache(cache) // 设置缓存
                    .build();
        }
        return mOkHttpClient;
    }

   /* private static String getUserAgent() {
        String userAgent;
        try {
            userAgent = WebSettings.getDefaultUserAgent(MyApplication.getInstance().getApplicationContext());
        } catch (Exception e) {
            userAgent = System.getProperty("http.agent");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }*/

    /**
     * 自动管理Cookies
     */
    /*private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getInstance()
            .getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }*/

}
