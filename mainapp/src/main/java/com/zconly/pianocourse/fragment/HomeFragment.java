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
import com.zconly.pianocourse.activity.WebViewActivity;
import com.zconly.pianocourse.adapter.CourseListAdapter;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.LiveBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.VideoListResult;
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
    private int page;
    private MHeader mHeader;
    private CourseBean liveBean;

    @Override
    protected void initView(View view) {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> initData());
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new CourseListAdapter(null));

        View header = getLayoutInflater().inflate(R.layout.header_course_home, mRecyclerView, false);
        mHeader = new MHeader(header);
        mAdapter.addHeaderView(header);
        mAdapter.addFooterView(getLayoutInflater().inflate(R.layout.footer_space, mRecyclerView, false));
    }

    @Override
    protected void initData() {
        page = 0;
        mPresenter.getCourseList(page, null, null);
        mPresenter.getBanner();
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
        if (response.getData() == null || ArrayUtil.isEmpty(response.getData().getData()))
            return;
        List<CourseBean> data = response.getData().getData();
        CourseBean titleData = new CourseBean();
        titleData.setTitle("大师课");
        titleData.setCategory(Constants.CATEGORY_PARENTS_COURSE);
        titleData.setViewType(CourseBean.TITLE);
        data.add(0, titleData);
        if (page == 0) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        page++;

        mPresenter.getLiveData();
    }

    @Override
    public void getVideoListSuccess(VideoListResult response) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {
        mHeader.updateBanner(response.getData());
    }

    @Override
    public void getLiveDataSuccess(LiveBean response) {
        CourseBean titleData = new CourseBean();
        titleData.setTitle("直播课");
        titleData.setCover(response.getCover());
        titleData.setUrl(response.getUrl());
        titleData.setViewType(CourseBean.TITLE);

        liveBean = new CourseBean();
        liveBean.setCover(response.getCover());
        liveBean.setUrl(response.getUrl());
        liveBean.setViewType(CourseBean.LIVE);

        List<CourseBean> data = new ArrayList<>();
        data.add(titleData);
        data.add(liveBean);

        mAdapter.addData(data);
    }

    @Override
    public void getCommentSuccess(CommentBean.CommentListResult response) {

    }

    @Override
    public void addCommentSuccess(CommentBean.CommentResult response) {

    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    class MHeader {

        @BindView(R.id.home_banner)
        ConvenientBanner<BannerBean> banner;
        @BindView(R.id.header_course_tv0)
        TextView tv0;
        @BindView(R.id.header_course_tv1)
        TextView tv1;
        @BindView(R.id.header_course_tv2)
        TextView tv2;

        private View view;

        MHeader(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }

        @SingleClick
        @OnClick({R.id.header_course_tv0, R.id.header_course_tv1, R.id.header_course_tv2})
        void onClick(View view) {
            switch (view.getId()) {
                case R.id.header_course_tv0:
                    CourseListActivity.start(mContext, Constants.CATEGORY_PARENTS_COURSE);
                    break;
                case R.id.header_course_tv1:
                    if (liveBean == null)
                        break;
                    WebViewActivity.start(mContext, liveBean.getTitle(), liveBean.getUrl());
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
