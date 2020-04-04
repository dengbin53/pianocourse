package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 23:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 23:54
 * @UpdateRemark: 更新说明
 */
public class NoticeBean extends BaseBean {

    private long id;
    private long user_id;
    private long c_time;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class NoticeResult extends BaseBean {
        private NoticeListBean data;

        public NoticeListBean getData() {
            return data;
        }

        public void setData(NoticeListBean data) {
            this.data = data;
        }
    }

    public static class NoticeListBean extends BaseBean {

        private List<NoticeBean> data;

        public List<NoticeBean> getData() {
            return data;
        }

        public void setData(List<NoticeBean> data) {
            this.data = data;
        }
    }
}
