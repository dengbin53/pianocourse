package com.zconly.pianocourse.constants;

import com.zconly.pianocourse.bean.UserBean;

/**
 * @Description: 静态常量
 * @Author: dengbin
 * @CreateDate: 2020/3/18 10:34
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 10:34
 * @UpdateRemark: 更新说明
 */
public class Constants {

    // 分页大小
    public static final int PAGE_COUNT = 12;
    public static final String CS_CALL = "+86 130000000";
    public static UserBean USER_BEAN;
    public static String CLIENT_VERSION;
    public static String IMEI;
    public static String UMENG_CHANNEL;

    public static final int TYPE_FAVORITE_COURSE = 0;
    public static final int TYPE_FAVORITE_VIDEO = 1;

    public static final int TYPE_BANNER_COURSE = 0;

    public static final float BANNER_ASPECT_RATIO_0 = 16f / 9f;
    public static final float VIDEO_ASPECT_RATIO = 16f / 9f;

    // 文件类型
    public static final int TYPE_FILE_IMAGE = 0;
    public static final int TYPE_FILE_VIDEO = 1;
    // 小数点位数
    public static final int DECIMAL_DIGITS = 2;

    public static final String END_LIKE = "次点赞";
    public static final String END_FAVORITE = "次收藏";

    public static final int TYPE_ROLE_ADMIN = 1; // 管理员
    public static final int TYPE_ROLE_STUDENT = 2; // 学生
    public static final int TYPE_ROLE_TEACHER = 3; // 老师

    public static final int VIEW_TYPE_PACK = 0; // 视频包
    public static final int VIEW_TYPE_VIDEO = 1; // 视频

    public static final int CATEGORY_PARENTS_COURSE = 0x0; // 家长课
    public static final int CATEGORY_TEACHER_COURSE = 0x1; // 老师课
    public static final int CATEGORY_OTHER_COURSE = 0x2; // 曲目精讲
    public static final int CATEGORY_FAVORITE_COURSE = 0x100; // 收藏的课程

    public static final String BAI_DU_AK = "958ec87f47f8455281435033c76eacfe"; // 邓斌

    public static final String XIAO_E_CLIENT_ID = "JC68kYJYpYMD";
    public static final String XIAO_E_APP_ID = "appoxlmAiwS3515";

}
