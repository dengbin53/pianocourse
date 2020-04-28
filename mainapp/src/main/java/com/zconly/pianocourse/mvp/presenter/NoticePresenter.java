package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.NoticeActivity;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.bean.NoticeBean;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.NoticeView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/29 23:08
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/29 23:08
 * @UpdateRemark: 更新说明
 */
public class NoticePresenter extends BasePresenter<NoticeView> {
    public NoticePresenter(NoticeView mView) {
        super(mView);
    }

    public void getMsg(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("currentPage", page + "");
        params.put("pageSize", Constants.PAGE_COUNT + "");
        Observable<NoticeBean.NoticeResult> ob = RetrofitUtils.create(ApiService.class).getNoticeList(params);
        HttpRxObservable.getObservable(ob, (NoticeActivity) mView).subscribe(
                new HttpRxObserver<NoticeBean.NoticeResult>() {

                    @Override
                    protected void onError(ApiException e) {
                        if (mView != null)
                            mView.onError(e);
                    }

                    @Override
                    protected void onSuccess(NoticeBean.NoticeResult response) {
                        if (mView != null) {
                            mView.dismissLoading();
                            mView.getNoticeSuccess(response);
                        }
                    }
                });

    }
}
