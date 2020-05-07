package com.zconly.pianocourse.bean;


import java.util.List;

/**
 * @Description: 课程对象
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:50
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:50
 * @UpdateRemark: 更新说明
 */
public class EvaluateBean extends BaseBean {

    private long id;
    private long exercise_id;
    private long user_id;
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
    private long c_time;
    private long m_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(long exercise_id) {
        this.exercise_id = exercise_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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

//    public static class AnnotationBean extends BaseBean {
//        private String head;
//        private String sheet;
//        private String comment;
//
//        public String getHead() {
//            return head;
//        }
//
//        public void setHead(String head) {
//            this.head = head;
//        }
//
//        public String getSheet() {
//            return sheet;
//        }
//
//        public void setSheet(String sheet) {
//            this.sheet = sheet;
//        }
//
//        public String getComment() {
//            return comment;
//        }
//
//        public void setComment(String comment) {
//            this.comment = comment;
//        }
//    }

    public static class EvaluateListBean extends BaseBean {

        private List<EvaluateBean> data;

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
