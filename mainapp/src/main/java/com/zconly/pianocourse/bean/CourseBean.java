package com.zconly.pianocourse.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:50
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:50
 * @UpdateRemark: 更新说明
 */
public class CourseBean extends BaseBean implements MultiItemEntity {

    public static final int TITLE = 1;
    public static final int ITEM = 0;

    private int type;

    private String title;
    private String img;
    private String videoUrl;

    @Override
    public int getItemType() {
        return getType();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
