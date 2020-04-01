package com.zconly.pianocourse.mvp.service;

/**
 * Api地址
 * Created by dengbin
 */
public class H5Service {
    public static final String FOLLOW = "follow/addFollow";
    public static final String CANCEL_FOLLOW = "follow/cancelFollow";
    public static final String GET_SERVICE = "user/guideService";
    public static final String GET_COMMENT_LIST = "comment/getCommentList";
    public static final String GET_SCHEDULE_LIST = "schedule/active-schedulelist";
    public static final String DELETE_SCHEDULE = "schedule/actvie-delschedule";
    public static final String ADD_SCHEDULE = "schedule/active-addschedule";
    public static final String UPLOAD_PAYMENT = "order/upPayment";
    public static final String COMMENT = "comment/commentGuide";
    public static final String GET_CONTACTS = "address/getAddressList";
    public static final String ADD_CONTACTS = "address/addToAddress";
    public static final String DELETE_CONTACTS = "address/delAddress";


    // 网页

    // 中文
    private static final String CN = "https://musicdata.tech/";

    private static String getHost() {
        return CN;
    }

    public static final String FILE_HOST = getHost() + "file/";

    // 服务费用说明
    public static final String CS_CHARGE = getHost() + "guwen/fwfy.html";
    // 注意事项 常见问题
    public static final String ATTENTION = getHost() + "guwen/cjwt.html";
    // 服务条款和隐私协议
    public static final String TERMS_OF_SERVICE = getHost() + "fwtk.html";
    // 关于我们
    public static final String ABOUT = getHost() + "gywm.html";

}
