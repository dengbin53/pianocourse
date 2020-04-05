package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.TokenBean;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.event.SignInEvent;
import com.zconly.pianocourse.mvp.presenter.LoginPresenter;
import com.zconly.pianocourse.mvp.view.LoginView;
import com.zconly.pianocourse.util.ActionUtil;
import com.zconly.pianocourse.util.StringTool;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.util.ViewTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 登录
 * @Author: dengbin
 * @CreateDate: 2020/3/19 20:39
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/19 20:39
 * @UpdateRemark: 更新说明
 */
public class SignInActivity extends BaseMvpActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.country)
    TextView countryTv;
    @BindView(R.id.email)
    EditText emailEt;
    @BindView(R.id.pass)
    EditText passEt;

    public static void start(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (context instanceof BaseMvpActivity)
            ((BaseMvpActivity) context).startActivityUp(intent);
        else
            context.startActivity(intent);
    }

    private void doSignIn() {

        String emailOrPhone = emailEt.getText().toString();

        if (!StringTool.isEmailOrPhone(mContext, emailOrPhone))
            return;

        String pwd = passEt.getText().toString();

        if (!StringTool.isPassLength(pwd)) {
            ToastUtil.toast(getString(R.string.toast_not_pass_length));
            return;
        }

        SysConfigTool.setLastLoginContact(emailOrPhone);

        loading("");

        mPresenter.signIn(emailOrPhone, pwd);
    }

    @SingleClick
    @OnClick({R.id.login, R.id.sign_up, R.id.find_pass, R.id.cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                doSignIn();
                break;
            case R.id.sign_up:
                ActionUtil.startAct(mContext, SignUpActivity.class);
                break;
            case R.id.find_pass:
                FindPassActivity.start(mContext, emailEt.getText().toString());
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    protected boolean initView() {
        String llc = SysConfigTool.getLastLoginContact();
        if (!TextUtils.isEmpty(llc)) {
            emailEt.setText(llc);
            ViewTool.setSelection(new EditText[]{emailEt});
        }

        emailEt.setHint(getString(R.string.hint_phone));

        return true;
    }

    @Override
    protected void initData() {
        SysConfigTool.logout();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    public void finish() {
        finishDown();
    }

    @Override
    public void loginSuccess(TokenBean.TokenResult response) {
        String token = response.getData().getToken();
        SysConfigTool.saveToken(token);
        mPresenter.getUserInfo(0);
    }

    @Override
    public void updateUserSuccess(UserBean.UserResult response) {

    }

    @Override
    public void getUserInfoSuccess(UserBean.UserResult response) {
        SysConfigTool.setUser(response.getData());
        EventBus.getDefault().post(new SignInEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThreadMainEvent(SignInEvent event) {
        finishDown();
    }

}
