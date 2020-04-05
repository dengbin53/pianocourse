package com.zconly.pianocourse.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.FindPassActivity;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.AppUpdateBean;
import com.zconly.pianocourse.event.LogoutEvent;
import com.zconly.pianocourse.mvp.presenter.SettingPresenter;
import com.zconly.pianocourse.mvp.view.SettingView;
import com.zconly.pianocourse.util.DataCleanManager;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.widget.MKeyValueView;
import com.zconly.pianocourse.widget.dialog.DialogConfirm;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends BaseMvpActivity<SettingPresenter> implements SettingView {

    @BindView(R.id.act_setting_modify)
    MKeyValueView modifyPassView;
    @BindView(R.id.act_setting_clean_cache)
    MKeyValueView cleanCacheView;
    @BindView(R.id.act_setting_push_switch)
    Switch pushSwitch;
    @BindView(R.id.act_setting_upgrade)
    MKeyValueView upgradeView;
    @BindView(R.id.act_setting_logout)
    TextView logoutTv;

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, SettingActivity.class);
        mContext.startActivity(intent);
    }

    @OnClick({R.id.act_setting_modify, R.id.act_setting_clean_cache, R.id.act_setting_upgrade,
            R.id.act_setting_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_setting_modify:
                FindPassActivity.start(mContext, null);
                break;
            case R.id.act_setting_clean_cache:
                DialogConfirm confirm = new DialogConfirm(mContext, v1 -> {
                    if (v1.getId() == R.id.dialog_confirm_btnleft) {
                        DataCleanManager.clearAllCache(mContext);
                        ToastUtil.toast(getString(R.string.toast_done));
                        cleanCacheView.setValue(getString(R.string.value_clean_cache));
                    }
                }, getString(R.string.confirm_clean_cache));
                confirm.show(getSupportFragmentManager(), null);
                break;
            case R.id.act_setting_upgrade:
                //loading(false);
                //ProtocolManager.checkVersion(context, versionCallback);
                // PgyUpdateManager.register(this);
                break;
            case R.id.act_setting_logout:
                confirm = new DialogConfirm(mContext, v12 -> {
                    if (v12.getId() == R.id.dialog_confirm_btnleft) {
                        SysConfigTool.logout();
                        EventBus.getDefault().post(new LogoutEvent());
                        finish();
                    }
                }, getString(R.string.confirm_logout));
                confirm.show(getSupportFragmentManager(), null);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean initView() {
        mTitleView.setTitle(getString(R.string.title_setting));
        cleanCacheView.setValue(DataCleanManager.getTotalCacheSize(mContext));
        return true;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected SettingPresenter getPresenter() {
        return new SettingPresenter(this);
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
    public void getAppUpdateSuccess(AppUpdateBean.AppUpdateResult result) {

    }
}
