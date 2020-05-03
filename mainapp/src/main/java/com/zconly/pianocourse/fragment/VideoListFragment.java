package com.zconly.pianocourse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.VideoDetailActivity;
import com.zconly.pianocourse.adapter.VideoListAdapter;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.FavoriteBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.mvp.presenter.FavoritePresenter;
import com.zconly.pianocourse.mvp.view.FavoriteView;
import com.zconly.pianocourse.util.DataUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @Description: 视频列表
 * @Author: dengbin
 * @CreateDate: 2020/4/3 10:48
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 10:48
 * @UpdateRemark: 更新说明
 */
public class VideoListFragment extends BaseMvpFragment<FavoritePresenter> implements FavoriteView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private VideoListAdapter<VideoBean> mAdapter;
    private int category;
    private int page;

    public static VideoListFragment getInstance(int category) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle arg = new Bundle();
        arg.putInt(ExtraConstants.EXTRA_COURSE_TYPE, category);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle == null)
            return;
        category = bundle.getInt(ExtraConstants.EXTRA_COURSE_TYPE);

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getData();
            }
        });
        mSmartRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(mAdapter = new VideoListAdapter<>(null));
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            VideoBean b = mAdapter.getItem(position);
            VideoDetailActivity.start(mContext, b);
        });
        mAdapter.addHeaderView(LayoutInflater.from(mContext)
                .inflate(R.layout.view_space_normal, mRecyclerView, false));
    }

    private void getData() {
        mPresenter.getFavoriteList(page, Constants.TYPE_FAVORITE_VIDEO);
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.view_refresh_recycler;
    }

    @Override
    protected FavoritePresenter getPresenter() {
        return new FavoritePresenter(this);
    }

    @Override
    public void getFavoriteListSuccess(FavoriteBean.FavoriteListResult response) {
        isLoadDataCompleted = true;
        if (response.getData() == null)
            return;
        List<FavoriteBean> beans = response.getData().getData();
        List<VideoBean> data = DataUtil.parseFavoriteVideo(beans);
        if (page == 0) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        page++;
        mSmartRefreshLayout.setEnableLoadMore(data.size() >= Constants.PAGE_COUNT);
    }

    @Override
    public void favoriteSuccess(BaseBean response) {

    }

    @Override
    public void likeSuccess(BaseBean response) {

    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

}
