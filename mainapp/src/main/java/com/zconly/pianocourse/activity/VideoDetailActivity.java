package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.base.ExtraConstants;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.LiveBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.VideoListResult;
import com.zconly.pianocourse.event.CourseEvent;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.view.CourseView;
import com.zconly.pianocourse.mvp.view.FavoriteView;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.ImgLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 视频详情
 * @Author: dengbin
 * @CreateDate: 2020/3/31 16:59
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/31 16:59
 * @UpdateRemark: 更新说明
 */
public class VideoDetailActivity extends BaseMvpActivity<CoursePresenter> implements CourseView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private VideoBean videoBean;
    private CourseBean courseBean;
    private int page;
    private MyAdapter mAdapter;
    private MHeader mHeader;

    public static void start(Context context, VideoBean bean, CourseBean courseBean) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, bean);
        intent.putExtra(ExtraConstants.EXTRA_DATA_COURSE, courseBean);
        context.startActivity(intent);
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void initView() {
        mTitleView.setTitle("课程详情");
        videoBean = (VideoBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        courseBean = (CourseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA_COURSE);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_video_detail, mRecyclerView,
                false);
        mHeader = new MHeader(view);
        mAdapter.addHeaderView(view);
    }

    private void getData() {
        mPresenter.getEvaluateList(videoBean.getId(), page);
    }

    @Override
    protected void initData() {
        getData();
        mHeader.setData(videoBean);
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
    public void getVideoListSuccess(VideoListResult response) {
        mHeader.setData(response.getData().get(0));
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    @Override
    public void getLiveDataSuccess(LiveBean response) {

    }

    @Override
    public void getEvaluateSuccess(EvaluateBean.EvaluateListResult response) {
        if (response.getData() == null)
            return;
        List<EvaluateBean> data = response.getData().getData();
        if (page == 0) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        page++;
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

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishLoadMore();
        mSmartRefreshLayout.finishRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CourseEvent event) {

    }

    private static class MyAdapter extends BaseQuickAdapter<EvaluateBean, BaseViewHolder> {

        MyAdapter(@Nullable List<EvaluateBean> data) {
            super(data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, EvaluateBean item) {

        }
    }

    class MHeader {

        @BindView(R.id.header_course_bd_video)
        VideoPlayerView videoView;
        @BindView(R.id.header_course_title_tv)
        TextView titleTv;
        @BindView(R.id.header_course_teacher_iv)
        ImageView teacherAvatarIv;
        @BindView(R.id.header_course_teacher_tv)
        TextView teacherNameTv;
        @BindView(R.id.header_course_time_tv)
        TextView timeTv;
        @BindView(R.id.header_course_like_tv)
        TextView likeTv;
        @BindView(R.id.header_course_favorite_tv)
        TextView favoriteTv;
        @BindView(R.id.header_video_course_rv)
        RecyclerView courseRv;
        @BindView(R.id.header_video_evaluate_count_tv)
        TextView evaluateCountTv;
        @BindView(R.id.header_video_evaluate_sort_iv)
        ImageView evaluateSortIv;

        private BaseQuickAdapter<CourseBean, BaseViewHolder> mAdapter
                = new BaseQuickAdapter<CourseBean, BaseViewHolder>(R.layout.item_list_course_h, null) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, CourseBean item) {

            }
        };

        MHeader(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(VideoBean data) {
            videoView.startPlay(Uri.parse(data.getUrl()));
            titleTv.setText(data.getTitle());
            ImgLoader.showAvatar(DataUtil.getImgUrl(courseBean.getTeacher_avatar()), teacherAvatarIv);
            teacherNameTv.setText(courseBean.getTeacher());
            timeTv.setText(DateUtils.getTime2M(videoBean.getC_time()));
            likeTv.setText(videoBean.getLike_count() + Constants.END_LIKE);
            favoriteTv.setText(videoBean.getFavorite_count() + Constants.END_FAVORITE);

            courseRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                    false));
            courseRv.setAdapter(mAdapter);
        }

        int totalCount;

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
