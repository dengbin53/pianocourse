package com.zconly.pianocourse.base;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.mvp.base.MvpActivity;
import com.mvp.base.MvpPresenter;
import com.mvp.exception.ApiException;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.StatUtil;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.widget.dialog.LoadingDialog;
import com.zconly.pianocourse.widget.TitleView;

import java.util.HashMap;

/**
 * @Description: 基于 MvpActivity 的Activity基类
 * @Author: dengbin
 * @CreateDate: 2019-12-25 18:55
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-25 18:55
 * @UpdateRemark: 更新说明
 */
public abstract class BaseMvpActivity<P extends MvpPresenter> extends MvpActivity<P, TitleView> {

    @Override
    public void onError(ApiException errorBean) {
        super.onError(errorBean);

        dismissLoading();

        ToastUtil.toast(errorBean.getCode() + " " + errorBean.getMsg());

        // TODO: 2020/3/27
        if (errorBean.getCode() == -100) {
            new AlertDialog.Builder(mContext)
                    .setMessage(errorBean.getMsg())
                    .setNegativeButton("确定", (dialog, which) -> SysConfigTool.logout())
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    protected void onPause() {
        StatUtil.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatUtil.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }

    @Override
    public void loading(String msg) {
        super.loading(msg);
        LoadingDialog.loading(mContext, msg);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        LoadingDialog.dismissLoadingDialog();
    }

    @Override
    protected TitleView getTitleView() {
        if (!hasTitleView())
            return mTitleView = null;
        return mTitleView = mTitleView == null ? new TitleView(mContext) : mTitleView;
    }

    // 默认启动动画
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_in_slide_left, R.anim.activity_out_slide_left);
    }

    // 默认退出动画
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in_slide_right, R.anim.activity_out_slide_right);
    }

    // 无动画效果启动Activity
    public void startActivityNoAnim(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_default, R.anim.activity_default);
    }

    // 向上推进
    public void startActivityUp(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_in_slide_up, R.anim.activity_default);
    }

    // 无动画效果 finish Activity
    public void finishNoAnim() {
        super.finish();
        overridePendingTransition(R.anim.activity_default, R.anim.activity_default);
    }

    // 淡出
    public void finishFade() {
        super.finish();
        overridePendingTransition(R.anim.activity_in_fade, R.anim.activity_out_fade);
    }

    // 下滑退出
    public void finishDown() {
        super.finish();
        overridePendingTransition(R.anim.activity_default, R.anim.activity_out_slide_down);
    }

    protected abstract boolean hasTitleView();

    public void insertUmeng(Context context, String eventId, String key, String value) {
        HashMap<String, String> map = new HashMap<>();
        map.put(key, value);
        StatUtil.onEvent(context, eventId, map);
    }

    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);
        }
    }

}
