package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 10:19
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 10:19
 * @UpdateRemark: 更新说明
 */
public class BannerBean extends BaseBean {

    private int id;
    private int type;
    private String title;
    private int lesson_id;
    private int status;
    private int sort;
    private long c_time;
    private long m_time;
    private String cover;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class BannerListResult extends BaseBean {
        private List<BannerBean> data;

        public List<BannerBean> getData() {
            return data;
        }

        public void setData(List<BannerBean> data) {
            this.data = data;
        }
    }
}
