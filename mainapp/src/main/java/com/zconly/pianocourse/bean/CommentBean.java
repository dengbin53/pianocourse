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
public class CommentBean extends BaseBean {

    private long id;
    private long user_id;
    private long lesson_id;
    private long reply_id;
    private long c_time;
    private String content;
    private int thumbup_count;
    private int thumbuped;
    private UserBean userBO;

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

    public long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public long getReply_id() {
        return reply_id;
    }

    public void setReply_id(long reply_id) {
        this.reply_id = reply_id;
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

    public int getThumbup_count() {
        return thumbup_count;
    }

    public void setThumbup_count(int thumbup_count) {
        this.thumbup_count = thumbup_count;
    }

    public int getThumbuped() {
        return thumbuped;
    }

    public void setThumbuped(int thumbuped) {
        this.thumbuped = thumbuped;
    }

    public UserBean getUserBO() {
        return userBO;
    }

    public void setUserBO(UserBean userBO) {
        this.userBO = userBO;
    }

    public static class CommentListBean extends BaseBean {

        private List<CommentBean> data;

        public List<CommentBean> getData() {
            return data;
        }

        public void setData(List<CommentBean> data) {
            this.data = data;
        }
    }

    public static class CommentListResult extends BaseBean {
        private CommentListBean data;

        public CommentListBean getData() {
            return data;
        }

        public void setData(CommentListBean data) {
            this.data = data;
        }
    }

    public static class CommentResult extends BaseBean {
        private CommentBean data;

        public CommentBean getData() {
            return data;
        }

        public void setData(CommentBean data) {
            this.data = data;
        }
    }

}
