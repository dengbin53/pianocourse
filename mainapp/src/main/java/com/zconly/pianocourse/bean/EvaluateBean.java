package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 18:35
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 18:35
 * @UpdateRemark: 更新说明
 */
public class EvaluateBean extends BaseBean {

    private int exercise_id;
    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;
    private int score6;
    private String comment;
    private String voice;
    private String mark;
    private int duration;
    private int status;
    private String annotation;
    private int id;
    private long c_time;
    private long m_time;
    private long user_id;

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getScore3() {
        return score3;
    }

    public void setScore3(int score3) {
        this.score3 = score3;
    }

    public int getScore4() {
        return score4;
    }

    public void setScore4(int score4) {
        this.score4 = score4;
    }

    public int getScore5() {
        return score5;
    }

    public void setScore5(int score5) {
        this.score5 = score5;
    }

    public int getScore6() {
        return score6;
    }

    public void setScore6(int score6) {
        this.score6 = score6;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public static class EvaluateListBean extends BaseBean {

        private int total;
        private int pageSize;
        private int currentPage;
        private int totalPage;
        private List<EvaluateBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<EvaluateBean> getData() {
            return data;
        }

        public void setData(List<EvaluateBean> data) {
            this.data = data;
        }
    }

    public static class EvaluateListResult extends BaseBean {
        private EvaluateListBean data;

        public EvaluateListBean getData() {
            return data;
        }

        public void setData(EvaluateListBean data) {
            this.data = data;
        }
    }

}
