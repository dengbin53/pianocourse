package com.zconly.pianocourse.mvp.presenter;

import android.text.TextUtils;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.zconly.pianocourse.activity.CourseDetailActivity;
import com.zconly.pianocourse.activity.VideoDetailActivity;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.LiveBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.VideoListResult;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.CourseView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 20:26
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 20:26
 * @UpdateRemark: 更新说明
 */
public class CoursePresenter extends BasePresenter<CourseView> {

    public CoursePresenter(CourseView mView) {
        super(mView);
    }

    public void getCourseList(int page, String id, String category) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", page + "");
        params.put("pageSize", Constants.PAGE_COUNT + "");
        Map<String, String> t = null;
        if (!TextUtils.isEmpty(id)) {
            t = new HashMap<>();
            t.put("id", id);
        }
        if (!TextUtils.isEmpty(category)) {
            if (t == null)
                t = new HashMap<>();
            t.put("category", category);
        }
        if (t != null)
            params.put("t", t);
        Observable<CourseListResult> o = RetrofitUtils.create(ApiService.class).getCourseList(params);
        HttpRxObservable.getObservableFragment(o, (LifecycleProvider<FragmentEvent>) mView)
                .subscribe(new HttpRxObserver<CourseListResult>() {
                    @Override
                    protected void onError(ApiException e) {
                        if (mView != null)
                            mView.onError(e);
                    }

                    @Override
                    protected void onSuccess(CourseListResult response) {
                        if (mView instanceof CourseView) {
                            mView.dismissLoading();
                            mView.getCourseListSuccess(response);
                        }
                    }
                });
    }

    public void getVideoList(long id) {
        Map<String, String> params = new HashMap<>();
        params.put("lesson_id", id + "");
        Observable<VideoListResult> o = RetrofitUtils.create(ApiService.class).getVideoList(params);
        HttpRxObservable.getObservable(o, (CourseDetailActivity) mView).subscribe(new HttpRxObserver<VideoListResult>() {
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
            protected void onSuccess(VideoListResult response) {
                if (mView instanceof CourseView) {
                    mView.dismissLoading();
                    mView.getVideoListSuccess(response);
                }
            }
        });
    }

    public void getBanner() {
        Observable<BannerBean.BannerListResult> o = RetrofitUtils.create(ApiService.class).getBanner();
        HttpRxObservable.getObservableFragment(o, (LifecycleProvider<FragmentEvent>) mView)
                .subscribe(new HttpRxObserver<BannerBean.BannerListResult>() {
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
                    protected void onSuccess(BannerBean.BannerListResult response) {
                        if (mView != null) {
                            mView.dismissLoading();
                            mView.getBannerListSuccess(response);
                        }
                    }
                });
    }

    public void getLiveData() {
        Observable<LiveBean> o = RetrofitUtils.createH5(H5Service.class).getLiveData();
        HttpRxObservable.getObservableFragment(o, (LifecycleProvider<FragmentEvent>) mView)
                .subscribe(new HttpRxObserver<LiveBean>() {
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
                    protected void onSuccess(LiveBean response) {
                        if (mView != null) {
                            mView.dismissLoading();
                            mView.getLiveDataSuccess(response);
                        }
                    }
                });
    }

    public void getEvaluateList(long id, int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", page + "");
        params.put("pageSize", Constants.PAGE_COUNT + "");
        if (id > 0) {
            Map<String, String> t = new HashMap<>();
            t.put("exercise_id", id + "");
            params.put("t", t);
        }
        Observable<EvaluateBean.EvaluateListResult> o = RetrofitUtils.create(ApiService.class).getEvaluate(params);
        HttpRxObservable.getObservable(o, (VideoDetailActivity) mView).subscribe(
                new HttpRxObserver<EvaluateBean.EvaluateListResult>() {
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
                    protected void onSuccess(EvaluateBean.EvaluateListResult response) {
                        if (mView != null) {
                            mView.dismissLoading();
                            mView.getEvaluateSuccess(response);
                        }
                    }
                });
    }
}
