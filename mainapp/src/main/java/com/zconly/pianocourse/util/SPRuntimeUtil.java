package com.zconly.pianocourse.util;

/**
 * SharedPreferences的工具类：一行代码完成操作！
 *
 * @author liuzhao
 */
public class SPRuntimeUtil {

    private static final String SHARED_PREF_RUNTIME = "runtime";
    private static final String SHARED_PREF_FIRST_START = "isFirstStart";// 是否第一次启动。
    private static final String SHARED_PREF_RUNTIME_NOTIFYURL = "notifyurl";
    private static final String SHARED_PREF_RUNTIME_USERID = "userid";
    private static final String SHARED_PREF_RUNTIME_TOKEN = "token";
    private static final String SHARED_PREF_RUNTIME_USERTYPE = "usertype";
    private static final String SHARED_PREF_RUNTIME_LOGINTYPE = "logintype";
    private static final String SHARED_PREF_RUNTIME_USERCATEID = "usercateid";
    private static final String SHARED_PREF_RUNTIME_NICKNAME = "nickname";
    private static final String SHARED_PREF_RUNTIME_ISCHECK = "ischeck";
    private static final String SHARED_PREF_RUNTIME_ISGOV = "isgov";
    private static final String SHARED_PREF_RUNTIME_GENDER = "gender";
    private static final String SHARED_PREF_RUNTIME_LEVEL = "level";
    private static final String SHARED_PREF_RUNTIME_USERICON = "usericon";
    private static final String SHARED_PREF_RUNTIME_SCHOOL = "school";// 院校
    private static final String SHARED_PREF_RUNTIME_GRADE = "grade";// 年级
    private static final String SHARED_PREF_RUNTIME_PROVINCE = "province";// 省份
    private static final String SHARED_PREF_RUNTIME_CITY = "city";// 城市
    private static final String SHARED_PREF_RUNTIME_STUDIO = "studio";// 画室
    private static final String SHARED_PREF_RUNTIME_STUDIOID = "studioid";// 画室ID
    private static final String SHARED_PREF_RUNTIME_PROFESSION = "profession";// 画室
    private static final String SHARED_PREF_RUNTIME_PHONE = "userphone";
    private static final String SHARED_PREF_RUNTIME_LOGIN_PHONE = "loginphone";
    private static final String SHARED_PREF_RUNTIME_LOGIN_OPENID = "loginopenid";
    private static final String SHARED_PREF_RUNTIME_IS_PASSWORD = "ispassword";
    private static final String SHARED_PREF_RUNTIME_NOTIFY = "NOTIFY";
    private static final String SHARED_PREF_RUNTIME_IMG = "image";
    private static final String SHARED_PREF_RUNTIME_VERSIONCODE = "versionCode";
    private static final String SHARED_PREF_RUNTIME_SIGN = "sign";
    private static final String SHARED_PREF_RUNTIME_DOWNLOAD_PATH = "download_save_path"; // 下载文件保存的路径
    private static final String SHARED_PREF_RUNTIME_VOICE = "voice";
    private static final String SHARED_PREF_RUNTIME_VIBRATION = "vibration";
    private static final String SHARED_PREF_STARTUP_ADV_IMAGE = "shared_pref_startup_adv_image";
    private static final String SHARED_PREF_STARTUP_ADV_URL = "shared_pref_startup_adv_url";
    private static final String TAB_ORDER = "tab_order"; // 帖子排序是否可以手动调整
    private static final String LOCAL_ORDER = "local_order";
    private static final String SHARED_PREF_RUNTIME_INVITECODE = "inviteCode";
    private static final String SHARED_LAT = "lat";
    private static final String SHARED_LON = "lon";
    private static final String SHARED_ADDR = "addr";
    private static final String NOTIFY_SETTING = "noticSetting";
    private static final String SHARED_PREF_RUNTIME_ANSWER_SAVE_CONTENT = "answerSaveContent";
    private static final String SHARED_PREF_RUNTIME_ANSWER_SAVE_QUESTID = "answerSaveQuestid";
    private static final String AGREEMENT_AGREE = "agreement_agree";
    private static final String SHARED_PREF_RUNTIME_ANSWER_SAVE_TEACHERID = "answerSaveTeacherid";
    private static final String HOME_ADS = "homeads";
    private static final String FOUND_RED = "found_red";
    private static final String FOUND_ARTPIC = "found_artpic";
    private static final String FOUND_JPKEJIAN = "found_jpkejian";
    private static final String FOUND_CALLTEA = "found_calltea";
    private static final String FOUND_ARTTEA = "found_arttea";
    private static final String FOUND_HOTTOPICS = "found_hottopics";
    private static final String FOUND_INSTEREST = "found_insterest";
    private static final String FOUND_NEAR = "found_near";
    private static final String FOUND_ARTIFACT = "found_artifact";
    private static final String FOUND_ZHIBO_JIAOXUE = "found_zbjiaoxue";
    private static final String FOUND_FANHUA_SHIPIN = "found_fhshipin"; // 美术院校
    private static final String FOUND_ART_COLLEGE = "found_art_college";
    private static final String FOUND_ARTICLE = "found_article"; // 文章推荐
    private static final String SHARED_PREF_RUNTIME_START = "start";

