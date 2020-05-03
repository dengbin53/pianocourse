package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.BookGridAdapter;
import com.zconly.pianocourse.adapter.decoration.GridDecoration;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.constants.BookLevel;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.mvp.presenter.QinfangPresenter;
import com.zconly.pianocourse.mvp.view.QinfangView;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;

/**
 * @Description: 书列表
 * @Author: dengbin
 * @CreateDate: 2020/5/3 13:59
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/3 13:59
 * @UpdateRemark: 更新说明
 */
public class BookListActivity extends BaseMvpActivity<QinfangPresenter> implements QinfangView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BookLevel level;
    private BookGridAdapter mAdapter;

    public static void start(Context context, BookLevel level) {
        Intent intent = new Intent(context, BookListActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, level);
        context.startActivity(intent);
    }

    private void requestData() {
        mPresenter.getBookList(0, 0, null, null, null, 0);
    }

    @Override
    protected boolean initView() {
        level = (BookLevel) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);

        mTitleView.setTitle(level.getLevelStr());

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> requestData());
        mSmartRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRecyclerView.addItemDecoration(new GridDecoration(DeviceUtils.dp2px(8f)));
        mRecyclerView.setAdapter(mAdapter = new BookGridAdapter(null));
        mAdapter.addFooterView(LayoutInflater.from(mContext).inflate(R.layout.view_space_large, mRecyclerView,
                false));
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
        if (response.getData() == null)
            return;
        mAdapter.setNewData(response.getData().getData());
    }

    @Override
    public void getSheetListSuccess(SheetBean.SheetListResult response) {

    }

    @Override
    public void getExerciseListSuccess(ExerciseBean.ExerciseListResult response) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

}
