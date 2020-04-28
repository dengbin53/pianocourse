package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.VideoListAdapter;
import com.zconly.pianocourse.base.BaseCourseActivity;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.base.ExtraConstants;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.bean.VideoPackBean;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.presenter.FavoritePresenter;
import com.zconly.pianocourse.mvp.view.AbstractFavoriteView;
import com.zconly.pianocourse.mvp.view.CourseDetailView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 课程详情
 * @Author: dengbin
 * @CreateDate: 2020/3/31 16:59
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/31 16:59
 * @UpdateRemark: 更新说明
 */
public class CourseDetailActivity extends BaseCourseActivity implements CourseDetailView {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CourseBean courseBean;
    private VideoListAdapter mAdapter;
    private MHeader mHeader;
    private int position;

    public static void start(Context context, CourseBean bean) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, bean);
        context.startActivity(intent);
    }

    private void getCourseData() {
        mPresenter.getCourseList(0, courseBean.getId() + "", null);
    }

    @Override
    protected boolean initView() {
        courseBean = (CourseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        if (courseBean == null) {
            finish();
            return false;
        }
        mTitleView.setTitle(courseBean.getTitle());
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> getCourseData());
        mSmartRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(mAdapter = new VideoListAdapter<>(null));
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_course_detail, mRecyclerView,
                false);
        mHeader = new MHeader(view);
        mAdapter.addHeaderView(view);
        mAdapter.addFooterView(LayoutInflater.from(mContext).inflate(R.layout.footer_space, mRecyclerView,
                false));
        mAdapter.setOnItemChildClickListener((adapter, view1, pos) -> {
            position = pos;
            Object obj = mAdapter.getItem(position);
            if (!(obj instanceof VideoPackBean))
                return;
            if (((VideoPackBean) obj).isOpened()) {
                mAdapter.closePack(position);
            } else if (!mAdapter.openPack(position, ((VideoPackBean) obj).getVideoBeans())) {
                mPresenter.getVideopackVideo(((VideoPackBean) obj).getId());
            }
        });
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Object b = mAdapter.getItem(position);
            if (!(b instanceof VideoBean))
                return;
            VideoDetailActivity.start(mContext, (VideoBean) b, courseBean);
        });
        ViewUtil.cancelRecyclerViewAnim(mRecyclerView);
        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void initData() {
        mHeader.setData();
        if (TextUtils.isEmpty(courseBean.getTitle()))
            getCourseData();
        mPresenter.getCourseVideoPack(courseBean.getId());
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
        return false;
    }

    @Override
    public void getCourseListSuccess(CourseBean.CourseListResult response) {
        if (response.getData() == null || ArrayUtil.isEmpty(response.getData().getData()))
            return;
        courseBean = response.getData().getData().get(0);
        initData();
    }

    @Override
    public void getCourseVideoPack(VideoPackBean.VideoPackResult response) {
        mAdapter.setNewData(response.getData());
    }

    @Override
    public void getVideoListSuccess(VideoBean.VideoListResult response) {
        Object obj = mAdapter.getItem(position);
        if (!(obj instanceof VideoPackBean))
            return;
        mAdapter.openPack(position, response.getData());
    }

    class MHeader extends AbstractFavoriteView {

        @BindView(R.id.header_course_iv)
        ImageView imageView;
        @BindView(R.id.header_course_title_tv)
        TextView titleTv;
        @BindView(R.id.header_course_bg_iv)
        ImageView bgIv;
        @BindView(R.id.header_course_desc_tv)
        TextView descTv;
        @BindView(R.id.header_course_like_tv)
        TextView likeTv;
        @BindView(R.id.header_course_favorite_tv)
        TextView favoriteTv;

        private FavoritePresenter favoritePresenter;

        MHeader(View view) {
            ButterKnife.bind(this, view);
        }

        @Override
        public void favoriteSuccess(BaseBean response) {
            int favorited = courseBean.getFavorited() > 0 ? 0 : 1;
            courseBean.setFavorited(favorited);
            courseBean.setFavorite_count(favorited > 0 ? courseBean.getFavorite_count() + 1
                    : courseBean.getFavorite_count() - 1);
            setFavorite();
        }

        @Override
        public void likeSuccess(BaseBean response) {
            int liked = courseBean.getLiked() > 0 ? 0 : 1;
            courseBean.setLiked(liked);
            courseBean.setLike_count(liked > 0 ? courseBean.getLike_count() + 1 : courseBean.getLike_count() - 1);
            setLike();
        }

        @OnClick({R.id.header_course_like_tv, R.id.header_course_favorite_tv})
        void onClick(View view) {
            if (favoritePresenter == null)
                favoritePresenter = new FavoritePresenter(this);
            switch (view.getId()) {
                case R.id.header_course_like_tv:
                    favoritePresenter.like(courseBean.getId(), 0);
                    break;
                case R.id.header_course_favorite_tv:
                    favoritePresenter.favorite(courseBean.getId(), 0);
                    break;
                default:
                    break;
            }
        }

        public void setData() {
            ImgLoader.showImg(DataUtil.getImgUrl(courseBean.getCover()), bgIv);
            ImgLoader.showImgRound(DataUtil.getImgUrl(courseBean.getCover_small()), imageView, DeviceUtils.dp2px(4f),
                    DeviceUtils.dp2px(128f));
            titleTv.setText(courseBean.getTitle());
            // teacherTv.setText(courseBean.getTeacher());
            descTv.setText(courseBean.getDescription());

            setLike();
            setFavorite();
        }

        private void setFavorite() {
            favoriteTv.setSelected(courseBean.getFavorited() > 0);
            favoriteTv.setText(courseBean.getFavorite_count() + Constants.END_FAVORITE);
        }

        private void setLike() {
            likeTv.setSelected(courseBean.getLiked() > 0);
            likeTv.setText(courseBean.getLike_count() + Constants.END_LIKE);
        }

    }

}
