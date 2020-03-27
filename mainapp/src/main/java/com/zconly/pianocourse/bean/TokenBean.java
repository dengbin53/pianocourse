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
    private long expire;
    private String token;

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
