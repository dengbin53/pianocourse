package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.mvp.exception.ApiException;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.FavoriteBean;
import com.zconly.pianocourse.mvp.view.FavoriteView;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/3 17:32
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 17:32
 * @UpdateRemark: 更新说明
 */
public abstract class AbstractFavoriteView implements FavoriteView {
    @Override
    public void loading(String msg) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onError(ApiException ae) {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void getFavoriteListSuccess(FavoriteBean.FavoriteListResult response) {

    }

    @Override
    public void favoriteSuccess(BaseBean response) {

    }

    @Override
    public void likeSuccess(BaseBean response) {

    }
}
