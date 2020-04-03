package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/3 15:12
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 15:12
 * @UpdateRemark: 更新说明
 */
public class FavoriteBean extends BaseBean {

    private CourseBean lesson;
    private VideoBean lessonVideo;

    public CourseBean getLesson() {
        return lesson;
    }

    public void setLesson(CourseBean lesson) {
        this.lesson = lesson;
    }

    public VideoBean getLessonVideo() {
        return lessonVideo;
    }

    public void setLessonVideo(VideoBean lessonVideo) {
        this.lessonVideo = lessonVideo;
    }

    public static class FavoriteListBean extends BaseBean {
        private List<FavoriteBean> data;

        public List<FavoriteBean> getData() {
            return data;
        }

        public void setData(List<FavoriteBean> data) {
            this.data = data;
        }
    }

    public static class FavoriteListResult extends BaseBean {

        private FavoriteListBean data;

        public FavoriteListBean getData() {
            return data;
        }

        public void setData(FavoriteListBean data) {
            this.data = data;
        }
    }
}
