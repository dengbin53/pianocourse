package com.zconly.pianocourse.mvp.service;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.result.TokenResult;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.bean.result.UserResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * @Description: host
 * @Author: dengbin
 * @CreateDate: 2020/3/18 14:35
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 14:35
 * @UpdateRemark: 更新说明
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("app/user/signIn")
    Observable<TokenResult> signIn(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/user/short-msg/signUp")
    Observable<UserBean> signUp(@FieldMap Map<String, String> params);

    @GET("app/user/signOut")
    Observable<UserBean> signOut(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/user/reset-password")
    Observable<UserResult> resetPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/user/short-msg/retrieve")
    Observable<BaseBean> retrieve(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("app/user/short-msg/verify")
    Observable<BaseBean> verify(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/user/completion")
    Observable<UserResult> completion(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/user/update")
    Observable<UserResult> updateUser(@FieldMap Map<String, String> params);

    @Multipart
    @POST("app/sys/upload-file")
    Observable<FileBean> uploadFile(@Part MultipartBody.Part body);

}
