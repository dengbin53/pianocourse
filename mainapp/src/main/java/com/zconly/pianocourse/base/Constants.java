package com.zconly.pianocourse.base;

import com.zconly.pianocourse.bean.UserBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/18 10:34
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 10:34
 * @UpdateRemark: 更新说明
 */
public class Constants {
    public static final int PAGE_COUNT = 12;
    public static UserBean USER_BEAN;
    public static String NETWORK;
    public static String CLIENT_VERSION;
    public static String IMEI;
    public static String UMENG_CHANNEL;
    public static String TOKEN;


    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    public static final int VIEW_TYPE_HEADER = 2;
    public static final int VIEW_TYPE_NORMAL_2 = 3;

    // 分页大小
    public static final int SIZE_LIMIT = 12;
    public static final int LIMIT_CONTENT = 3;
    // 一天的毫秒数
    public static final long DAY_OF_MILLIS = 60000 * 60 * 24;
    // 文件类型
    public static final int TYPE_FILE_IMAGE = 0;
    public static final int TYPE_FILE_VIDEO = 1;
    // 小数点位数
    public static final int DECIMAL_DIGITS = 2;
    // fir token
    public static final String FIR_TOKEN = "040dca072cd700d9a8df39d733ea7e34";

    public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 0x333;
    public static final int EXTRA_REQUEST_IMAGE_FROM_PROGRESS = 0x444;
    // 客服聊天账号
    public static final long CS_ACCOUNT = 21;
    // 客服电话
    public static final String CS_CALL = "+86 4008155144";
    // 正式
    public static final String STRIPE_PUBLISHABLE_KEY = "pk_live_OHlC1rJ4jKvomEEE5MMYGtq9";
    // 测试
    // public static final String STRIPE_PUBLISHABLE_KEY = "pk_test_fcgPjc2LLFunCrc9ytLZXRW7";

    // post
    public static final int POST = 0;
    public static final int POST_FILE = 4;
    // get
    public static final int GET = 1;
    // delete
    public static final int DELETE = 2;
    // put
    public static final int PUT = 3;
    // 上传Image最大值
    public static final int IMAGE_MAX = 1280;
    public static final int IMAGE_NORMAL = 720;

    public static final String CHAT_IMAGE_FILE = "file:///";

    public static final String IMAGE_BIG = "?imageView2/0/w/1280/h/1280";
    public static final String IMAGE_SMALE = "?imageView2/0/w/256/h/256";
    public static final String IMAGE_MIDLE = "?imageView2/0/w/768/h/768";

    public static final int CATEGORY_CLASS_UNKNOWN = 0; // 未知 0
    public static final int CATEGORY_CLASS_EXPERIENCE = 1; // 活动 1
    public static final int CATEGORY_CLASS_DISCOVER = 2; // 发现 2
    public static final int CATEGORY_CLASS_GUIDE = 3; // 引导(pdf) 3
}
