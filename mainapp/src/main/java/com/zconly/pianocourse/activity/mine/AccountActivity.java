package com.zconly.pianocourse.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BalanceBean;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.BoughtRecordBean;
import com.zconly.pianocourse.bean.ChangeRecordBean;
import com.zconly.pianocourse.mvp.presenter.AccountPresenter;
import com.zconly.pianocourse.mvp.view.AccountView;
import com.zconly.pianocourse.widget.dialog.DialogRecharge;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 账户
 * @Author: dengbin
 * @CreateDate: 2020/6/18 23:31
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/6/18 23:31
 * @UpdateRemark: 更新说明
 */
public class AccountActivity extends BaseMvpActivity<AccountPresenter> implements AccountView {

    @BindView(R.id.account_amount_tv)
    TextView amountTv;

    public static void start(Context context) {
        Intent intent = new Intent(context, AccountActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.account_update_tv, R.id.account_coupon_kvv, R.id.account_trading_record_kvv,
            R.id.account_recharge_tv})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_update_tv: // 更新
                initData();
                break;
            case R.id.account_coupon_kvv: // 优惠券
                MyCouponActivity.start(mContext, false);
                break;
            case R.id.account_trading_record_kvv: // 交易记录
                TradingRecordActivity.start(mContext);
                break;
            case R.id.account_recharge_tv: // 充值
                DialogRecharge settingDialog = DialogRecharge.getInstance((DialogRecharge.ClickListener) bean -> {
                    int amount = bean.getAmount();
                    // TODO: 2020/6/22 充值
                });
                settingDialog.show(getSupportFragmentManager(), null);
                break;
        }
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle("账户");
        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void initData() {
        mPresenter.balance();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_account;
    }

    @Override
    protected AccountPresenter getPresenter() {
        return new AccountPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void balanceSuccess(BalanceBean.BalanceResult response) {
        amountTv.setText("￥" + response.getData().getBalance());
    }

    @Override
    public void changeRecordListSuccess(ChangeRecordBean.ChangeRecordListResult response) {

    }

    @Override
    public void boughtRecordListSuccess(BoughtRecordBean.BoughtRecordListResult response) {

    }

    @Override
    public void buySuccess(BaseBean response) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

}
