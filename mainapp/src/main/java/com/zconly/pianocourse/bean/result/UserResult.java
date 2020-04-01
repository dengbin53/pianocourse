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

    private UserBean data;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }
}
