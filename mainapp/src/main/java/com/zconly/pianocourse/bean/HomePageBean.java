package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 16:42
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 16:42
 * @UpdateRemark: 更新说明
 */
public class HomePageBean extends BaseBean {

    private RecommendBean live;
    private List<RecommendBean> recommend;

    public RecommendBean getLive() {
        return live;
    }

    public void setLive(RecommendBean live) {
        this.live = live;
    }

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public static class HomePageResult extends BaseBean {
        private HomePageBean jiangtang;
        private HomePageBean tuijian;

        public HomePageBean getJiangtang() {
            return jiangtang;
        }

        public void setJiangtang(HomePageBean jiangtang) {
            this.jiangtang = jiangtang;
        }

        public HomePageBean getTuijian() {
            return tuijian;
        }

        public void setTuijian(HomePageBean tuijian) {
            this.tuijian = tuijian;
        }
    }

    public static class RecommendBean extends BaseBean {

        public static final int TYPE_RECOMMEND_URL = 0;
        public static final int TYPE_RECOMMEND_COURSE = 1;

        private int type; // 推荐类型，0-URL(支持任意URL，含小鹅通直播)，1-课程(课程ID必须存在于系统内)
        private String url;
        private CourseBean lesson;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public CourseBean getLesson() {
            return lesson;
        }

        public void setLesson(CourseBean lesson) {
            this.lesson = lesson;
        }
    }

}
