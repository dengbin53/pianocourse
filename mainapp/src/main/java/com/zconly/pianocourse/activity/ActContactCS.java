package com.zconly.pianocourse.activity;

import android.view.View;

import com.mvp.base.MvpPresenter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.base.RequestCode;
import com.zconly.pianocourse.util.ActionUtil;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.widget.TitleView;

import butterknife.OnClick;

public class ActContactCS extends BaseMvpActivity {

    @OnClick({R.id.call, R.id.chat})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:
                ActionUtil.call(mContext, Constants.CS_CALL);
                break;
            case R.id.chat:
                if (SysConfigTool.isLogin()) {
                    ActionUtil.startChat(mContext, Constants.CS_ACCOUNT + "");
                } else {
                    // DialogLogin dl = new DialogLogin(this);
                    // dl.show(getFragmentManager(), null);
                }
                break;
            case R.id.login:
                ActionUtil.startActForRet(mContext, LoginActivity.class, RequestCode.LOGIN);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView() {
        ((TitleView) mTitleView).setTitle(getString(R.string.key_contact_cs));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_contact_cs;
    }

    @Override
    protected MvpPresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }
}
