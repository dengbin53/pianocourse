package com.zconly.pianocourse.bean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/6 00:13
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/6 00:13
 * @UpdateRemark: 更新说明
 */
public class UserDataBean extends BaseBean {

    private TokenBean token;
    private UserBean user;

    public TokenBean getToken() {
        return token;
    }

    public void setToken(TokenBean token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class SetInfoResult extends BaseBean {

        private UserDataBean data;

        public UserDataBean getData() {
            return data;
        }

        public void setData(UserDataBean data) {
            this.data = data;
        }
    }
}
