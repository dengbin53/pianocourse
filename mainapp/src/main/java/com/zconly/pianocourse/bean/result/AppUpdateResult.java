package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 13:11
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 13:11
 * @UpdateRemark: 更新说明
 */
public class AppUpdateResult extends BaseBean {
    private AppUpdateBean data;

    public AppUpdateBean getData() {
        return data;
    }

    public void setData(AppUpdateBean data) {
        this.data = data;
    }

    public static class AppUpdateBean extends BaseBean {

    }
}
