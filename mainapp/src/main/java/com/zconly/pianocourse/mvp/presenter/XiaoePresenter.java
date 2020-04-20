package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.XiaoeActivity;
import com.zconly.pianocourse.bean.XiaoeTokenBean;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.XiaoeView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/19 01:04
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/19 01:04
 * @UpdateRemark: 更新说明
 */
public class XiaoePresenter extends BasePresenter<XiaoeView> {

    public XiaoePresenter(XiaoeView mView) {
        super(mView);
    }

    public void getXiaoeToken() {
        Observable<XiaoeTokenBean.XiaoeTokenResult> ob = RetrofitUtils.create(ApiService.class).xiaoeLogin();
        HttpRxObservable.getObservable(ob, (XiaoeActivity) mView).subscribe(
                new HttpRxObserver<XiaoeTokenBean.XiaoeTokenResult>() {

                    @Override
                    protected void onStart(Disposable d) {
                        mView.loading("");
                    }

                    @Override
                    protected void onError(ApiException e) {
                        mView.onError(e);
                    }

                    @Override
                    protected void onSuccess(XiaoeTokenBean.XiaoeTokenResult response) {
                        mView.dismissLoading();
                        mView.getXiaoeTokenSuccess(response);
                    }
                });
    }
}
