package com.zconly.pianocourse.bean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/6 00:05
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/6 00:05
 * @UpdateRemark: 更新说明
 */
public class AppUpdateBean extends BaseBean {

    public static class AppUpdateResult extends BaseBean {

        private AppUpdateBean data;

        public AppUpdateBean getData() {
            return data;
        }

        public void setData(AppUpdateBean data) {
            this.data = data;
        }

    }

}
