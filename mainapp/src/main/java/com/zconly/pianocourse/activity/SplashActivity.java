package com.zconly.pianocourse.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.View;
import android.widget.VideoView;

import com.mvp.base.MvpPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseMvpActivity {

    @BindView(R.id.splash_video_view)
    VideoView videoView;

    private void gotoMain() {
        MainActivity.start(mContext);
        finishDown();
    }

    @OnClick({R.id.splash_tv})
    public void onClick(View view) {
        gotoMain();
    }

    @SuppressLint("CheckResult")
    @Override
    protected boolean initView() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
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
//        final String uri = "android.resource://" + getPackageName() + "/" + R.raw.guide00;
//        videoView.setVideoURI(Uri.parse(uri));
//        videoView.start();
//        videoView.setOnPreparedListener(mp -> {
//            mp.start();
//            mp.setLooping(false);
//        });
//
//        videoView.setOnCompletionListener(mp -> gotoMain());

        gotoMain();
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

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}
