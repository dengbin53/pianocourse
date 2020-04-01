package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.result.AppUpdateResult;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 13:10
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 13:10
 * @UpdateRemark: 更新说明
 */
interface UpdateView extends MvpView {
    void getAppUpdateSuccess(AppUpdateResult result);
}
