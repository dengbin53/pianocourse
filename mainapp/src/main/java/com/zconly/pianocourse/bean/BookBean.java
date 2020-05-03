package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: 书
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:17
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:17
 * @UpdateRemark: 更新说明
 */
public class BookBean extends BaseBean {

    private long id;
    private String name;
    private String author;
    private String publish;
    private String introduction;
    private String cover;
    private int sort;
    private String locked;
    private long c_time;
    private long m_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
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

    public static class BookListBean extends BaseBean {
        private List<BookBean> data;

        public List<BookBean> getData() {
            return data;
        }

        public void setData(List<BookBean> data) {
            this.data = data;
        }
    }

    public static class BookListResult extends BaseBean {
        private BookListBean data;

        public BookListBean getData() {
            return data;
        }

        public void setData(BookListBean data) {
            this.data = data;
        }
    }
}
