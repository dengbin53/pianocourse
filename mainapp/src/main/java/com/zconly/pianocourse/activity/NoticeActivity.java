package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.NoticeBean;
import com.zconly.pianocourse.event.LogoutEvent;
import com.zconly.pianocourse.event.SignInEvent;
import com.zconly.pianocourse.mvp.presenter.NoticePresenter;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.mvp.view.NoticeView;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.SysConfigTool;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @Description: 通知消息
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class NoticeActivity extends BaseMvpActivity<NoticePresenter> implements NoticeView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private mAdapter mAdapter;
    private int page;

    public static void start(Context context) {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle(getString(R.string.title_notice));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMsg(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new mAdapter(null));
        return true;
    }

    @Override
    protected void initData() {
        if (!SysConfigTool.isLogin(mContext, false)) {
            View ev = LayoutInflater.from(mContext).inflate(R.layout.view_empty_login, mRecyclerView,
                    false);
            ev.findViewById(R.id.empty_login_tv).setOnClickListener(v -> SignInActivity.start(mContext));
            mAdapter.setEmptyView(ev);
            mAdapter.setNewData(null);
            return;
        }
        page = 0;
        mPresenter.getMsg(page);
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.view_refresh_recycler;
    }

    @Override
    protected NoticePresenter getPresenter() {
        return new NoticePresenter(this);
    }

    @Override
    public void getNoticeSuccess(NoticeBean.NoticeResult result) {
        if (result.getData() == null)
            return;
        if (page == 0) {
            mAdapter.setNewData(result.getData().getData());
        } else {
            mAdapter.addData(result.getData().getData());
        }
        page++;
        mRefreshLayout.setEnableLoadMore(page < result.getData().getTotalPage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SignInEvent event) {
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LogoutEvent event) {
        initData();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    private static class mAdapter extends BaseQuickAdapter<NoticeBean, BaseViewHolder> {

        mAdapter(@Nullable List<NoticeBean> data) {
            super(R.layout.item_list_notice, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, NoticeBean item) {
            ImgLoader.showAvatar(H5Service.ADMIN_AVATAR, helper.getView(R.id.notice_iv));
            helper.setText(R.id.notice_name_tv, "消息");
            helper.setText(R.id.notice_time_tv, DateUtils.getTime2M(item.getC_time()));
            helper.setText(R.id.notice_content_tv, item.getContent());
        }
    }
}
