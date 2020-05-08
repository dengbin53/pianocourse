package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.SheetListAdapter;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.mvp.presenter.QinfangPresenter;
import com.zconly.pianocourse.mvp.view.AbstractFavoriteView;
import com.zconly.pianocourse.mvp.view.QinfangView;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 书详情
 * @Author: dengbin
 * @CreateDate: 2020/5/3 13:59
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/3 13:59
 * @UpdateRemark: 更新说明
 */
public class BookDetailActivity extends BaseMvpActivity<QinfangPresenter> implements QinfangView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BookBean bookBean;
    private SheetListAdapter mAdapter;
    private MHeader mHeader;
    private int page;

    public static void start(Context context, BookBean bean) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, bean);
        context.startActivity(intent);
    }

    private void requestData() {
        mPresenter.getSheetList(page, 0, bookBean.getId());
    }

    @Override
    protected boolean initView() {
        bookBean = (BookBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        if (bookBean == null) {
            finish();
            return false;
        }
        mTitleView.setTitle(bookBean.getName());
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
        mSmartRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(mAdapter = new SheetListAdapter(null));
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_book_detail, mRecyclerView,
                false);
        mHeader = new MHeader(view);
        mAdapter.addHeaderView(view);
        mAdapter.addFooterView(LayoutInflater.from(mContext).inflate(R.layout.view_space_large, mRecyclerView,
                false));
        ViewUtil.cancelRecyclerViewAnim(mRecyclerView);

        mHeader.setData();
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
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void getBookListSuccess(BookBean.BookListResult response) {

    }

    @Override
    public void getSheetListSuccess(SheetBean.SheetListResult response) {
        if (response.getData() == null || response.getData().getData() == null)
            return;
        if (page == 0) {
            mAdapter.setNewData(response.getData().getData());
        } else {
            mAdapter.addData(response.getData().getData());
        }
        page++;
        mSmartRefreshLayout.setEnableLoadMore(response.getData().getData().size() >= Constants.PAGE_COUNT);
    }

    @Override
    public void getExerciseListSuccess(ExerciseBean.ExerciseListResult response) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    class MHeader extends AbstractFavoriteView {

        @BindView(R.id.header_book_iv)
        ImageView imageView;
        @BindView(R.id.header_book_title_tv)
        TextView titleTv;
        @BindView(R.id.header_book_author_tv)
        TextView authorTv;
        @BindView(R.id.header_book_desc_tv)
        TextView descTv;

        private View mView;

        MHeader(View view) {
            mView = view;
            ButterKnife.bind(this, mView);
        }

        public void setData() {
            ImgLoader.downloadBitmap(mContext, DataUtil.getImgUrl(bookBean.getCover()), DeviceUtils.dp2px(4f),
                    new ImgLoader.DownloadImgListener<Bitmap>() {
                        @Override
                        public void OnDownloadFinish(Bitmap resource) {
                            imageView.setImageBitmap(resource);
                            Palette.from(resource).generate(palette -> {
                                if (palette == null)
                                    return;
                                int dc = getResources().getColor(R.color.color_green);
                                mView.setBackgroundColor(palette.getVibrantColor(dc));
                            });
                        }

                        @Override
                        public void OnDownloadFailed() {

                        }
                    });
            titleTv.setText(bookBean.getName());
            authorTv.setText(bookBean.getAuthor());
            descTv.setText("简介:\n" + bookBean.getIntroduction());
        }

    }

}
