package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.cloud.videoplayer.util.VideoPlayerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.ExtraConstants;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.CourseResult;
import com.zconly.pianocourse.event.CourseEvent;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.view.CourseView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/31 16:59
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/31 16:59
 * @UpdateRemark: 更新说明
 */
public class CourseDetailActivity extends BaseMvpActivity<CoursePresenter> implements CourseView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CourseBean courseBean;
    private int page;
    private MyAdapter mAdapter;
    private MHeader mHeader;

    public static void start(Context context, CourseBean bean) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, bean);
        context.startActivity(intent);
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void initView() {
        mTitleView.setTitle("课程详情");
        courseBean = (CourseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
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
        mRecyclerView.setAdapter(mAdapter = new MyAdapter(null));
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_course_detail, mRecyclerView,
                false);
        mHeader = new MHeader(view);
        mAdapter.addHeaderView(view);
    }

    private void getData() {
        courseBean = (CourseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        CourseResult result = new CourseResult();
        result.setData(courseBean);
        getCourseSuccess(result);
    }

    @Override
    protected void initData() {
        getData();
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
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    public void getCourseListSuccess(CourseListResult response) {

    }

    @Override
    public void getCourseSuccess(CourseResult response) {
        mHeader.setData(response.getData());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CourseEvent event) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHeader != null)
            mHeader.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHeader.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHeader.onDestroy();
    }

    private static class MyAdapter extends BaseQuickAdapter<CourseBean, BaseViewHolder> {

        MyAdapter(@Nullable List<CourseBean> data) {
            super(data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, CourseBean item) {

        }
    }

    class MHeader {

        @BindView(R.id.header_course_bd_video)
        VideoPlayerView videoView;

        MHeader(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(CourseBean data) {
            videoView.startPlay(Uri.parse(data.getVideoUrl()));
        }

        void onPause() {
            videoView.onPause();
        }

        void onResume() {
            if (videoView != null)
                videoView.onResume();
        }

        void onDestroy() {
            videoView.onActivityDestroy();
        }

    }

}
