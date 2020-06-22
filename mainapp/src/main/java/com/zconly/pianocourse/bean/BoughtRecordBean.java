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
public class BoughtRecordBean extends BaseBean {

    private long id;
    private int type;
    private String remark;
    private int amount;
    private CourseBean lesson;
    private VideoPackBean lvp;

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

    public static class BoughtRecordListResult extends BaseBean {

        private BoughtRecordListBean data;

        public BoughtRecordListBean getData() {
            return data;
        }

        public void setData(BoughtRecordListBean data) {
            this.data = data;
        }
    }

    public static class BoughtRecordListBean extends BaseBean {
        private List<BoughtRecordBean> data;

        public List<BoughtRecordBean> getData() {
            return data;
        }

        public void setData(List<BoughtRecordBean> data) {
            this.data = data;
        }
    }
}
