package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.mine.FeedbackActivity;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.FeedbackView;
import com.zconly.pianocourse.mvp.view.UploadView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 19:36
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 19:36
 * @UpdateRemark: 更新说明
 */
public class FeedbackPresenter extends BasePresenter<FeedbackView> {

    public FeedbackPresenter(FeedbackView mView) {
        super(mView);
    }

    public void feedback(String content) {
        if (!isNetConnect()) return;
        Map<String, String> params = new HashMap<>();
        params.put("feedback", content);
        Observable<BaseBean> ob = RetrofitUtils.create(ApiService.class).feedback(params);
        Observer<BaseBean> observer = new HttpRxObserver<BaseBean>() {
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
                if (mView instanceof UploadView) {
                    mView.dismissLoading();
                    mView.feedbackSuccess(response);
                }
            }
        };
        HttpRxObservable.getObservable(ob, (FeedbackActivity) mView).subscribe(observer);
    }
}
