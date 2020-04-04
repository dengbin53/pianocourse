package com.zconly.pianocourse.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvp.base.MvpPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;

import butterknife.BindView;

public class SplashActivity extends BaseMvpActivity {

    @BindView(R.id.splash_iv)
    ImageView iv;
    @BindView(R.id.splash_tv)
    TextView tv;

    @SuppressLint("CheckResult")
    @Override
    protected boolean initView() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {

                    } else {

                    }
                });
        return true;
    }

    @Override
    protected void initData() {
        iv.postDelayed(() -> {
            MainActivity.start(mContext);
            finishDown();
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
