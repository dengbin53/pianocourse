package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.FindPassActivity;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.result.UserResult;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.SignUpView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 21:56
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 21:56
 * @UpdateRemark: 更新说明
 */
public class SignUpPresenter extends BasePresenter<SignUpView> {

    public SignUpPresenter(SignUpView mView) {
        super(mView);
    }

    public void retrieve(String phone) {
        if (!isNetConnect()) return;
        Observable<BaseBean> observer = RetrofitUtils.create(ApiService.class).retrieve(phone);
        HttpRxObservable.getObservable(observer, (BaseMvpActivity) mView).subscribe(new HttpRxObserver<BaseBean>() {
            @Override
            protected void onStart(Disposable d) {
                if (mView != null)
                    mView.loading("");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(BaseBean response) {
                if (mView != null)
                    mView.retrieveSuccess(response);
            }
        });
    }

    public void verify(String phone, String code) {
        if (!isNetConnect()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("code", code);
        Observable<BaseBean> observer = RetrofitUtils.create(ApiService.class).verify(params);
        HttpRxObservable.getObservable(observer, (BaseMvpActivity) mView).subscribe(new HttpRxObserver<BaseBean>() {
            @Override
            protected void onStart(Disposable d) {
                if (mView != null)
                    mView.loading("");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(BaseBean response) {
                if (mView != null)
                    mView.verifySuccess(response);
            }
        });
    }

    public void resetPassword(Map<String, String> params) {
        if (!isNetConnect()) return;
        Observable<UserResult> observer = RetrofitUtils.create(ApiService.class).resetPassword(params);
        HttpRxObservable.getObservable(observer, (FindPassActivity) mView).subscribe(new HttpRxObserver<UserResult>() {
            @Override
            protected void onStart(Disposable d) {
                if (mView != null)
                    mView.loading("");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(UserResult response) {
                if (mView != null)
                    mView.resetSuccess(response);
            }
        });
    }
}
