package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: 交易记录
 * @Author: dengbin
 * @CreateDate: 2020/6/21 23:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/6/21 23:54
 * @UpdateRemark: 更新说明
 */
public class ChangeRecordBean extends BaseBean {

    private long id;
    private int type;
    private String remark;
    private int amount;
    private CourseBean lesson;
    private VideoPackBean lvp;
    private long c_time;

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CourseBean getLesson() {
        return lesson;
    }

    public void setLesson(CourseBean lesson) {
        this.lesson = lesson;
    }

    public VideoPackBean getLvp() {
        return lvp;
    }

    public void setLvp(VideoPackBean lvp) {
        this.lvp = lvp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public static class ChangeRecordListResult extends BaseBean {

        private ChangeRecordListBean data;

        public ChangeRecordListBean getData() {
            return data;
        }

        public void setData(ChangeRecordListBean data) {
            this.data = data;
        }
    }

    public static class ChangeRecordListBean extends BaseBean {
        private List<ChangeRecordBean> data;

        public List<ChangeRecordBean> getData() {
            return data;
        }

        public void setData(List<ChangeRecordBean> data) {
            this.data = data;
        }
    }
}
