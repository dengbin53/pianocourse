package com.zconly.pianocourse.bean;

import com.google.gson.Gson;

/**
 * Created by dengbin
 */
public class XiaoeTokenBean extends BaseBean {

    private static final long serialVersionUID = 2200L;

    private int expires;
    private String user_id;
    private String token_key;
    private String token_value;
    private long sdk_user_id;

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken_key() {
        return token_key;
    }

    public void setToken_key(String token_key) {
        this.token_key = token_key;
    }

    public String getToken_value() {
        return token_value;
    }

    public void setToken_value(String token_value) {
        this.token_value = token_value;
    }

    public long getSdk_user_id() {
        return sdk_user_id;
    }

    public void setSdk_user_id(long sdk_user_id) {
        this.sdk_user_id = sdk_user_id;
    }

    public static class XiaoeTokenData extends BaseBean {
        private XiaoeTokenBean data;

        public XiaoeTokenBean getData() {
            return data;
        }

        public void setData(XiaoeTokenBean data) {
            this.data = data;
        }
    }

    public static class XiaoeTokenResult extends BaseBean {

        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public XiaoeTokenData getDataBean() {
            return new Gson().fromJson(data, XiaoeTokenData.class);
        }

    }

}
