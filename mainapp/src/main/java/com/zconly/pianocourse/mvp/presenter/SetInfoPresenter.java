package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.SetInfoActivity;
import com.zconly.pianocourse.bean.result.UserResult;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.SetInfoView;

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
public class SetInfoPresenter extends BasePresenter<SetInfoView> {

    public SetInfoPresenter(SetInfoView mView) {
        super(mView);
    }

    public void completion(Map<String, String> params) {
        if (!isNetConnect()) return;
        Observable<UserResult> observer = RetrofitUtils.create(ApiService.class).completion(params);
        HttpRxObservable.getObservable(observer, (SetInfoActivity) mView).subscribe(new HttpRxObserver<UserResult>() {
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
                    mView.completionSuccess(response);
            }
        });
    }

}