    private static SPHelper getSp() {
        return SPHelper.getInstance(SHARED_PREF_RUNTIME);
    }

    public static long getLong(String key, int def) {
        return getSp().getLong(key, def);
    }

    public static void setLong(String key, long l) {
        getSp().putLong(key, l);
    }

    public static void setVoice(int voice) {
        getSp().putInt(SHARED_PREF_RUNTIME_VOICE, voice);
    }

    public static int getVoice() {
        return getSp().getInt(SHARED_PREF_RUNTIME_VOICE, 0);
    }

    public static void setVibration(int vibration) {
        getSp().putInt(SHARED_PREF_RUNTIME_VIBRATION, vibration);
    }

    public static int getVibration() {
        return getSp().getInt(SHARED_PREF_RUNTIME_VIBRATION, 0);
    }

    public static int getDownloadPath() {
        return getSp().getInt(SHARED_PREF_RUNTIME_DOWNLOAD_PATH, 1);
    }

    public static void setDownloadPath(int dp) {
        getSp().putInt(SHARED_PREF_RUNTIME_DOWNLOAD_PATH, dp);
    }

    // 启动页广告图片地址
    public static String getSplashAdvImg() {
        return getSp().getString(SHARED_PREF_STARTUP_ADV_IMAGE, "");
    }

    public static void setSplashAdvImg(String img) {
        getSp().putString(SHARED_PREF_STARTUP_ADV_IMAGE, img);
    }

    public static String getSplashAdvUrl() {
        return getSp().getString(SHARED_PREF_STARTUP_ADV_URL, "");
    }

    public static void setSplashAdvUrl(String url) {
        getSp().putString(SHARED_PREF_STARTUP_ADV_URL, url);
    }

    public static String getTabOrder() {
        return getSp().getString(TAB_ORDER, "");
    }

    public static void setTabOrder(String tb) {
        getSp().putString(TAB_ORDER, tb);
    }

    public static boolean getLocalOrder() {
        return getSp().getBoolean(LOCAL_ORDER, false);
    }

    public static void setLocalOrder(boolean lo) {
        getSp().putBoolean(LOCAL_ORDER, lo);
    }

    public static String getNotifyUrl() {
        return getSp().getString(SHARED_PREF_RUNTIME_NOTIFYURL, "");
    }

    public static void setNotifyUrl(String url) {
        getSp().putString(SHARED_PREF_RUNTIME_NOTIFYURL, url);
    }

    public static String getUserId() {
        return getSp().getString(SHARED_PREF_RUNTIME_USERID, "");
    }

    public static void setUserId(String uid) {
        getSp().putString(SHARED_PREF_RUNTIME_USERID, uid);
    }

    public static String getToken() {
        return getSp().getString(SHARED_PREF_RUNTIME_TOKEN, "");
    }

    public static void setToken(String token) {
        getSp().putString(SHARED_PREF_RUNTIME_TOKEN, token);
    }

    public static int getLoginType() {
        return getSp().getInt(SHARED_PREF_RUNTIME_LOGINTYPE, -1);
    }

