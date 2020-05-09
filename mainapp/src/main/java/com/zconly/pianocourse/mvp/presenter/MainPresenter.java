package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.MainActivity;
import com.zconly.pianocourse.bean.UpdateBean;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.MainView;

import io.reactivex.Observable;

/**
 * @Description: 主页
 * @Author: dengbin
 * @CreateDate: 2020/5/9 23:53
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/9 23:53
 * @UpdateRemark: 更新说明
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView mView) {
        super(mView);
    }

    public void getUpdateResult() {
        Observable<UpdateBean.UpdateResult> ob = RetrofitUtils.createH5(H5Service.class).getUpdateJson();
        HttpRxObservable.getObservable(ob, (MainActivity) mView).subscribe(new HttpRxObserver<UpdateBean.UpdateResult>() {

            @Override
            protected void onError(ApiException e) {
                // mView.onError(e);
            }

            @Override
            protected void onSuccess(UpdateBean.UpdateResult response) {
                mView.getUpdateSuccess(response);
            }
        });
    }

}
