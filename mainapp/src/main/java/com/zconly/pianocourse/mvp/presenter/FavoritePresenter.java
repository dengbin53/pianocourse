package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.FavoriteBean;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.FavoriteView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/3 14:56
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 14:56
 * @UpdateRemark: 更新说明
 */
public class FavoritePresenter extends BasePresenter<FavoriteView> {

    public FavoritePresenter(FavoriteView mView) {
        super(mView);
    }

    public void getFavoriteList(int page, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", Constants.PAGE_COUNT + "");
        params.put("currentPage", page + "");
        Map<String, String> p2 = new HashMap<>();
        p2.put("type", type + "");
        params.put("t", p2);
        Observable<FavoriteBean.FavoriteListResult> o = RetrofitUtils.create(ApiService.class).getFavoriteList(params);
        HttpRxObservable.getObservableFragment(o, (LifecycleProvider<FragmentEvent>) mView)
                .subscribe(new HttpRxObserver<FavoriteBean.FavoriteListResult>() {

                    @Override
                    protected void onError(ApiException e) {
                        if (mView != null)
                            mView.onError(e);
                    }

                    @Override
                    protected void onSuccess(FavoriteBean.FavoriteListResult response) {
                        if (mView instanceof FavoriteView) {
                            mView.dismissLoading();
                            mView.getFavoriteListSuccess(response);
                        }
                    }
                });
    }

    public void favorite(long courseId, long videoId) {
        Map<String, Object> params = new HashMap<>();
        if (courseId > 0)
            params.put("lesson_id", courseId + "");
        if (videoId > 0)
            params.put("lv_id", videoId + "");
        Observable<BaseBean> o = RetrofitUtils.create(ApiService.class).favoriteVideo(params);
        HttpRxObservable.getObservable(o).subscribe(new HttpRxObserver<BaseBean>() {

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(BaseBean response) {
                if (mView instanceof FavoriteView) {
                    mView.dismissLoading();
                    mView.favoriteSuccess(response);
                }
            }
        });
    }

    public void like(long courseId, long videoId) {
        Map<String, Object> params = new HashMap<>();
        if (courseId > 0)
            params.put("lesson_id", courseId + "");
        if (videoId > 0)
            params.put("lv_id", videoId + "");
        Observable<BaseBean> o = RetrofitUtils.create(ApiService.class).like(params);
        HttpRxObservable.getObservable(o).subscribe(new HttpRxObserver<BaseBean>() {

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(BaseBean response) {
                if (mView instanceof FavoriteView) {
                    mView.dismissLoading();
                    mView.likeSuccess(response);
                }
            }
        });
    }

}
