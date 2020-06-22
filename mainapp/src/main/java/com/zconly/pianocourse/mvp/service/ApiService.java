package com.zconly.pianocourse.mvp.service;

import com.zconly.pianocourse.bean.BalanceBean;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.BoughtRecordBean;
import com.zconly.pianocourse.bean.ChangeRecordBean;
import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CouponBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.FavoriteBean;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.NoticeBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.bean.TokenBean;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.bean.UserDataBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.bean.VideoPackBean;
import com.zconly.pianocourse.bean.XiaoeTokenBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @Description: API
 * @Author: dengbin
 * @CreateDate: 2020/3/18 14:35
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 14:35
 * @UpdateRemark: 更新说明
 */
public interface ApiService {

    @POST("app/user/signIn")
    Observable<TokenBean.TokenResult> signIn(@Body Map<String, String> body);

    @POST("app/user/short-msg/signUp")
    Observable<BaseBean> signUpCode(@Body Map<String, String> params);

    @GET("app/user/signOut")
    Observable<UserBean> signOut(@QueryMap Map<String, String> params);

    @POST("app/user/reset-password")
    Observable<UserBean.UserResult> resetPassword(@Body Map<String, String> params);

    @POST("app/user/short-msg/retrieve")
    Observable<BaseBean> retrieve(@Body Map<String, String> params);

    @POST("app/user/short-msg/verify")
    Observable<BaseBean> verify(@Body Map<String, String> params);

    @POST("app/user/completion")
    Observable<UserDataBean.SetInfoResult> completion(@Body Map<String, String> params);

    @POST("app/user/update")
    Observable<UserBean.UserResult> updateUser(@Body Map<String, String> params);

    @POST("app/user/feedback")
    Observable<BaseBean> feedback(@Body Map<String, String> params);

    @GET("app/user/info")
    Observable<UserBean.UserResult> userInfo(@QueryMap Map<String, String> params);

    @GET("app/lesson-banner/list")
    Observable<BannerBean.BannerListResult> getBanner();

    @Multipart
    @POST("app/sys/upload-file")
    Observable<FileBean> uploadFile(@Part MultipartBody.Part body);

    // 上传练习
    @POST("app/user/upload-exercise")
    Observable<ExerciseBean.ExerciseResult> uploadExercise(@Body RequestBody body);

    // 课程列表
    @POST("app/lesson/list")
    Observable<CourseBean.CourseListResult> getCourseList(@Body Map<String, Object> params);

    // 视频包 lesson_id
    @GET("app/lesson-videopack/list")
    Observable<VideoPackBean.VideoPackResult> getCourseVideoPack(@QueryMap Map<String, String> params);

    // 视频包下的视频 videoPack_id
    @GET("app/lesson-videopack-video/list")
    Observable<VideoBean.VideoListResult> getVideopackVideo(@QueryMap Map<String, String> params);

    // 课程内视频列表
    @GET("app/lesson-video/list")
    Observable<VideoBean.VideoListResult> getVideoList(@QueryMap Map<String, String> params);

    // comment
    @POST("app/lesson-video-comment/add")
    Observable<CommentBean.CommentResult> addComment(@Body Map<String, String> params);

    @POST("app/lesson-video-comment/list")
    Observable<CommentBean.CommentListResult> getComment(@Body Map<String, Object> params);

    @POST("app/system-notice/list")
    Observable<NoticeBean.NoticeResult> getNoticeList(@Body Map<String, String> params);

    @POST("app/lesson-video-favorite/list")
    Observable<FavoriteBean.FavoriteListResult> getFavoriteList(@Body Map<String, Object> params);

    @POST("app/lesson-video-favorite/add")
    Observable<BaseBean> favoriteVideo(@Body Map<String, Object> params);

    @GET("app/favorite/add")
    Observable<BaseBean> favoriteSheet(@Query("sheet_id") long sheetId);

    @POST("app/book/list")
    Observable<BookBean.BookListResult> getBookList(@Body Map<String, Object> params);

    // 课程下的单曲
    @POST("app/sheet/list")
    Observable<SheetBean.SheetListResult> getSheetList(@Body Map<String, Object> params);    // 课程下的单曲

    @GET("app/sheet-watch/add")
    Observable<BaseBean> addSheetWatch(@Query("sheet_id") long sheetId);

    // 学生练习列表
    @POST("app/user/exercise/list")
    Observable<ExerciseBean.ExerciseListResult> getExerciseList(@Body Map<String, Object> params);

    // 点赞视频
    @GET("app/lesson-video-like/add")
    Observable<BaseBean> like(@QueryMap Map<String, Object> params);

    // 点赞评论 id
    @GET("app/lesson-video-comment-thumbup/add")
    Observable<BaseBean> likeComment(@QueryMap Map<String, Object> params);

    // 增加查看 lv_id
    @GET("app/lesson-video-watch/add")
    Observable<BaseBean> addVideoWatch(@QueryMap Map<String, Object> params);

    @GET("app/system-ext/login")
    Observable<XiaoeTokenBean.XiaoeTokenResult> xiaoeLogin();

    @POST("app/evaluate/list")
    Observable<EvaluateBean.EvaluateListResult> getEvaluateList(@Body Map<String, Object> params);

    @POST("app/cash-coupon/my-list")
    Observable<CouponBean.CouponListResult> getMyCoupon(@Body Map<String, Object> params);

    @GET("app/cash-coupon/obtain")
    Observable<BaseBean> obtainCoupon(@Query("identifier") String identifier);

    @GET("app/account/balance")
    Observable<BalanceBean.BalanceResult> balance();

    @POST("app/account/change_record")
    Observable<ChangeRecordBean.ChangeRecordListResult> changeRecord(@Body Map<String, Object> params);

    @POST("app/account/bought_record")
    Observable<BoughtRecordBean.BoughtRecordListResult> boughtRecord(@Body Map<String, Object> params);

    @POST("app/account/buy")
    Observable<BaseBean> buy(@Body Map<String, Object> params);

}
