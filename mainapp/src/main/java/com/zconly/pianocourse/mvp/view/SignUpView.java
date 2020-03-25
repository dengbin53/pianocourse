package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.result.UserResult;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 21:58
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 21:58
 * @UpdateRemark: 更新说明
 */
public interface SignUpView extends MvpView {

    void retrieveSuccess(BaseBean response);

    void verifySuccess(BaseBean response);

    void resetSuccess(UserResult response);
}
