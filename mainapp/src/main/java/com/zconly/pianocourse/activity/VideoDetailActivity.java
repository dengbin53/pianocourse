package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.LiveBean;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.VideoListResult;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.presenter.FavoritePresenter;
import com.zconly.pianocourse.mvp.view.AbstractFavoriteView;
import com.zconly.pianocourse.mvp.view.CourseView;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ImgLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.video_comment_et)
    EditText commentEt;

    private VideoBean videoBean;
    private CourseBean courseBean;
    private int page;
    private MyAdapter mAdapter;
    private MHeader mHeader;
    private long replyId;

    public static void start(Context context, VideoBean bean, CourseBean courseBean) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_DATA, bean);
        intent.putExtra(ExtraConstants.EXTRA_DATA_COURSE, courseBean);
        context.startActivity(intent);
    }

    private void resetCommentEt(String hint) {
        commentEt.setText("");
        commentEt.setHint(hint);
    }

    private void getCommentList() {
        mPresenter.getCommentList(videoBean.getLesson_id(), 0, page);
    }

    private void getVideoList() {
        if (mHeader.getVideoCount() <= 0)
            mPresenter.getVideoList(videoBean.getLesson_id());
    }

    @SingleClick
    @OnClick({R.id.video_comment_done_tv})
    void sendClick(View view) {
        DeviceUtils.hideSoftInput(mContext, commentEt);
        String content = commentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            replyId = 0;
            resetCommentEt(getString(R.string.hint_comment));
            return;
        }
        mPresenter.addComment(videoBean.getId(), replyId, content);
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle("课程详情");
        videoBean = (VideoBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA);
        courseBean = (CourseBean) getIntent().getSerializableExtra(ExtraConstants.EXTRA_DATA_COURSE);
        if (videoBean == null) {
            finish();
            return false;
        }
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getCommentList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mHeader.resetCommentCount();
                initData();
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

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            CommentBean bean = (CommentBean) adapter.getItem(position);
            if (bean == null)
                return;
            resetCommentEt(bean.getUserBO() == null ? "" : "@" + bean.getUserBO().getNickname());
            replyId = bean.getId();
            DeviceUtils.showSoftInput(commentEt);
        });

        return true;
    }

    @Override
    protected void initData() {
        if (courseBean == null) {
            mPresenter.getCourseList(0, videoBean.getLesson_id() + "", null);
        } else {
            mHeader.setHeaderData();
        }
        getVideoList();
        getCommentList();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video_detail;
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
    public void getCourseListSuccess(CourseListResult response) {
        if (response.getData() == null || ArrayUtil.isEmpty(response.getData().getData()))
            return;
        courseBean = response.getData().getData().get(0);
        mHeader.setHeaderData();
    }

    @Override
    public void getVideoListSuccess(VideoListResult response) {
        mHeader.setVideoData(response.getData());
    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

    @Override
    public void getLiveDataSuccess(LiveBean response) {

    }

    @Override
    public void getCommentSuccess(CommentBean.CommentListResult response) {
        if (response.getData() == null)
            return;
        List<CommentBean> data = response.getData().getData();
        if (page == 0) {
            mAdapter.setNewData(data);
            mHeader.addCommentCount(response.getData().getTotal());
        } else {
            mAdapter.addData(data);
        }
        page++;
    }

    @Override
    public void addCommentSuccess(CommentBean.CommentResult response) {
        mAdapter.addData(0, response.getData());
        mHeader.addCommentCount(1);
        resetCommentEt(getString(R.string.hint_comment));
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
        if (mHeader != null)
            mHeader.onDestroy();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mSmartRefreshLayout.finishLoadMore();
        mSmartRefreshLayout.finishRefresh();
    }

    private static class MyAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> {

        MyAdapter(@Nullable List<CommentBean> data) {
            super(R.layout.item_list_comment, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, CommentBean item) {
            helper.setText(R.id.comment_content_tv, item.getContent());
            UserBean user = item.getUserBO();
            helper.setText(R.id.comment_name_tv, user == null ? "" : user.getNickname());
            helper.setText(R.id.comment_time_tv, DateUtils.getTime2M(item.getC_time()));
            ImgLoader.showAvatar(user == null ? null : DataUtil.getImgUrl(user.getAvatar()),
                    helper.getView(R.id.comment_avatar_iv));
        }
    }

    class MHeader extends AbstractFavoriteView {

        @BindView(R.id.header_video_bd_video)
        VideoPlayerView videoView;
        @BindView(R.id.header_video_title_tv)
        TextView titleTv;
        @BindView(R.id.header_video_teacher_iv)
        ImageView teacherAvatarIv;
        @BindView(R.id.header_video_teacher_tv)
        TextView teacherNameTv;
        @BindView(R.id.header_video_time_tv)
        TextView timeTv;
        @BindView(R.id.header_video_like_tv)
        TextView likeTv;
        @BindView(R.id.header_video_favorite_tv)
        TextView favoriteTv;
        @BindView(R.id.header_video_video_rv)
        RecyclerView courseRv;
        @BindView(R.id.header_video_evaluate_count_tv)
        TextView commentCountTv;
        @BindView(R.id.header_video_evaluate_sort_iv)
        ImageView commentSortIv;

        private int totalComment;
        private FavoritePresenter favoritePresenter;
        private BaseQuickAdapter<VideoBean, BaseViewHolder> videoAdapter;

        MHeader(View view) {
            ButterKnife.bind(this, view);
        }

        @Override
        public void favoriteSuccess(BaseBean response) {
            int favorited = videoBean.getFavorited() > 0 ? 0 : 1;
            videoBean.setFavorited(favorited);
            videoBean.setFavorite_count(favorited > 0 ? videoBean.getFavorite_count() + 1
                    : videoBean.getFavorite_count() - 1);
            setFavorite();
        }

        @Override
        public void likeSuccess(BaseBean response) {
            int liked = videoBean.getLiked() > 0 ? 0 : 1;
            videoBean.setLiked(liked);
            videoBean.setLike_count(liked > 0 ? videoBean.getLike_count() + 1 : videoBean.getLike_count() - 1);
            setLike();
        }

        @OnClick({R.id.header_video_like_tv, R.id.header_video_favorite_tv})
        void onClick(View view) {
            favoritePresenter = favoritePresenter == null ? new FavoritePresenter(this) : favoritePresenter;
            switch (view.getId()) {
                case R.id.header_video_favorite_tv:
                    favoritePresenter.favorite(0, videoBean.getId());
                    break;
                case R.id.header_video_like_tv:
                    favoritePresenter.like(0, videoBean.getId());
                    break;
                default:
                    break;
            }
        }

        void setHeaderData() {

            changeVideo();

            ImgLoader.showAvatar(DataUtil.getImgUrl(courseBean.getTeacher_avatar()), teacherAvatarIv);
            teacherNameTv.setText(courseBean.getTeacher());

            setLike();
            setFavorite();

            courseRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                    false));
            courseRv.setAdapter(videoAdapter = new BaseQuickAdapter<VideoBean, BaseViewHolder>(
                    R.layout.item_list_video_h, null) {
                @Override
                protected void convert(@NonNull BaseViewHolder helper, VideoBean item) {
                    ImgLoader.showImgRound(DataUtil.getImgUrl(item.getCover()), helper.getView(R.id.video_iv));
                }
            });
            videoAdapter.setOnItemClickListener((adapter, view, position) -> {
                VideoBean bean = videoAdapter.getItem(position);
                if (bean == null || bean.getId() == videoBean.getId())
                    return;
                videoBean = bean;
                changeVideo();
            });
        }

        void changeVideo() {
            titleTv.setText(videoBean.getTitle());
            videoView.setVideoCover(DataUtil.getImgUrl(videoBean.getCover()));
            timeTv.setText(DateUtils.getTime2M(videoBean.getC_time()));
            videoView.startPlay(Uri.parse(videoBean.getUrl()));
        }

        void addCommentCount(int total) {
            totalComment += total;
            commentCountTv.setText("评论 " + totalComment);
        }

        void resetCommentCount() {
            totalComment = 0;
        }

        void setVideoData(List<VideoBean> data) {
            videoAdapter.setNewData(data);
        }

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

        private void setFavorite() {
            favoriteTv.setSelected(videoBean.getFavorited() > 0);
            favoriteTv.setText(videoBean.getFavorite_count() + Constants.END_FAVORITE);
        }

        private void setLike() {
            likeTv.setSelected(videoBean.getLiked() > 0);
            likeTv.setText(videoBean.getLike_count() + Constants.END_LIKE);
        }

        int getVideoCount() {
            return videoAdapter == null ? 0 : videoAdapter.getItemCount();
        }
    }

}
