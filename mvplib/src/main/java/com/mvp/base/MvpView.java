package com.mvp.base;

import com.mvp.exception.ApiException;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2019-12-24 16:47
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-24 16:47
 * @UpdateRemark: 更新说明
 */
public interface MvpView {

    void loading(String msg);

    void dismissLoading();

    void onError(ApiException ae);

    void onFailure(String msg);
}
