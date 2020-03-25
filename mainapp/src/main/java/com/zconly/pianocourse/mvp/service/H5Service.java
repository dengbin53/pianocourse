package com.zconly.pianocourse.mvp.service;

/**
 * Api地址
 * Created by dengbin
 */
public class H5Service {
    // 用户
    public static final String LOGIN = "user/login";
    public static final String UPDATE_USER_INFO = "user/updateUserInfo";
    public static final String SIGN_UP = "user/register";
    public static final String REGISTER_CHECK = "user/register-check";
    public static final String FIND_PASS = "user/retrieve";
    public static final String REFRESH_TOKEN = "user/refreshToken";
    public static final String FEEDBACK = "user/feedback";
    public static final String GUIDE_BY_CITY = "user/getGuideByCity";
    public static final String AUTHENTICATE = "user/authenticate";
    public static final String GET_GUIDE_LANGUAGE = "user/languageGuide";
    public static final String USER_INFO = "user/getUserInfo";
    public static final String USER_PHOTO_ADD = "user/addPhoto";
    public static final String USER_PHOTO_DELETE = "user/delPhoto";
    public static final String GET_IM_TOKEN = "im/getImToken";
    public static final String REFRESH_IM_TOKEN = "im/refreshImToken";


    // 内容
    public static final String CONTENTS = "content/getcontents";
    public static final String FAVOURITES = "content/getusercollection";
    public static final String FAVOURITE = "content/addCollection";
    public static final String FAVOURITE_CANCEL = "content/cancelCollection";
    public static final String CONTENTS_OF_CITY = "content/getCityContent";
    public static final String CONTENTS_OF_GUIDE = "content/getActives";
    public static final String NEWEST_NEWS = "content/newestNews";
    public static final String NEWS_LIST = "content/getnewslist";
    public static final String BNNER = "content/getbannercontent";
    public static final String BNNER_V2 = "banner/list";
    public static final String GUIDE_PHOTOS = "content/guidePhotos";
    public static final String CONTENT_INFO = "content/getcontentinfo";
    public static final String PUBLISH_ACTIVITY = "content/publish-active";
    // 获取城市内所有体验内容列表：
    public static final String CITY_EXPERIENCE = "content/getCityExperience";
    // 获取城市内所有发现内容列表
    public static final String CITY_DISCOVER = "content/getCityDiscover";
    // 获取城市内所有导览内容列表
    public static final String CITY_GUIDE = "content/getCityGuide";

    // 我的

    public static final String HOT_CITY = "city/hotcity";
    public static final String CITY_LIST = "city/citylist";
    public static final String OPEN_CITY = "city/opencity";
    // 其他
    public static final String UPLOAD_FILE = "sys/upData";
    public static final String UPLOAD_FILE_2 = "sys/back/upData";
    public static final String VERIFY_CODE = "sys/verification";
    public static final String SEND_VERIFY_CODE = "sys/getbannercontent";
    public static final String FOLLOW_LIST = "follow/getFollowList";
    public static final String CLIENT_TOKEN = "";

    // 订单
    public static final String ADD_ORDER = "order/addorder";            // 生成订单
    public static final String CANCEL_ORDER = "order/cancelOrder";      // 取消订单
    public static final String GET_ORDER_INFO = "order/getorderinfo";   // 订单详情
    public static final String GET_ORDER_LIST = "order/getorderlist";   // 订单列表
    public static final String UP_ORDER_PAYMENT = "order/upPayment";    // 支付凭证
    public static final String STRIPE_TOKEN = "order/create-charege";   //

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
    private static final String CN = "https://api.letslocals.com/terms/cn";

    private static String getHost() {
        return CN;
    }

    // 服务费用说明
    public static final String CS_CHARGE = getHost() + "/guwen/fwfy.html";
    // 注意事项 常见问题
    public static final String ATTENTION = getHost() + "/guwen/cjwt.html";
    // 服务条款和隐私协议
    public static final String TERMS_OF_SERVICE = getHost() + "/fwtk.html";
    // 关于我们
    public static final String ABOUT = getHost() + "/gywm.html";
    // 如何成为顾问
    public static final String HOW_TO_GUIDE = getHost() + "http://www.xoneday.com/lets/terms-of-service/guwen/rhcwlxgw.html";// 如何成为顾问
    // 顾问规则
    public static final String GUIDE_RULE = getHost() + "/guwen/gwgz.html";

}
