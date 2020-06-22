package com.zconly.pianocourse.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.bean.BalanceBean;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.BoughtRecordBean;
import com.zconly.pianocourse.bean.ChangeRecordBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.VideoPackBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.mvp.presenter.AccountPresenter;
import com.zconly.pianocourse.mvp.view.AccountView;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.ImgLoader;

import java.util.List;

import butterknife.BindView;

/**
 * @Description: 已购课程列表
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class BoughtCourseListFragment extends BaseMvpFragment<AccountPresenter> implements AccountView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BoughtCourseAdapter mAdapter;
    private int category;
    private int page;

    public static BoughtCourseListFragment getInstance(int category) {
        BoughtCourseListFragment fragment = new BoughtCourseListFragment();
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
        mRecyclerView.setAdapter(mAdapter = new BoughtCourseAdapter(null));
    }

    private void getData() {
        mPresenter.boughtRecord(page, category);
    }

    @Override
    protected void initData() {
        mSmartRefreshLayout.post(() -> mSmartRefreshLayout.autoRefresh());
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
    protected AccountPresenter getPresenter() {
        return new AccountPresenter(this);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    @Override
    public void balanceSuccess(BalanceBean.BalanceResult response) {

    }

    @Override
    public void changeRecordListSuccess(ChangeRecordBean.ChangeRecordListResult response) {

    }

    @Override
    public void boughtRecordListSuccess(BoughtRecordBean.BoughtRecordListResult response) {
        if (response.getData() == null)
            return;
        isLoadDataCompleted = true;
        List<BoughtRecordBean> data = response.getData().getData();
        if (page == 0) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        page++;
        mSmartRefreshLayout.setEnableLoadMore(data.size() >= Constants.PAGE_COUNT);
    }

    @Override
    public void buySuccess(BaseBean response) {

    }

    private static class BoughtCourseAdapter extends BaseQuickAdapter<BoughtRecordBean, BaseViewHolder> {

        public BoughtCourseAdapter(@Nullable List<BoughtRecordBean> data) {
            super(R.layout.item_list_course, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, BoughtRecordBean bean) {
            CourseBean item = bean.getLesson();
            VideoPackBean packBean = bean.getLvp();
            if (item != null) {
                String url = TextUtils.isEmpty(item.getCover_small()) ? item.getCover() : item.getCover_small();
                ImgLoader.showImgRound(DataUtil.getImgUrl(url), helper.getView(R.id.item_list_course_iv));
                helper.setText(R.id.item_list_course_title_tv, item.getTitle());
                helper.setText(R.id.item_list_course_desc_tv, item.getDescription());
                helper.setText(R.id.item_list_course_teacher_tv, item.getTeacher());
                helper.setText(R.id.item_list_course_count_tv, item.getView_count() + "次观看");
                helper.setText(R.id.item_list_course_status_tv, "");
            } else if (packBean != null) {
                ImgLoader.showImgRound(DataUtil.getImgUrl(packBean.getCover_small()), helper.getView(R.id.item_list_course_iv));
                helper.setText(R.id.item_list_course_title_tv, packBean.getTitle());
                helper.setText(R.id.item_list_course_desc_tv, packBean.getDescription());
                helper.setText(R.id.item_list_course_teacher_tv, packBean.getTeacher());
                helper.setText(R.id.item_list_course_count_tv, packBean.getVideo_count() + "个视频");
                helper.setText(R.id.item_list_course_status_tv, "");
            }
        }
    }
}
