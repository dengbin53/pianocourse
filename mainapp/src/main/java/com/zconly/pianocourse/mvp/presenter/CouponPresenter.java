package com.zconly.pianocourse.mvp.presenter;

import com.mvp.base.MvpDialog;
import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CouponBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.CouponView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: 作用描述
 * @Author: dengbin
 * @CreateDate: 2020/6/21 21:14
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/6/21 21:14
 * @UpdateRemark: 更新说明
 */
public class CouponPresenter extends BasePresenter<CouponView> {

    public CouponPresenter(CouponView mView) {
        super(mView);
    }

    public void getMyCoupon(int page, long id, long lessonId, long lvpId, int status) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> t = new HashMap<>();
        t.put("id", id);
        t.put("lesson_id", lessonId);
        t.put("lvp_id", lvpId);
        t.put("status", status);
        params.put("currentPage", page);
        params.put("pageSize", Constants.PAGE_COUNT);
        params.put("t", t);
        Observable<CouponBean.CouponListResult> o = RetrofitUtils.create(ApiService.class).getMyCoupon(params);
        HttpRxObserver<CouponBean.CouponListResult> hro = new HttpRxObserver<CouponBean.CouponListResult>() {

            @Override
            protected void onStart(Disposable d) {
                super.onStart(d);
                if (mView != null)
                    mView.loading("");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(CouponBean.CouponListResult response) {
                if (mView != null) {
                    mView.dismissLoading();
                    mView.getCouponListSuccess(response);
                }
            }
        };

        if (mView instanceof BaseMvpFragment)
            HttpRxObservable.getObservableFragment(o, (BaseMvpFragment) mView).subscribe(hro);
        else if (mView instanceof BaseMvpActivity)
            HttpRxObservable.getObservable(o, (BaseMvpActivity) mView).subscribe(hro);
    }

    public void obtainCoupon(String identifier) {
        Observable<BaseBean> o = RetrofitUtils.create(ApiService.class).obtainCoupon(identifier);
        HttpRxObserver<BaseBean> hro = new HttpRxObserver<BaseBean>() {

            @Override
            protected void onStart(Disposable d) {
                super.onStart(d);
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
                    mView.obtainCouponSuccess(response);
                }
            }
        };

        if (mView instanceof MvpDialog)
            HttpRxObservable.getObservableFragment(o, (MvpDialog) mView).subscribe(hro);
    }

}
