package com.zconly.pianocourse.bean;

/**
 * API总入口
 *
 * @author DengBin
 */
public class SysConfig extends BaseBean {
    private static final long serialVersionUID = 2L;
    private String storeUrl;
    private int beta;
    // 服务器信息
    // api服务器地址，用于api分发
    private String api;
    // Image服务器, 用于图片存储分发
    private String image;
    // Push服务器地址, 用于Push长连接的分发
    private String push;
    private String pushPort;

    // 内容链接信息
    // 隐私协议地址
    private String privacy;
    // 用户协议地址
    private String terms;
    // 联系我们地址
    private String contact;
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPushPort() {
        return pushPort;
    }

    public void setPushPort(String pushPort) {
        this.pushPort = pushPort;
    }

}
