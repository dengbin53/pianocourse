package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.VideoListAdapter;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.ExtraConstants;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.LiveBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.VideoListResult;
import com.zconly.pianocourse.event.CourseEvent;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.view.CourseView;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.ImgLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 课程详情
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
    private VideoListAdapter mAdapter;
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
        mTitleView.setTitle("大师课目录");
        courseBean = (CourseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> initData());
        mSmartRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(mAdapter = new VideoListAdapter(null));
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_course_detail, mRecyclerView,
                false);
        mHeader = new MHeader(view);
        mAdapter.addHeaderView(view);
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            VideoDetailActivity.start(mContext, mAdapter.getItem(position), courseBean);
        });
    }

    @Override
    protected void initData() {
        mHeader.setData();
        mPresenter.getVideoList(courseBean.getId());
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
        mAdapter.setNewData(response.getData());
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    @Override
    public void getLiveDataSuccess(LiveBean response) {

    }

    @Override
    public void getEvaluateSuccess(EvaluateBean.EvaluateListResult response) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CourseEvent event) {

    }

    class MHeader {

        @BindView(R.id.header_course_iv)
        ImageView imageView;
        @BindView(R.id.header_course_title_tv)
        TextView titleTv;
        @BindView(R.id.header_course_teacher_tv)
        TextView teacherTv;
        @BindView(R.id.header_course_desc_tv)
        TextView descTv;
        @BindView(R.id.header_course_like_tv)
        TextView likeTv;
        @BindView(R.id.header_course_favorite_tv)
        TextView favoriteTv;

        MHeader(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData() {
            ImgLoader.showImg(DataUtil.getImgUrl(courseBean.getCover()), imageView);
            titleTv.setText(courseBean.getTitle());
            teacherTv.setText(courseBean.getTeacher());
            descTv.setText(courseBean.getDescription());
            likeTv.setText(courseBean.getLike_count() + "次点赞");
            favoriteTv.setText(courseBean.getFavorite_count() + "次收藏");
        }

    }

}
