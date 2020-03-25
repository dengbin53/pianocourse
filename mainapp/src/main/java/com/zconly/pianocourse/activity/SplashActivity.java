package com.zconly.pianocourse.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.mvp.base.MvpPresenter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;

import butterknife.BindView;

public class SplashActivity extends BaseMvpActivity {

    @BindView(R.id.splash_iv)
    ImageView iv;
    @BindView(R.id.splash_tv)
    TextView tv;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(mContext);
                finish();
            }
        }, 1000);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected MvpPresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }
}
