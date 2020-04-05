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
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.base.ExtraConstants;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.LiveBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.presenter.FavoritePresenter;
import com.zconly.pianocourse.mvp.view.AbstractFavoriteView;
import com.zconly.pianocourse.mvp.view.CourseView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.ImgLoader;

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
    protected boolean initView() {
        mTitleView.setTitle("大师课目录");
        courseBean = (CourseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> getCourseData());
        mSmartRefreshLayout.setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(mAdapter = new VideoListAdapter(null, pos -> courseBean));
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_course_detail, mRecyclerView,
                false);
        mHeader = new MHeader(view);
        mAdapter.addHeaderView(view);

        return true;
    }

    private void getCourseData() {
        mPresenter.getCourseList(0, courseBean.getId() + "", null);
    }

    @Override
    protected void initData() {
        mHeader.setData();
        if (TextUtils.isEmpty(courseBean.getTitle()))
            getCourseData();
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
    public void getVideoListSuccess(VideoBean.VideoListResult response) {
        mAdapter.setNewData(response.getData());
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    @Override
    public void getLiveDataSuccess(LiveBean response) {

    }

    @Override
    public void getCommentSuccess(CommentBean.CommentListResult response) {

    }

    @Override
    public void addCommentSuccess(CommentBean.CommentResult response) {

    }

    class MHeader extends AbstractFavoriteView {

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
            ImgLoader.showImg(DataUtil.getImgUrl(courseBean.getCover()), imageView);
            titleTv.setText(courseBean.getTitle());
            teacherTv.setText(courseBean.getTeacher());
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
