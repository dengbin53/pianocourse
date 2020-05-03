package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: 单曲
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:17
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:17
 * @UpdateRemark: 更新说明
 */
public class ExerciseBean extends BaseBean {

    private long id;
    private int bpm;
    private int staff;
    private int mode;
    private long c_time;
    private String path;
    private int duration;
    private int evaluate_num;
    private UserBean user;
    private SheetBean sheet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEvaluate_num() {
        return evaluate_num;
    }

    public void setEvaluate_num(int evaluate_num) {
        this.evaluate_num = evaluate_num;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public SheetBean getSheet() {
        return sheet;
    }

    public void setSheet(SheetBean sheet) {
        this.sheet = sheet;
    }

    public static class ExerciseListBean extends BaseBean {
        private List<ExerciseBean> data;

        public List<ExerciseBean> getData() {
            return data;
        }

        public void setData(List<ExerciseBean> data) {
            this.data = data;
        }
    }

    public static class ExerciseListResult extends BaseBean {
        private ExerciseListBean data;

        public ExerciseListBean getData() {
            return data;
        }

        public void setData(ExerciseListBean data) {
            this.data = data;
        }
    }
    
}
