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
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CouponBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.event.SelectCouponEvent;
import com.zconly.pianocourse.mvp.presenter.CouponPresenter;
import com.zconly.pianocourse.mvp.view.CouponView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * @Description: 我的优惠券
 * @Author: dengbin
 * @CreateDate: 2020/6/18 23:31
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/6/18 23:31
 * @UpdateRemark: 更新说明
 */
public class MyCouponActivity extends BaseMvpActivity<CouponPresenter> implements CouponView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private boolean select;
    private int page;

    private BaseQuickAdapter<CouponBean, BaseViewHolder> mAdapter = new BaseQuickAdapter<CouponBean, BaseViewHolder>(
            R.layout.item_counpon, null) {
        @Override
        protected void convert(@NonNull BaseViewHolder helper, CouponBean item) {
            helper.setText(R.id.coupon_name_tv, item.getDescription());
            helper.setText(R.id.coupon_amount_tv, "￥" + item.getDenomination());
            helper.setText(R.id.coupon_time_tv, DateUtils.formatYMD(item.getEffective()) + " - "
                    + DateUtils.formatYMD(item.getExpire()));
        }
    };

    public static void start(Context context, boolean select) {
        Intent intent = new Intent(context, MyCouponActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, select);
        context.startActivity(intent);
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle("我的优惠券");
        select = getIntent().getBooleanExtra(ExtraConstants.EXTRA_DATA, false);
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
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!select)
                return;
            SelectCouponEvent event = new SelectCouponEvent(mAdapter.getItem(position));
            EventBus.getDefault().post(event);
            finish();
        });
        mAdapter.setOnLoadMoreListener(this::requestData, mRecyclerView);
    }

    private void requestData() {
        mPresenter.getMyCoupon(page, 0, 0, 0, Constants.STATUS_COUPON_ACTIVE);
    }

    @Override
    protected int getContentView() {
        return R.layout.view_refresh_recycler;
    }

    @Override
    protected CouponPresenter getPresenter() {
        return new CouponPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getCouponListSuccess(CouponBean.CouponListResult result) {
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
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void obtainCouponSuccess(BaseBean response) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

}
