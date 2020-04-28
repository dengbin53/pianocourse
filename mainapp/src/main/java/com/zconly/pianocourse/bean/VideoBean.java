package com.zconly.pianocourse.bean;

import androidx.annotation.Nullable;

import com.zconly.pianocourse.base.Constants;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 10:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 10:54
 * @UpdateRemark: 更新说明
 */
public class VideoBean extends BaseBean {

    private long id;
    private long lesson_id;
    private String title;
    private int status;
    private int sort;
    private long c_time;
    private long m_time;
    private String description;
    private String url;
    private String cover;
    private String cover_small;
    private int favorite_count;
    private int like_count;
    private int view_count;
    private int favorited;
    private int liked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public long getM_time() {
        return m_time;
    }

    public void setM_time(long m_time) {
        this.m_time = m_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public int getItemType() {
        return Constants.VIEW_TYPE_VIDEO;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof VideoBean && ((VideoBean) obj).getId() == getId();
    }

    public static class VideoListResult extends BaseBean {
        private List<VideoBean> data;

        public List<VideoBean> getData() {
            return data;
        }

        public void setData(List<VideoBean> data) {
            this.data = data;
        }
    }
}
