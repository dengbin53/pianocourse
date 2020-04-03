package com.zconly.pianocourse.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:50
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:50
 * @UpdateRemark: 更新说明
 */
public class CourseBean extends BaseBean implements MultiItemEntity {

    public static final int TITLE = 1;
    public static final int ITEM = 0;
    public static final int LIVE = 22;

    private long id;
    private String teacher;
    private String teacher_avatar;
    private String title;
    private String cover;
    private String cover_small;
    private String description;
    private int video_count;
    private int favorite_count;
    private int like_count;
    private int view_count;
    private int comment_count;
    private int category;
    private int sort;
    private long c_time;
    private int favorited;
    private int liked;

    private int viewType;
    private String url;

    @Override
    public int getItemType() {
        return getViewType();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacher_avatar() {
        return teacher_avatar;
    }

    public void setTeacher_avatar(String teacher_avatar) {
        this.teacher_avatar = teacher_avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover_small() {
        return cover_small;
    }

    public void setCover_small(String cover_small) {
        this.cover_small = cover_small;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public int getFavorited() {
        return favorited;
    }

    public void setFavorited(int favorited) {
        this.favorited = favorited;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
