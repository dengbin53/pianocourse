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
import com.zconly.pianocourse.base.RequestCode;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.result.UserResult;
import com.zconly.pianocourse.mvp.presenter.SignUpPresenter;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.SignUpView;
import com.zconly.pianocourse.util.ActionTool;
import com.zconly.pianocourse.util.CountDownTimerTool;
import com.zconly.pianocourse.util.StringTool;
import com.zconly.pianocourse.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 * Created by dengbin
 */
public class SignUpActivity extends BaseMvpActivity<SignUpPresenter> implements SignUpView {

    private static final int GET_CODE_SUCCESS = 0;

    @BindView(R.id.email)
    EditText emailEt;
    @BindView(R.id.pass)
    EditText passEt;
    @BindView(R.id.verification_code)
    EditText codeEt;
    @BindView(R.id.terms_of_service)
    TextView termsOfServiceTv;
    @BindView(R.id.re_send)
    TextView sendTv;

    private CountDownTimerTool timer;
    private String emailOrPhone;
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
        emailOrPhone = emailEt.getText().toString();
        if (!StringTool.isEmailOrPhone(mContext, emailOrPhone)) {
            return;
        }

        mPresenter.retrieve(emailOrPhone);
    }

    private void doSignUp() {

        emailOrPhone = emailEt.getText().toString();
        if (!StringTool.isEmailOrPhone(mContext, emailOrPhone))
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

        mPresenter.verify(emailOrPhone, pass);
    }

    @OnClick({R.id.login, R.id.sign_in, R.id.help, R.id.re_send, R.id.cancel, R.id.terms_of_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityUp(intent);
                break;
            case R.id.sign_in:
                doSignUp();
                break;
            case R.id.help:
                ActionTool.startAct(mContext, ActContactCS.class);
                break;
            case R.id.re_send:
                sendCode();
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.terms_of_service:
                intent = new Intent(mContext, ActWebView.class);
                intent.putExtra("title", getString(R.string.key_terms_of_service));
                intent.putExtra("url", H5Service.TERMS_OF_SERVICE);
                ActionTool.startAct(mContext, intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == RequestCode.SIGN_IN) {
            setResult(RESULT_OK);
            finish();
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
        return false;
    }

    @Override
    protected void initView() {
        String str = getString(R.string.btn_terms_of_service_confirm);
        str = String.format(str, "<u>", "</u>");

        termsOfServiceTv.setText(Html.fromHtml(str));

        codeEt.setHint(getString(R.string.hint_verification_code_phone));
        emailEt.setHint(getString(R.string.hint_phone));
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
        intent.putExtra("emailOrPhone", emailOrPhone);
        intent.putExtra("code", code);
        intent.putExtra("pass", pass);
        startActivity(intent);
    }

    @Override
    public void resetSuccess(UserResult response) {

    }
}
