package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.result.UserResult;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/25 12:14
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/25 12:14
 * @UpdateRemark: 更新说明
 */
public interface UserView extends MvpView {
    void updateUserSuccess(UserResult response);
}
