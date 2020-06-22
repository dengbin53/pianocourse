package com.zconly.pianocourse.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.decoration.GridDecoration;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BalanceBean;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.BoughtRecordBean;
import com.zconly.pianocourse.bean.ChangeRecordBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.mvp.presenter.AccountPresenter;
import com.zconly.pianocourse.mvp.view.AccountView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;

/**
 * @Description: 交易记录
 * @Author: dengbin
 * @CreateDate: 2020/6/18 23:31
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/6/18 23:31
 * @UpdateRemark: 更新说明
 */
public class TradingRecordActivity extends BaseMvpActivity<AccountPresenter> implements AccountView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int page;

    private BaseQuickAdapter<ChangeRecordBean, BaseViewHolder> mAdapter = new BaseQuickAdapter<ChangeRecordBean, BaseViewHolder>(
            R.layout.item_counpon, null) {
        @Override
        protected void convert(@NonNull BaseViewHolder helper, ChangeRecordBean item) {
            helper.setText(R.id.coupon_name_tv, item.getType() == Constants.TYPE_CHANGE_RECORD_BUY ? "消费" : "充值");
            helper.setText(R.id.coupon_amount_tv, "￥" + item.getAmount());
            helper.setText(R.id.coupon_time_tv, DateUtils.getTime2M(item.getC_time()));
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, TradingRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle("交易记录");
        mSmartRefreshLayout.post(() -> mSmartRefreshLayout.autoRefresh());
        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void initData() {
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> requestData());
        mSmartRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridDecoration(DeviceUtils.dp2px(8f)));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addFooterView(LayoutInflater.from(mContext).inflate(R.layout.view_space_large, mRecyclerView,
                false));
        ViewUtil.cancelRecyclerViewAnim(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this::requestData, mRecyclerView);
    }

    private void requestData() {
        mPresenter.changeRecord(page);
    }

    @Override
    protected int getContentView() {
        return R.layout.view_refresh_recycler;
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
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    @Override
    public void balanceSuccess(BalanceBean.BalanceResult response) {

    }

    @Override
    public void changeRecordListSuccess(ChangeRecordBean.ChangeRecordListResult result) {
        if (result.getData() == null || ArrayUtil.isEmpty(result.getData().getData()))
            return;
        if (page == 0)
            mAdapter.setNewData(result.getData().getData());
        else
            mAdapter.addData(result.getData().getData());

        if (result.getData().getData().size() < Constants.PAGE_COUNT)
            mAdapter.loadMoreEnd(true);
        else
            page++;
    }

    @Override
    public void boughtRecordListSuccess(BoughtRecordBean.BoughtRecordListResult response) {

    }

    @Override
    public void buySuccess(BaseBean response) {

    }
}
