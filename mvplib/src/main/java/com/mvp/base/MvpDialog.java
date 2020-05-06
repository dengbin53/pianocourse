package com.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mvp.R;
import com.mvp.exception.ApiException;
import com.mvp.utils.Util;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

import butterknife.ButterKnife;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2019-12-24 17:57
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-24 17:57
 * @UpdateRemark: 更新说明
 */
public abstract class MvpDialog extends RxDialogFragment implements MvpView {

    private static final float DEFAULT_ALPHA = 0.48f;
    private int gravity = 0;
    private int alpha;
    private int y;
    private int padding = 64;

    // 必须传递字段
    public Context mContext;
    private boolean cancelable;
    private boolean canceledOnTouchOutside;

    public MvpDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public MvpDialog setAlpha(int alpha) {
        this.alpha = alpha;
        return this;
    }

    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        this.cancelable = cancelable;
    }

    public void setCanceledOnTouchOutside(boolean cancelable) {
        if (getDialog() != null)
            getDialog().setCanceledOnTouchOutside(cancelable);
        this.canceledOnTouchOutside = cancelable;
    }

    public MvpDialog setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * 设置距离左右的padding
     */
    public MvpDialog setPadding(int x) {
        this.padding = x;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        int style = getStyle();
        setStyle(DialogFragment.STYLE_NO_TITLE, style > 0 ? style : R.style.MvpBaseDialogTheme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        view.setClickable(true);
        if (gravity == Gravity.BOTTOM) {
            // AnimationUtil.slideToUp(view);
        } else if (gravity == Gravity.CENTER) {
            // AnimationUtil.fadeCenter(view);
        }
        setCancelable(cancelable);
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        initView(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        //设置宽度铺满全屏
        Window win = getDialog().getWindow();
        if (win == null)
            return;
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.dimAmount = alpha == 0 ? DEFAULT_ALPHA : alpha;
        params.gravity = gravity == 0 ? Gravity.BOTTOM : gravity;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (gravity == Gravity.TOP) {
            params.y = Util.dp2px(mContext, y);
            params.width = Util.widthDisplay(mContext) - Util.dp2px(mContext, padding);
        } else if (gravity == Gravity.CENTER) {
            params.width = Util.widthDisplay(mContext) - Util.dp2px(mContext, padding);
        }
        win.setAttributes(params);
    }

    /**
     * set layout xml
     */
    protected abstract int getContentViewID();

    /**
     * init object
     */
    protected abstract void initView(Bundle bundle);

    protected abstract int getStyle();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // ToggleDialog.toggleDismissDialogLoading();
    }

    @Override
    public void onError(ApiException errorBean) {
        int code = errorBean.getCode();
        switch (code) {
            // token失效
            case 101:
            case 100:
//                SharePreferencesUtil.saveStringData("token", "");
//                SharePreferencesUtil.saveObjectBean(null, "userBean");
//                SharePreferencesUtil.saveIntData("userId", 0);
//                startIntent(MainActivity.class);
//                startIntent(LoginActivity.class);
                break;
        }
    }

    public void startIntent(Class cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        try {
            // 在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commit();
        } catch (Exception e) {

        }

        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void dismiss() {
        dismissAllowingStateLoss();
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void loading(String msg) {

    }
}

