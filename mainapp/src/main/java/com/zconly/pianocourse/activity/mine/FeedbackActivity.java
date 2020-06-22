package com.zconly.pianocourse.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.mvp.presenter.FeedbackPresenter;
import com.zconly.pianocourse.mvp.view.FeedbackView;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseMvpActivity<FeedbackPresenter> implements FeedbackView {

    @BindView(R.id.act_feedback_et)
    EditText contentEt;

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, FeedbackActivity.class);
        mContext.startActivity(intent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_feedback_btn:
                String content = contentEt.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.toast("请输入内容");
                    break;
                }
                content = content.trim();
                mPresenter.feedback(content);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean initView() {
        mTitleView.setTitle(getString(R.string.key_feedback));
        mTitleView.postDelayed(() -> ViewUtil.edtFocusable(contentEt), 128);
        return true;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.acttivity_feedback;
    }

    @Override
    protected FeedbackPresenter getPresenter() {
        return new FeedbackPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    public void feedbackSuccess(BaseBean response) {
        ToastUtil.toast("提交成功");
        finish();
    }
}
