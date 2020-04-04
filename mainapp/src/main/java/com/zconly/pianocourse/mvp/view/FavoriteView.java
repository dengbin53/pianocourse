package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.FavoriteBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/3 14:56
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 14:56
 * @UpdateRemark: 更新说明
 */
public interface FavoriteView extends MvpView {

    void getFavoriteListSuccess(FavoriteBean.FavoriteListResult response);

    void favoriteSuccess(BaseBean response);

    void likeSuccess(BaseBean response);
}
