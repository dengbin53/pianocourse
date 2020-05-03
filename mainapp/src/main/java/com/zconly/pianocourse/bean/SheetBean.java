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
public class SheetBean extends BaseBean {

    public static final int ITEM = 0;

    private long id;
    private long book_id;
    private String name;
    private String author;
    private String publish;
    private String introduction;
    private String cover;
    private int sort;
    private String locked;
    private long c_time;
    private long m_time;
    private String desc_name;
    private String desc_author;
    private String style;
    private String number;
    private int level;
    private int bpm1;
    private int bpm2;
    private int bpm3;
    private long total_time1;
    private long total_time2;
    private long total_time3;
    private int beat;
    private int countdown;
    private int parts;
    private int width_scale;
    private String sheet_mxl;
    private String sheet_pic;
    private String sheet_head_pic;
    private int status;
    private long c_user_id;
    private int favorited;
    private int view_count;

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

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public String getDesc_name() {
        return desc_name;
    }

    public void setDesc_name(String desc_name) {
        this.desc_name = desc_name;
    }

    public String getDesc_author() {
        return desc_author;
    }

    public void setDesc_author(String desc_author) {
        this.desc_author = desc_author;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBpm1() {
        return bpm1;
    }

    public void setBpm1(int bpm1) {
        this.bpm1 = bpm1;
    }

    public int getBpm2() {
        return bpm2;
    }

    public void setBpm2(int bpm2) {
        this.bpm2 = bpm2;
    }

    public int getBpm3() {
        return bpm3;
    }

    public void setBpm3(int bpm3) {
        this.bpm3 = bpm3;
    }

    public long getTotal_time1() {
        return total_time1;
    }

    public void setTotal_time1(long total_time1) {
        this.total_time1 = total_time1;
    }

    public long getTotal_time2() {
        return total_time2;
    }

    public void setTotal_time2(long total_time2) {
        this.total_time2 = total_time2;
    }

    public long getTotal_time3() {
        return total_time3;
    }

    public void setTotal_time3(long total_time3) {
        this.total_time3 = total_time3;
    }

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getParts() {
        return parts;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }

    public int getWidth_scale() {
        return width_scale;
    }

    public void setWidth_scale(int width_scale) {
        this.width_scale = width_scale;
    }

    public String getSheet_mxl() {
        return sheet_mxl;
    }

    public void setSheet_mxl(String sheet_mxl) {
        this.sheet_mxl = sheet_mxl;
    }

    public String getSheet_pic() {
        return sheet_pic;
    }

    public void setSheet_pic(String sheet_pic) {
        this.sheet_pic = sheet_pic;
    }

    public String getSheet_head_pic() {
        return sheet_head_pic;
    }

    public void setSheet_head_pic(String sheet_head_pic) {
        this.sheet_head_pic = sheet_head_pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getC_user_id() {
        return c_user_id;
    }

    public void setC_user_id(long c_user_id) {
        this.c_user_id = c_user_id;
    }

    public int getFavorited() {
        return favorited;
    }

    public void setFavorited(int favorited) {
        this.favorited = favorited;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public static class SheetListBean extends BaseBean {
        private List<SheetBean> data;

        public List<SheetBean> getData() {
            return data;
        }

        public void setData(List<SheetBean> data) {
            this.data = data;
        }
    }

    public static class SheetListResult extends BaseBean {
        private SheetListBean data;

        public SheetListBean getData() {
            return data;
        }

        public void setData(SheetListBean data) {
            this.data = data;
        }
    }
}
