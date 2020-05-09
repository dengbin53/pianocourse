package com.zconly.pianocourse.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.mvp.presenter.SignUpPresenter;
import com.zconly.pianocourse.util.SysConfigTool;

import butterknife.OnClick;

/**
 * 注册
 * Created by dengbin
 */
public class SignUpVipActivity extends BaseMvpActivity {

    public static void start(BaseMvpActivity context) {
        Intent intent = new Intent(context, SignUpVipActivity.class);
        context.startActivityNoAnim(intent);
    }

    @SingleClick
    @OnClick({R.id.sign_up_vip_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_vip_iv:
                if (SysConfigTool.isLogin(mContext, false)) {
                    CourseListActivity.start(mContext, SysConfigTool.getUser().getRole_id());
                }
                finishFade();
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
        return R.layout.activity_sign_up_vip;
    }

    @Override
    protected SignUpPresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected boolean initView() {
        return false;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

}
