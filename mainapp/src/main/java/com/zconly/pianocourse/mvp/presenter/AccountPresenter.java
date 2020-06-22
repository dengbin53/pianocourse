package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.bean.BalanceBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.BoughtRecordBean;
import com.zconly.pianocourse.bean.ChangeRecordBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.AccountView;

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
public class AccountPresenter extends BasePresenter<AccountView> {

    public AccountPresenter(AccountView mView) {
        super(mView);
    }

    public void balance() {
        Observable<BalanceBean.BalanceResult> o = RetrofitUtils.create(ApiService.class).balance();
        HttpRxObserver<BalanceBean.BalanceResult> hro = new HttpRxObserver<BalanceBean.BalanceResult>() {

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
            protected void onSuccess(BalanceBean.BalanceResult response) {
                if (mView != null) {
                    mView.dismissLoading();
                    mView.balanceSuccess(response);
                }
            }
        };

        if (mView instanceof BaseMvpFragment)
            HttpRxObservable.getObservableFragment(o, (BaseMvpFragment) mView).subscribe(hro);
        else if (mView instanceof BaseMvpActivity)
            HttpRxObservable.getObservable(o, (BaseMvpActivity) mView).subscribe(hro);
    }

    public void changeRecord(int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", page);
        params.put("pageSize", Constants.PAGE_COUNT);
        Observable<ChangeRecordBean.ChangeRecordListResult> o = RetrofitUtils.create(ApiService.class).changeRecord(params);
        HttpRxObserver<ChangeRecordBean.ChangeRecordListResult> hro = new HttpRxObserver<ChangeRecordBean.ChangeRecordListResult>() {

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
            protected void onSuccess(ChangeRecordBean.ChangeRecordListResult response) {
                if (mView != null) {
                    mView.dismissLoading();
                    mView.changeRecordListSuccess(response);
                }
            }
        };

        if (mView instanceof BaseMvpFragment)
            HttpRxObservable.getObservableFragment(o, (BaseMvpFragment) mView).subscribe(hro);
        else if (mView instanceof BaseMvpActivity)
            HttpRxObservable.getObservable(o, (BaseMvpActivity) mView).subscribe(hro);
    }

    public void boughtRecord(int page, int category) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", page);
        params.put("pageSize", Constants.PAGE_COUNT);
        Observable<BoughtRecordBean.BoughtRecordListResult> o = RetrofitUtils.create(ApiService.class).boughtRecord(params);
        HttpRxObserver<BoughtRecordBean.BoughtRecordListResult> hro = new HttpRxObserver<BoughtRecordBean.BoughtRecordListResult>() {

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
            protected void onSuccess(BoughtRecordBean.BoughtRecordListResult response) {
                if (mView != null) {
                    mView.dismissLoading();
                    mView.boughtRecordListSuccess(response);
                }
            }
        };

        if (mView instanceof BaseMvpFragment)
            HttpRxObservable.getObservableFragment(o, (BaseMvpFragment) mView).subscribe(hro);
        else if (mView instanceof BaseMvpActivity)
            HttpRxObservable.getObservable(o, (BaseMvpActivity) mView).subscribe(hro);
    }

    public void buy(long lessonId, long LvpId, String[] couponIds) {
        Map<String, Object> params = new HashMap<>();
        if (lessonId > 0)
            params.put("lesson_id", lessonId);
        if (LvpId > 0)
            params.put("lvp_id", LvpId);
        params.put("cash_coupon_ids", couponIds);
        Observable<BaseBean> o = RetrofitUtils.create(ApiService.class).buy(params);
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
                    mView.buySuccess(response);
                }
            }
        };

        if (mView instanceof BaseMvpFragment)
            HttpRxObservable.getObservableFragment(o, (BaseMvpFragment) mView).subscribe(hro);
        else if (mView instanceof BaseMvpActivity)
            HttpRxObservable.getObservable(o, (BaseMvpActivity) mView).subscribe(hro);
    }

}
