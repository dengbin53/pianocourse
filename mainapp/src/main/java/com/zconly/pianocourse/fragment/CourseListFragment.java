package com.zconly.pianocourse.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.CourseListAdapter;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.base.ExtraConstants;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.CourseResult;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.view.CourseView;

import butterknife.BindView;

/**
 * @Description: 课程列表
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class CourseListFragment extends BaseMvpFragment<CoursePresenter> implements CourseView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CourseListAdapter mAdapter;
    private int type;
    private int page;

    public static CourseListFragment getInstance(int type) {
        CourseListFragment fragment = new CourseListFragment();
        Bundle arg = new Bundle();
        arg.putInt(ExtraConstants.EXTRA_COURSE_TYPE, type);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle == null)
            return;
        type = bundle.getInt(ExtraConstants.EXTRA_COURSE_TYPE);

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
        mRecyclerView.setAdapter(mAdapter = new CourseListAdapter(null));
    }

    private void getData() {
        mPresenter.getCourseList(type, page);
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
    protected CoursePresenter getPresenter() {
        return new CoursePresenter(this);
    }

    @Override
    public void getCourseListSuccess(CourseListResult response) {
        mAdapter.setNewData(response.getData());
    }

    @Override
    public void getCourseSuccess(CourseResult response) {

    }
}
