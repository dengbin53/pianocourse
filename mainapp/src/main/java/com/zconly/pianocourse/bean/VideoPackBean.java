package com.zconly.pianocourse.bean;

import com.zconly.pianocourse.constants.Constants;

import java.util.List;

/**
 * @Description: 视频包
 * @Author: dengbin
 * @CreateDate: 2020/4/2 10:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 10:54
 * @UpdateRemark: 更新说明
 */
public class VideoPackBean extends BaseBean {

    private long id;
    private long lesson_id;
    private String description;
    private String title;
    private int sort;
    private int video_count;
    private long c_time;
    private String cover_small;
    private String teacher;
    private String teacher_avatar;

    private boolean opened;
    private List<VideoBean> videoBeans;

    public List<VideoBean> getVideoBeans() {
        return videoBeans;
    }

    public void setVideoBeans(List<VideoBean> videoBeans) {
        this.videoBeans = videoBeans;
        for (VideoBean bean : this.videoBeans) {
            bean.setLvp_id(bean.getLvp_id() == 0 ? getId() : bean.getLvp_id());
        }
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public String getCover_small() {
        return cover_small;
    }

    public void setCover_small(String cover_small) {
        this.cover_small = cover_small;
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

    @Override
    public int getItemType() {
        return Constants.VIEW_TYPE_PACK;
    }

    public static class VideoPackResult extends BaseBean {

        private List<VideoPackBean> data;

        public List<VideoPackBean> getData() {
            return data;
        }

        public void setData(List<VideoPackBean> data) {
            this.data = data;
        }
    }
}
