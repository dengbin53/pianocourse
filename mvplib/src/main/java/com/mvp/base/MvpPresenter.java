package com.mvp.base;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2019-12-24 17:05
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-24 17:05
 * @UpdateRemark: 更新说明
 */
public abstract class MvpPresenter<V extends MvpView> {

    protected V mView;

    public MvpPresenter(V mView) {
        this.mView = mView;
    }

}
