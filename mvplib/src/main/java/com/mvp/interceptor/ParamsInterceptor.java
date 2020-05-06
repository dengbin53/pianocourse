package com.mvp.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 请求的拦截器(get和post 添加公共参数)
 */
public abstract class ParamsInterceptor implements Interceptor {

    private static final MediaType TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    @NonNull
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

        switch (request.method()) {
            case "GET":
                request = methodGet(request, builder);
                break;
            case "DELETE":
            case "POST":
                request = methodPost(request, builder);
                break;
            case "PUT":
                request = methodPut(request, builder);
                break;
        }

        return chain.proceed(request);
    }

    private Request methodPut(Request request, Request.Builder builder) {
        return builder.put(method(request)).build();
    }

    private RequestBody method(Request request) {
        RequestBody requestBody = request.body();

        if (requestBody != null && !(requestBody instanceof FormBody) && !(requestBody instanceof MultipartBody)) {
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null)
                charset = contentType.charset(charset);
            String paramsStr = buffer.readString(charset);
            JSONObject jo;
            try {
                jo = new JSONObject(paramsStr);
            } catch (JSONException e) {
                jo = null;
                e.printStackTrace();
            }
            requestBody = jo == null ? requestBody : RequestBody.create(TYPE_JSON, jo.toString());
        }
        return requestBody;
    }

    private Request methodPost(Request request, Request.Builder builder) {
        return builder.post(method(request)).build();
    }

    private Request methodGet(Request request, Request.Builder builder) {
        HttpUrl httpUrl = request.url().newBuilder().build();
        return builder.url(httpUrl).build();
    }

    protected abstract Map<String, String> getHeader();

}
