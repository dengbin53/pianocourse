package com.zconly.pianocourse.bean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 20:14
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 20:14
 * @UpdateRemark: 更新说明
 */
public class TokenBean extends BaseBean {
    private String token;
    private String imtoken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }
}
