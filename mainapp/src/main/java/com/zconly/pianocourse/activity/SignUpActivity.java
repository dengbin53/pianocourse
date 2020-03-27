package com.zconly.pianocourse.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.result.UserResult;
import com.zconly.pianocourse.event.SignInEvent;
import com.zconly.pianocourse.mvp.presenter.SignUpPresenter;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.SignUpView;
import com.zconly.pianocourse.util.ActionUtil;
import com.zconly.pianocourse.util.CountDownTimerTool;
import com.zconly.pianocourse.util.StringTool;
import com.zconly.pianocourse.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 * Created by dengbin
 */
public class SignUpActivity extends BaseMvpActivity<SignUpPresenter> implements SignUpView {

    private static final int GET_CODE_SUCCESS = 0;

    @BindView(R.id.email)
    EditText mobileEt;
    @BindView(R.id.pass)
    EditText passEt;
    @BindView(R.id.verification_code)
    EditText codeEt;
    @BindView(R.id.terms_of_service)
    TextView termsOfServiceTv;
    @BindView(R.id.re_send)
    TextView sendTv;

    private CountDownTimerTool timer;
    private String mobile;
    private String code;
    private String pass;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CountDownTimerTool.TIMER_TICK:
                    sendTv.setEnabled(false);
                    sendTv.setText(String.valueOf(msg.arg1));
                    break;
                case CountDownTimerTool.TIMER_FINISH:
                    sendTv.setEnabled(true);
                    sendTv.setText(getString(R.string.btn_re_send));
                    break;
                case GET_CODE_SUCCESS:
                    dismissLoading();
                    startTimer(60);
                    break;
            }
        }
    };

    // 倒计时
    private void startTimer(int time) {
        timer = new CountDownTimerTool(mHandler, time * 1000, 1000);
        timer.start();
    }

    private void sendCode() {
        mobile = mobileEt.getText().toString();
        if (!StringTool.isEmailOrPhone(mContext, mobile)) {
            return;
        }

        mPresenter.signUpCode(mobile);
    }

    private void doSignUp() {
        mobile = mobileEt.getText().toString();
        if (!StringTool.isEmailOrPhone(mContext, mobile))
            return;

        code = codeEt.getText().toString();
        if (!StringTool.isCodeLength(code)) {
            ToastUtil.toast(getString(R.string.toast_not_code_length));
            return;
        }

        pass = passEt.getText().toString();
        if (!StringTool.isPassLength(pass)) {
            ToastUtil.toast(getString(R.string.toast_not_pass_length));
            return;
        }

        mPresenter.verify(mobile, code, "0");
    }

    @OnClick({R.id.login, R.id.sign_in, R.id.help, R.id.re_send, R.id.cancel, R.id.terms_of_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                LoginActivity.start(mContext);
                break;
            case R.id.sign_in:
                doSignUp();
                break;
            case R.id.help:
                ActionUtil.startAct(mContext, ActContactCS.class);
                break;
            case R.id.re_send:
                sendCode();
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.terms_of_service:
                WebViewActivity.start(mContext, getString(R.string.key_terms_of_service),
                        H5Service.TERMS_OF_SERVICE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected SignUpPresenter getPresenter() {
        return new SignUpPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        String str = getString(R.string.btn_terms_of_service_confirm);
        str = String.format(str, "<u>", "</u>");
        termsOfServiceTv.setText(Html.fromHtml(str));
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    public void retrieveSuccess(BaseBean response) {
        ToastUtil.toast(R.string.toast_code_send_success);
        mHandler.sendEmptyMessage(GET_CODE_SUCCESS);
    }

    @Override
    public void verifySuccess(BaseBean response) {
        Intent intent = new Intent(mContext, SetInfoActivity.class);
        intent.putExtra("emailOrPhone", mobile);
        intent.putExtra("code", code);
        intent.putExtra("pass", pass);
        startActivity(intent);
    }

    @Override
    public void resetSuccess(UserResult response) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThreadMainEvent(SignInEvent event) {
        finish();
    }
}