    public static void setLoginType(int type) {
        getSp().putInt(SHARED_PREF_RUNTIME_LOGINTYPE, type);
    }

    public static int getUserType() {
        return getSp().getInt(SHARED_PREF_RUNTIME_USERTYPE, 0);
    }

    public static void setUserType(int type) {
        getSp().putInt(SHARED_PREF_RUNTIME_USERTYPE, type);
    }

    public static int getUserCateId() {
        return getSp().getInt(SHARED_PREF_RUNTIME_USERCATEID, 0);
    }

    public static void setUserCateId(int cid) {
        getSp().putInt(SHARED_PREF_RUNTIME_USERCATEID, cid);
    }

    public static String getNickName() {
        return getSp().getString(SHARED_PREF_RUNTIME_NICKNAME, "");
    }

    public static void setNickName(String nickName) {
        getSp().putString(SHARED_PREF_RUNTIME_NICKNAME, nickName);
    }

    public static boolean isCheck() {
        return getSp().getBoolean(SHARED_PREF_RUNTIME_ISCHECK, false);
    }

    public static void setCheck(boolean check) {
        getSp().putBoolean(SHARED_PREF_RUNTIME_ISCHECK, check);
    }

    public static boolean isGov() {
        return getSp().getBoolean(SHARED_PREF_RUNTIME_ISGOV, false);
    }

    public static void setGov(boolean gov) {
        getSp().putBoolean(SHARED_PREF_RUNTIME_ISGOV, gov);
    }

    public static int getLevel() {
        return getSp().getInt(SHARED_PREF_RUNTIME_LEVEL, 0);
    }

    public static void setLevel(int level) {
        getSp().putInt(SHARED_PREF_RUNTIME_LEVEL, level);
    }

    public static int getGender() {
        return getSp().getInt(SHARED_PREF_RUNTIME_GENDER, 1);
    }

    public static void setGender(int gender) {
        getSp().putInt(SHARED_PREF_RUNTIME_GENDER, gender);
    }

    public static String getIcon() {
        return getSp().getString(SHARED_PREF_RUNTIME_USERICON, "");
    }

    public static void setIcon(String icon) {
        getSp().putString(SHARED_PREF_RUNTIME_USERICON, icon);
    }

    public static boolean isPassWord() {
        return getSp().getBoolean(SHARED_PREF_RUNTIME_IS_PASSWORD, false);
    }

    public static void setPassWord(boolean ipw) {
        getSp().putBoolean(SHARED_PREF_RUNTIME_IS_PASSWORD, ipw);
    }

    public static String getLoginNumber() {
        return getSp().getString(SHARED_PREF_RUNTIME_LOGIN_PHONE, "");
    }

    public static void setLoginNumber(String number) {
        getSp().putString(SHARED_PREF_RUNTIME_LOGIN_PHONE, number);
    }

    public static String getLoginOpenId() {
        return getSp().getString(SHARED_PREF_RUNTIME_LOGIN_OPENID, "");
    }

    public static void setLoginOpenId(String id) {
        getSp().putString(SHARED_PREF_RUNTIME_LOGIN_OPENID, id);
    }

    public static String getPhoneNumber() {
        return getSp().getString(SHARED_PREF_RUNTIME_PHONE, "");
    }

    public static void setPhoneNumber(String number) {
        getSp().putString(SHARED_PREF_RUNTIME_PHONE, number);
    }

    public static String getGrade() {
        return getSp().getString(SHARED_PREF_RUNTIME_GRADE, "");
    }

    public static void setGrade(String grade) {
        getSp().putString(SHARED_PREF_RUNTIME_GRADE, grade);
    }

    public static String getSchool() {
        return getSp().getString(SHARED_PREF_RUNTIME_SCHOOL, "");
    }

    public static void setSchool(String school) {
        getSp().putString(SHARED_PREF_RUNTIME_SCHOOL, school);
    }

    public static String getProvince() {
        return getSp().getString(SHARED_PREF_RUNTIME_PROVINCE, "");
    }

    public static void setProvince(String province) {
        getSp().putString(SHARED_PREF_RUNTIME_PROVINCE, province);
    }

