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
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.base.ExtraConstants;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.FavoriteBean;
import com.zconly.pianocourse.bean.HomePageBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.presenter.FavoritePresenter;
import com.zconly.pianocourse.mvp.view.CourseView;
import com.zconly.pianocourse.mvp.view.FavoriteView;
import com.zconly.pianocourse.util.DataUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @Description: 课程列表
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class CourseListFragment extends BaseMvpFragment<CoursePresenter> implements CourseView, FavoriteView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CourseListAdapter mAdapter;
    private int category;
    private int page;

    public static CourseListFragment getInstance(int category) {
        CourseListFragment fragment = new CourseListFragment();
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
        mRecyclerView.setAdapter(mAdapter = new CourseListAdapter(null));
    }

    private void getData() {
        if (category == Constants.CATEGORY_FAVORITE_COURSE) {
            new FavoritePresenter(this).getFavoriteList(page, Constants.TYPE_FAVORITE_COURSE);
        } else {
            mPresenter.getCourseList(page, null, category + "");
        }
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
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void getCourseListSuccess(CourseBean.CourseListResult response) {
        if (response.getData() == null)
            return;
        isLoadDataCompleted = true;
        List<CourseBean> data = response.getData().getData();
        if (page == 0) {
            mAdapter.setNewData(data);

            CourseBean courseBean = new CourseBean();
            courseBean.setCover(DataUtil.getCategoryBannerImg(category));
            courseBean.setViewType(CourseBean.BANNER);
            mAdapter.addData(0, courseBean);
        } else {
            mAdapter.addData(data);
        }
        page++;
        mSmartRefreshLayout.setEnableLoadMore(data.size() >= Constants.PAGE_COUNT);
    }

    @Override
    public void getVideoListSuccess(VideoBean.VideoListResult response) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    @Override
    public void getCommentSuccess(CommentBean.CommentListResult response) {

    }

    @Override
    public void addCommentSuccess(CommentBean.CommentResult response) {

    }

    @Override
    public void getHomePageSuccess(HomePageBean.HomePageResult response) {

    }

    @Override
    public void getFavoriteListSuccess(FavoriteBean.FavoriteListResult response) {
        isLoadDataCompleted = true;
        if (response.getData() == null)
            return;
        List<CourseBean> data = DataUtil.parseFavoriteCourse(response.getData().getData());
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

}
