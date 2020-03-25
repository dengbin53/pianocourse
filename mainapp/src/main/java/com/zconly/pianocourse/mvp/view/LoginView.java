package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.result.TokenResult;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 21:58
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 21:58
 * @UpdateRemark: 更新说明
 */
public interface LoginView extends MvpView {

    void loginSuccess(TokenResult response);

}