    public static String getCity() {
        return getSp().getString(SHARED_PREF_RUNTIME_CITY, "");
    }

    public static void setCity(String city) {
        getSp().putString(SHARED_PREF_RUNTIME_CITY, city);
    }

    public static String getStudio() {
        return getSp().getString(SHARED_PREF_RUNTIME_STUDIO, "");
    }

    public static void setStudio(String studio) {
        getSp().putString(SHARED_PREF_RUNTIME_STUDIO, studio);
    }

    public static String getStudioId() {
        return getSp().getString(SHARED_PREF_RUNTIME_STUDIOID, "");
    }

    public static void setStudioId(String id) {
        getSp().putString(SHARED_PREF_RUNTIME_STUDIOID, id);
    }

    public static String getProfession() {
        return getSp().getString(SHARED_PREF_RUNTIME_PROFESSION, "");
    }

    public static void setProfession(String profession) {
        getSp().putString(SHARED_PREF_RUNTIME_PROFESSION, profession);
    }

    public static int getNotify() {
        return getSp().getInt(SHARED_PREF_RUNTIME_NOTIFY, 0);
    }

    public static void setNotify(int off) {
        getSp().putInt(SHARED_PREF_RUNTIME_NOTIFY, off);
    }

    public static int getVersionCode() {
        return getSp().getInt(SHARED_PREF_RUNTIME_VERSIONCODE, 0);
    }

    public static void setVersionCode(int code) {
        getSp().putInt(SHARED_PREF_RUNTIME_VERSIONCODE, code);
    }

    public static String getInviteCode() {
        return getSp().getString(SHARED_PREF_RUNTIME_INVITECODE, "");
    }

    public static void getInviteCode(String code) {
        getSp().putString(SHARED_PREF_RUNTIME_INVITECODE, code);
    }

    public static String getSign() {
        return getSp().getString(SHARED_PREF_RUNTIME_SIGN, "");
    }

    public static void setSign(String sign) {
        getSp().putString(SHARED_PREF_RUNTIME_SIGN, sign);
    }

    public static int getImg(int def) {
        return getSp().getInt(SHARED_PREF_RUNTIME_IMG, def);
    }

    public static void setImg(int img) {
        getSp().putInt(SHARED_PREF_RUNTIME_IMG, img);
    }

    public static boolean getFirstStart() {
        return getSp().getBoolean(SHARED_PREF_FIRST_START, true);
    }

    public static void setFirstStart(boolean firstStart) {
        getSp().putBoolean(SHARED_PREF_FIRST_START, firstStart);
    }

    public static String getLat() {
        return getSp().getString(SHARED_LAT, "");
    }

    public static void setLat(String lat) {
        getSp().putString(SHARED_LAT, lat);
    }

    public static String getLon() {
        return getSp().getString(SHARED_LON, "");
    }

    public static void setLon(String lon) {
        getSp().putString(SHARED_LON, lon);
    }

    public static String getAddr() {
        return getSp().getString(SHARED_ADDR, "");
    }

    public static void setAddr(String addr) {
        getSp().putString(SHARED_ADDR, addr);
    }

    public static void setNotifySetting(int i) {
        getSp().putInt(NOTIFY_SETTING, i);
    }

    public static int getNotifySetting() {
        return getSp().getInt(NOTIFY_SETTING, 1);
    }

    public static String getAnswerSaveQuestId() {
        return getSp().getString(SHARED_PREF_RUNTIME_ANSWER_SAVE_QUESTID, "");
    }

    public static void setAnswerSaveQuestid(String s) {
        getSp().putString(SHARED_PREF_RUNTIME_ANSWER_SAVE_QUESTID, s);
    }

    public static String getAnswerSaveContent() {
        return getSp().getString(SHARED_PREF_RUNTIME_ANSWER_SAVE_CONTENT, "");
    }

    public static void setAnswerSaveContent(String content) {
        getSp().putString(SHARED_PREF_RUNTIME_ANSWER_SAVE_CONTENT, content);
    }

    public static String getAnswerSaveTeacherid() {
        return getSp().getString(SHARED_PREF_RUNTIME_ANSWER_SAVE_TEACHERID, "");
    }

