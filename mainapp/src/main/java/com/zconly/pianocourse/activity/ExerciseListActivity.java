package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.ExerciseListAdapter;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.mvp.presenter.QinfangPresenter;
import com.zconly.pianocourse.mvp.view.QinfangView;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;

/**
 * @Description: 学生练习列表
 * @Author: dengbin
 * @CreateDate: 2020/5/3 13:59
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/3 13:59
 * @UpdateRemark: 更新说明
 */
public class ExerciseListActivity extends BaseMvpActivity<QinfangPresenter> implements QinfangView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ExerciseListAdapter mAdapter;
    private int page;

    public static void start(Context context) {
        if (!SysConfigTool.isLogin(context, true))
            return;
        Intent intent = new Intent(context, ExerciseListActivity.class);
        context.startActivity(intent);
    }

    private void requestData() {
        mPresenter.getExerciseList(page, 0, SysConfigTool.getUser().getId(), 0);
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle(SysConfigTool.getUser().getNickname() + " 练习记录");

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                requestData();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(mAdapter = new ExerciseListAdapter(null));
        ViewUtil.cancelRecyclerViewAnim(mRecyclerView);

        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void initData() {
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    protected int getContentView() {
        return R.layout.view_refresh_recycler;
    }

    @Override
    protected QinfangPresenter getPresenter() {
        return new QinfangPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public void getBookListSuccess(BookBean.BookListResult response) {

    }

    @Override
    public void getSheetListSuccess(SheetBean.SheetListResult response) {

    }

    @Override
    public void getExerciseListSuccess(ExerciseBean.ExerciseListResult response) {
        if (response.getData() == null)
            return;
        mAdapter.setNewData(response.getData().getData());
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

}
