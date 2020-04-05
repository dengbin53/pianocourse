package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.FindPassActivity;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.UserBean;
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

    // 找回密码-验证码
    public void retrieve(String mobile) {
        if (!isNetConnect()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        Observable<BaseBean> observer = RetrofitUtils.create(ApiService.class).retrieve(params);
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
                if (mView != null) {
                    mView.dismissLoading();
                    mView.sendCodeSuccess(response);
                }
            }
        });
    }

    public void signUpCode(String mobile) {
        if (!isNetConnect()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        Observable<BaseBean> observer = RetrofitUtils.create(ApiService.class).signUpCode(params);
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
                if (mView != null) {
                    mView.dismissLoading();
                    mView.sendCodeSuccess(response);
                }
            }
        });
    }

    // behavior，0-注册，1-登录，2-修改米啊吗
    public void verify(String phone, String code, String behavior) {
        if (!isNetConnect()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("code", code);
        params.put("behavior", behavior);
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
                if (mView != null) {
                    mView.dismissLoading();
                    mView.verifySuccess(response);
                }
            }
        });
    }

    public void resetPassword(Map<String, String> params) {
        if (!isNetConnect()) return;
        Observable<UserBean.UserResult> observer = RetrofitUtils.create(ApiService.class).resetPassword(params);
        HttpRxObservable.getObservable(observer, (FindPassActivity) mView).subscribe(
                new HttpRxObserver<UserBean.UserResult>() {
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
                    protected void onSuccess(UserBean.UserResult response) {
                        if (mView != null) {
                            mView.dismissLoading();
                            mView.resetSuccess(response);
                        }
                    }
                });
    }
}