    public static void setAnswerSaveTeacherid(String id) {
        getSp().putString(SHARED_PREF_RUNTIME_ANSWER_SAVE_TEACHERID, id);
    }

    public static String getHomeAds() {
        return getSp().getString(HOME_ADS, "");
    }

    public static void setHomeAds(String adv) {
        getSp().putString(HOME_ADS, adv);
    }

    public static String getFoundRed() {
        return getSp().getString(FOUND_RED, "0");
    }

    public static void setFoundRed(String fd) {
        getSp().putString(FOUND_RED, fd);
    }

    public static String getFoundArtCollege() {
        return getSp().getString(FOUND_ART_COLLEGE, "");
    }

    public static void setFoundArtCollege(String artCollegeStr) {
        getSp().putString(FOUND_ART_COLLEGE, artCollegeStr);
    }

    public static String getFoundArtIcle() {
        return getSp().getString(FOUND_ARTICLE, "");
    }

    public static void setFoundArtIcle(String articleStr) {
        getSp().putString(FOUND_ARTICLE, articleStr);
    }

    public static String getFoundArtTea() {
        return getSp().getString(FOUND_ARTTEA, "");
    }

    public static void setFoundArtTea(String foundArttea) {
        getSp().putString(FOUND_ARTTEA, foundArttea);
    }

    public static String getFoundArtPic() {
        return getSp().getString(FOUND_ARTPIC, "");
    }

    public static void setFoundArtPic(String foundArtpic) {
        getSp().putString(FOUND_ARTPIC, foundArtpic);
    }

    public static String getFoundNear() {
        return getSp().getString(FOUND_NEAR, "");
    }

    public static void setFoundNear(String foundNear) {
        getSp().putString(FOUND_NEAR, foundNear);
    }

    public static String getFoundHotTopics() {
        return getSp().getString(FOUND_HOTTOPICS, "");
    }

    public static void setFoundHotTopics(String foundHotTopics) {
        getSp().putString(FOUND_HOTTOPICS, foundHotTopics);
    }

    public static String getFoundArtIfact() {
        return getSp().getString(FOUND_ARTIFACT, "");
    }

    public static void setFoundArtIfact(String foundArtifact) {
        getSp().putString(FOUND_ARTIFACT, foundArtifact);
    }

    public static String getFoundJpKeJian() {
        return getSp().getString(FOUND_JPKEJIAN, "");
    }

    public static void setFoundJpKeJian(String foundJpKejian) {
        getSp().putString(FOUND_JPKEJIAN, foundJpKejian);
    }

    public static String getFoundZhiboJiaoxue() {
        return getSp().getString(FOUND_ZHIBO_JIAOXUE, "");
    }

    public static void setFoundZhiboJiaoxue(String foundZbJiaoxue) {
        getSp().putString(FOUND_ZHIBO_JIAOXUE, foundZbJiaoxue);
    }

    public static String getFoundFanhuaShipin() {
        return getSp().getString(FOUND_FANHUA_SHIPIN, "");
    }

    public static void setFoundFanhuaShipin(String foundFhShipin) {
        getSp().putString(FOUND_FANHUA_SHIPIN, foundFhShipin);
    }

    public static String getFoundCallTee() {
        return getSp().getString(FOUND_CALLTEA, "");
    }

    public static void setFoundCallTee(String foundFhShipin) {
        getSp().putString(FOUND_CALLTEA, foundFhShipin);
    }

    public static String getFoundInsterest() {
        return getSp().getString(FOUND_INSTEREST, "");
    }

    public static void setFoundInsterest(String foundFhShipin) {
        getSp().putString(FOUND_INSTEREST, foundFhShipin);
    }

    public static int getStart() {
        return getSp().getInt(SHARED_PREF_RUNTIME_START, 0);
    }

    public static void setStart(int start) {
        getSp().putInt(SHARED_PREF_RUNTIME_START, start);
    }

    public static boolean getAgreementAgree() {
        return getSp().getBoolean(AGREEMENT_AGREE, false);
    }

    public static void setAgreementAgree(boolean b) {
        getSp().putBoolean(AGREEMENT_AGREE, b);
    }
}
