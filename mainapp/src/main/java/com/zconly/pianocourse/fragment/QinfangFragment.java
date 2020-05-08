package com.zconly.pianocourse.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.BookDetailActivity;
import com.zconly.pianocourse.activity.BookListActivity;
import com.zconly.pianocourse.activity.ExerciseListActivity;
import com.zconly.pianocourse.adapter.SheetListAdapter;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.constants.BookLevel;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.mvp.presenter.QinfangPresenter;
import com.zconly.pianocourse.mvp.view.QinfangView;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 琴房
 * @Author: dengbin
 * @CreateDate: 2020/4/26 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/26 21:03
 * @UpdateRemark: 更新说明
 */
public class QinfangFragment extends BaseMvpFragment<QinfangPresenter> implements QinfangView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SheetListAdapter mAdapter;
    private MHeader mHeader;
    private int page;
    private long bookId = 15;

    private void requestData() {
        mPresenter.getBookList(0, 0, null, null, null, 0);
        mPresenter.getBanner();
    }

    private void addHeaderView() {
        if (mHeader != null)
            return;
        View header = getLayoutInflater().inflate(R.layout.header_qinfang, mRecyclerView, false);
        mHeader = new MHeader(header);
        mAdapter.addHeaderView(header);
    }

    @SingleClick
    @OnClick({R.id.classroom_level_0, R.id.classroom_level_1, R.id.classroom_level_2, R.id.classroom_level_3,
            R.id.classroom_my_sheet_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.classroom_level_0:
                BookListActivity.start(mContext, BookLevel.LEVEL_0);
                break;
            case R.id.classroom_level_1:
                BookListActivity.start(mContext, BookLevel.LEVEL_1);
                break;
            case R.id.classroom_level_2:
                BookListActivity.start(mContext, BookLevel.LEVEL_2);
                break;
            case R.id.classroom_level_3:
                BookListActivity.start(mContext, BookLevel.LEVEL_3);
                break;
            case R.id.classroom_my_sheet_tv: // 我的演奏
                ExerciseListActivity.start(mContext);
                break;
        }
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getSheetList(page, 0, bookId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                requestData();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new SheetListAdapter(null));

        mAdapter.addFooterView(getLayoutInflater().inflate(R.layout.view_space_large, mRecyclerView,
                false));
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
        return R.layout.fragment_qinfang;
    }

    @Override
    protected QinfangPresenter getPresenter() {
        return new QinfangPresenter(this);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {
        addHeaderView();
        mHeader.updateBanner(response.getData());
    }

    @Override
    public void getBookListSuccess(BookBean.BookListResult response) {
        if (response.getData() == null)
            return;
        addHeaderView();
        mHeader.updateBook(response.getData().getData());
        mPresenter.getSheetList(page, 0, bookId);
    }

    @Override
    public void getSheetListSuccess(SheetBean.SheetListResult response) {
        if (response.getData() == null || response.getData().getData() == null)
            return;
        isLoadDataCompleted = true;
        if (page == 0) {
            mAdapter.setNewData(response.getData().getData());
        } else {
            mAdapter.addData(response.getData().getData());
        }

        page++;
        mRefreshLayout.setEnableLoadMore(response.getData().getData().size() >= Constants.PAGE_COUNT);
    }

    @Override
    public void getExerciseListSuccess(ExerciseBean.ExerciseListResult response) {

    }

    static class MHeader {

        @BindView(R.id.home_banner)
        ConvenientBanner<BannerBean> banner;
        @BindView(R.id.header_qifang_rv)
        RecyclerView qinfangRv;

        private final MHeaderAdapter mAdapter;

        MHeader(View view) {
            ButterKnife.bind(this, view);

            qinfangRv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,
                    false));
            qinfangRv.setAdapter(mAdapter = new MHeaderAdapter(null));
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

        void updateBook(List<BookBean> books) {
            mAdapter.setNewData(books);
        }

    }

    private static class MHeaderAdapter extends BaseQuickAdapter<BookBean, BaseViewHolder> {

        MHeaderAdapter(List<BookBean> data) {
            super(R.layout.item_list_book_h, data);

            // 跳转书本详情
            setOnItemClickListener((adapter, view, position)
                    -> BookDetailActivity.start(mContext, getItem(position)));
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, BookBean item) {
            ImgLoader.showImgSmall(DataUtil.getImgUrl(item.getCover()), helper.getView(R.id.item_book_h_iv));
            helper.setText(R.id.item_book_h_author_tv, item.getAuthor());
            helper.setText(R.id.item_book_h_name_tv, item.getName());
        }
    }

}
