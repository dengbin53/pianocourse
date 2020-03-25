package com.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mvp.exception.ApiException;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2019-12-24 17:40
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-24 17:40
 * @UpdateRemark: 更新说明
 */
public abstract class MvpFragment<P extends MvpPresenter> extends RxFragment implements MvpView {

    protected P mPresenter;
    protected boolean isLoadDataCompleted;
    protected boolean isViewCreated;
    protected View fragmentView;
    private Unbinder unBinder;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (fragmentView == null)
            fragmentView = inflater.inflate(getContentView(), null);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unBinder = ButterKnife.bind(this, view);
        if (isBindEventBus())
            EventBus.getDefault().register(this);

        mPresenter = getPresenter();

        initView(view);
        isViewCreated = true;
        if (getUserVisibleHint() && !isLoadDataCompleted) {
            initData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (fragmentView != null && isViewCreated && isVisibleToUser && !isLoadDataCompleted) {
            initData();
        }
    }

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract boolean isBindEventBus();

    protected abstract int getContentView();

    protected abstract P getPresenter();

    @Override
    public void loading(String msg) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onError(ApiException ae) {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewCreated = false;
        isLoadDataCompleted = false;
        if (unBinder != null)
            unBinder.unbind();
        EventBus.getDefault().unregister(this);
    }

}
