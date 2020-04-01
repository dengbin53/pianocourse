package com.zconly.pianocourse.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mvp.base.MvpDialog;
import com.zconly.pianocourse.R;

import butterknife.BindView;

/**
 * 聊天确认框
 *
 * @author DengBin
 */
public class DialogConfirm extends MvpDialog {

    @BindView(R.id.dialog_confirm_btnleft)
    TextView btn1;
    @BindView(R.id.dialog_confirm_btnright)
    TextView btn2;
    @BindView(R.id.dialog_confirm_title)
    TextView titleView;
    @BindView(R.id.dialog_confirm_content)
    TextView contentTv;

    private String content;
    private OnClickListener mOnClick;
    private String title;
    private String btn1Str;
    private String btn2Str;

    public DialogConfirm(Context context, OnClickListener onClick, String title, String content, String btn1,
                         String btn2, boolean cancelabl) {
        this.mOnClick = onClick;
        this.title = title;
        this.content = content;
        this.btn1Str = btn1;
        this.btn2Str = btn2;
        setCancelable(cancelabl);
        setGravity(Gravity.CENTER);
        setmContext(context);
    }

    public DialogConfirm(Context context, OnClickListener onClick, String content) {
        this(context, onClick, null, content, null, null, true);
    }

    private OnClickListener tmpOnclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            mOnClick.onClick(v);
        }
    };

    @Override
    protected int getContentViewID() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected int getStyle() {
        return R.style.dialog_sacle;
    }

    @Override
    protected void initView(Bundle bundle) {
        btn1.setOnClickListener(tmpOnclick);
        btn2.setOnClickListener(tmpOnclick);
        if (!TextUtils.isEmpty(this.title))
            titleView.setText(this.title);
        if (!TextUtils.isEmpty(btn1Str))
            btn1.setText(this.btn1Str);
        if (!TextUtils.isEmpty(btn2Str)) {
            btn2.setText(btn2Str);
        }
        if (!TextUtils.isEmpty(content)) {
            contentTv.setText(content);
        }
    }

}
