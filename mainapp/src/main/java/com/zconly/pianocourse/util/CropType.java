package com.zconly.pianocourse.util;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/7 13:53
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/7 13:53
 * @UpdateRemark: 更新说明
 */
public enum CropType {
    ICON(1, 1, 512, 512),// 头像类型:比例1:1
    COVER(16, 10, 800, 500),// 直播封面类型:比例:16:10
    PAGEGB(9, 5, 1080, 600);//个人主页背景

    public int aspectX;
    public int aspectY;
    public int width;
    public int height;

    // 构造方法
    CropType(int aspectX, int aspectY, int width, int height) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.width = width;
        this.height = height;
    }
}
