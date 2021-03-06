package com.zconly.pianocourse.base;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.mvp.base.MvpFragment;
import com.mvp.base.MvpPresenter;
import com.mvp.exception.ApiException;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.util.UmengUtil;
import com.zconly.pianocourse.widget.dialog.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Fragment的统一父类
 * @Author: dengbin
 * @CreateDate: 2020-01-06 15:17
 * @UpdateUser: dengbin
 * @UpdateDate: 2020-01-06 15:17
 * @UpdateRemark: 更新说明
 */
public abstract class BaseMvpFragment<P extends MvpPresenter> extends MvpFragment<P> {

    @Override
    public void onResume() {
        super.onResume();
        UmengUtil.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        UmengUtil.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.with(this).pauseRequests();
    }

    @Override
    public void onError(ApiException ae) {
        super.onError(ae);
        dismissLoading();
        if (ae != null)
            ToastUtil.toast(ae.getMsg());
    }

    @Override
    public void loading(String msg) {
        super.loading(msg);
        LoadingDialog.loading((FragmentActivity) mContext, msg);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        LoadingDialog.dismissLoadingDialog();
    }

    public void insertUmeng(String eventId, String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        UmengUtil.onEvent(mContext, eventId, map);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.activity_in_slide_left, R.anim.activity_out_slide_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        ((Activity) mContext).overridePendingTransition(R.anim.activity_in_slide_left, R.anim.activity_out_slide_left);
    }

    public void startActivityUp(Intent intent) {
        startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.activity_in_slide_up, R.anim.activity_default);
    }
}
