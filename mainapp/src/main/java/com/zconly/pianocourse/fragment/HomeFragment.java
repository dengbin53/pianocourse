package com.zconly.pianocourse.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.CourseListActivity;
import com.zconly.pianocourse.activity.XiaoeActivity;
import com.zconly.pianocourse.adapter.CourseListAdapter;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.HomePageBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.view.CourseView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 首页
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class HomeFragment extends BaseMvpFragment<CoursePresenter> implements CourseView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CourseListAdapter mAdapter;
    private MHeader mHeader;
    private HomePageBean.RecommendBean liveBean;

    private void requestData() {
        mPresenter.getHomePageJson();
        mPresenter.getBanner();
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> requestData());
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new CourseListAdapter(null));

        View header = getLayoutInflater().inflate(R.layout.header_course_home, mRecyclerView, false);
        mHeader = new MHeader(header);
        mAdapter.addHeaderView(header);
        mAdapter.addFooterView(getLayoutInflater().inflate(R.layout.view_space_large, mRecyclerView, false));
    }

    @Override
    protected void initData() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected CoursePresenter getPresenter() {
        return new CoursePresenter(this);
    }

    @Override
    public void getCourseListSuccess(CourseBean.CourseListResult response) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {
        mHeader.updateBanner(response.getData());
    }

    @Override
    public void getVideoListSuccess(VideoBean.VideoListResult response) {

    }

    @Override
    public void getCommentSuccess(CommentBean.CommentListResult response) {

    }

    @Override
    public void addCommentSuccess(CommentBean.CommentResult response) {

    }

    @Override
    public void getHomePageSuccess(HomePageBean.HomePageResult response) {
        HomePageBean tuijianBean = response.getTuijian();
        if (tuijianBean == null)
            return;

        isLoadDataCompleted = true;

        List<CourseBean> data = new ArrayList<>();
        liveBean = tuijianBean.getLive();
        if (liveBean != null) {
            CourseBean cData = new CourseBean();
            cData.setTitle("直播课");
            cData.setUrl(liveBean.getUrl());
            cData.setViewType(CourseBean.LIVE_TITLE);
            data.add(cData);

            cData = liveBean.getLesson() == null ? new CourseBean() : liveBean.getLesson();
            cData.setUrl(liveBean.getUrl());
            // cData.setViewType(CourseBean.LIVE);
            cData.setType(liveBean.getType());
            data.add(cData);
        }

        mAdapter.setNewData(data);

        List<HomePageBean.RecommendBean> recommendBean = tuijianBean.getRecommend();
        if (ArrayUtil.isEmpty(recommendBean))
            return;
        data = new ArrayList<>();
        CourseBean cData = new CourseBean();
        cData.setTitle("大师课");
        cData.setCategory(Constants.CATEGORY_PARENTS_COURSE);
        cData.setViewType(CourseBean.TITLE);
        data.add(cData);

        for (HomePageBean.RecommendBean bean : recommendBean) {
            CourseBean courseBean = bean.getLesson();
            if (courseBean == null)
                continue;
            courseBean.setType(bean.getType());
            courseBean.setUrl(bean.getUrl());
            data.add(courseBean);
        }
        mAdapter.addData(data);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    class MHeader {

        @BindView(R.id.header_course_entrance_ll)
        LinearLayout entranceLl;
        @BindView(R.id.home_banner)
        ConvenientBanner<BannerBean> banner;
        @BindView(R.id.header_course_tv0)
        TextView tv0;
        @BindView(R.id.header_course_tv1)
        TextView tv1;
        @BindView(R.id.header_course_tv2)
        TextView tv2;

        MHeader(View view) {
            ButterKnife.bind(this, view);

            entranceLl.setVisibility(View.GONE);
        }

        @SingleClick
        @OnClick({R.id.header_course_tv0, R.id.header_course_tv1, R.id.header_course_tv2})
        void onClick(View view) {
            switch (view.getId()) {
                case R.id.header_course_tv0:
                    CourseListActivity.start(mContext, Constants.CATEGORY_PARENTS_COURSE);
                    break;
                case R.id.header_course_tv1: // 直播课
                    XiaoeActivity.start(mContext, liveBean.getUrl());
                    break;
                case R.id.header_course_tv2:

                    break;
                default:
                    break;
            }
        }

        void updateBanner(List<BannerBean> bannerBeans) {
            int width = DeviceUtils.getScreenWidth();
            int height = ViewUtil.getBannerHeight(width, Constants.BANNER_ASPECT_RATIO_0);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) banner.getLayoutParams();
            lp.width = width;
            lp.height = height;
            banner.setLayoutParams(lp);

            ViewUtil.updateBanner(banner, bannerBeans);
        }

    }

}
