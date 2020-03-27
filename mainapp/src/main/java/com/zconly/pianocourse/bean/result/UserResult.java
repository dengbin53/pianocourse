package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.TokenBean;
import com.zconly.pianocourse.bean.UserBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/25 11:04
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/25 11:04
 * @UpdateRemark: 更新说明
 */
public class UserResult extends BaseBean {

    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public static class UserData extends BaseBean {
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
    }
}
