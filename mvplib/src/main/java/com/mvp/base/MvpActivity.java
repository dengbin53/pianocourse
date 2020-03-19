package com.mvp.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.mvp.exception.ApiException;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: 支持MVP的activity基类
 * 注：在显示titleView的情况下不能很好的支持使用<merge>标签
 * @Author: dengbin
 * @CreateDate: 2019-12-17 11:31
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-17 11:31
 * @UpdateRemark:
 */
public abstract class MvpActivity<P extends MvpPresenter, T extends View> extends RxAppCompatActivity
        implements MvpView {

    protected P mPresenter;
    protected Unbinder unbinder;
    protected FragmentActivity mContext;
    protected Handler mHandler;
    protected T mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        doSetContentView(getContentView());

        mPresenter = getPresenter();
        unbinder = ButterKnife.bind(this);

        if (isBindEventBus())
            EventBus.getDefault().register(this);

        initView();
        initData();
    }

    protected void doSetContentView(int id) {

        ViewGroup mRootView = getWindow().getDecorView().findViewById(android.R.id.content);

        if (id > 0)
            getLayoutInflater().inflate(id, mRootView, true);

        mTitleView = getTitleView();

        if (mTitleView == null)
            return;

        mRootView.addView(mTitleView, 0);

        int count = mRootView.getChildCount();
        if (count <= 1)
            return;

        for (int i = 1; i < count; i++) {
            final View view = mRootView.getChildAt(i);
            view.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    lp.topMargin = lp.topMargin + mTitleView.getMeasuredHeight();
                    view.setLayoutParams(lp);
                }
            });
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getContentView();

    protected abstract T getTitleView();

    protected abstract P getPresenter();

    protected abstract boolean isBindEventBus();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (unbinder != null)
            unbinder.unbind();
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void loading(String msg) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onError(ApiException errorBean) {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res != null) {
            Configuration config = res.getConfiguration();
            if (config != null && config.fontScale != 1.0f) {
                config.fontScale = 1.0f;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
        }
        return res;
    }
}
