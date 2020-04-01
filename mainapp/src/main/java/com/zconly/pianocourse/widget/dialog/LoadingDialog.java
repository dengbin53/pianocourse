package com.zconly.pianocourse.widget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.mvp.base.MvpDialog;
import com.zconly.pianocourse.R;

public class LoadingDialog extends MvpDialog {

    @SuppressLint("StaticFieldLeak")
    private static LoadingDialog mLoadingDialog;
    private TextView tv;
    private String msg = "";

    public LoadingDialog() {

    }

    @SuppressLint("ValidFragment")
    public LoadingDialog(Context context) {
        setmContext(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 屏蔽返回按钮
        Window win = getDialog().getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
        setCancelable(false);
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismissLoadingDialog();
            }
            return false;
        });
    }

    @Override
    protected int getContentViewID() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initView(Bundle bundle) {
        tv = getView().findViewById(R.id.tv);
        tv.setText(msg);
        tv.setVisibility(TextUtils.isEmpty(msg) ? View.GONE : View.VISIBLE);
    }

    @Override
    protected int getStyle() {
        return R.style.updateDialogStyle;
    }

    public void setShowMsg(String msg) {
        this.msg = msg;
        if (tv != null)
            tv.setText(msg);
    }

    public static void loading(FragmentActivity context, String msg) {
        if (context == null)
            return;
        if (mLoadingDialog != null && mLoadingDialog.isAdded()
                && mLoadingDialog.getContext() != null && mLoadingDialog.getContext().equals(context)
                && mLoadingDialog.getDialog() != null && mLoadingDialog.getDialog().isShowing()) {
            mLoadingDialog.setShowMsg(msg);
            return;
        }
        dismissLoadingDialog();
        mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.setShowMsg(msg);
        mLoadingDialog.setGravity(Gravity.CENTER);
        mLoadingDialog.show(context.getSupportFragmentManager(), "");
    }

    public static void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

}
