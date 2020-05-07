package com.zconly.pianocourse.mvp.service;

import com.zconly.pianocourse.BuildConfig;
import com.zconly.pianocourse.bean.HomePageBean;
import com.zconly.pianocourse.bean.InvitationBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Api地址
 * Created by dengbin
 */
public interface H5Service {

    String FILE_HOST = BuildConfig.HOST_H5 + "file/";
    String IMG_HOST = BuildConfig.HOST_H5 + "conf/img/";
    String MP3_HOST = "mp3/";
    // 服务条款和隐私协议
    String TERMS_OF_SERVICE = BuildConfig.HOST_H5 + "conf/terms/fwtk.html";
    // 管理员头像
    String ADMIN_AVATAR = IMG_HOST + "admin_avatar.jpg";

    String IMG_COURSE_PARENT_BANNER = IMG_HOST + "course_parent_banner.jpg";
    String IMG_COURSE_TEACHER_BANNER = H5Service.IMG_HOST + "course_teacher_banner.jpg";
    String IMG_COURSE_TRACK_BANNER = H5Service.IMG_HOST + "course_track_banner.jpg";

    @GET("conf/homepage/homepage.json")
    Observable<HomePageBean.HomePageResult> getHomePageJson();

    // 邀请码
    @GET("conf/invitationcode/invitationcode.json")
    Observable<InvitationBean> getInvitationCode();

    // 下载文件
    @Streaming
    @GET("{dir}{url}")
    Observable<ResponseBody> downloadFile(@Path(value = "dir", encoded = true) String dir,
                                          @Path(value = "url", encoded = true) String fileUrl);

}
