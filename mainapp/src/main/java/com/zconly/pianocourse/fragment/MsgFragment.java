package com.zconly.pianocourse.fragment;

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
import com.zconly.pianocourse.activity.SignInActivity;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.bean.MsgBean;
import com.zconly.pianocourse.bean.result.MsgResult;
import com.zconly.pianocourse.event.SignInEvent;
import com.zconly.pianocourse.mvp.presenter.MsgPresenter;
import com.zconly.pianocourse.mvp.view.MsgView;
import com.zconly.pianocourse.util.SysConfigTool;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @Description: 消息
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class MsgFragment extends BaseMvpFragment<MsgPresenter> implements MsgView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private mAdapter mAdapter;
    private int page;

    private void getData() {
        if (mAdapter.getEmptyViewCount() > 0)
            mAdapter.setEmptyView(null);
        mPresenter.getMsg(page);
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new mAdapter(null));
        if (!SysConfigTool.isLogin()) {
            View ev = LayoutInflater.from(mContext).inflate(R.layout.view_empty_login, mRecyclerView,
                    false);
            ev.findViewById(R.id.empty_login_tv).setOnClickListener(v -> {
                SignInActivity.start(mContext);
            });
            mAdapter.setEmptyView(ev);
        }
    }

    @Override
    protected void initData() {
        if (!SysConfigTool.isLogin())
            return;
        page = 0;
        getData();
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_msg;
    }

    @Override
    protected MsgPresenter getPresenter() {
        return new MsgPresenter(this);
    }

    @Override
    public void getMsgSuccess(MsgResult result) {
        mAdapter.setEmptyView(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SignInEvent event) {
        initData();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    private class mAdapter extends BaseQuickAdapter<MsgBean, BaseViewHolder> {

        mAdapter(@Nullable List<MsgBean> data) {
            super(R.layout.item_list_msg, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, MsgBean item) {

        }
    }
}
