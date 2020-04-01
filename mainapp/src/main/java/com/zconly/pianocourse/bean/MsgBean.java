package com.zconly.pianocourse.bean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/29 23:04
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/29 23:04
 * @UpdateRemark: 更新说明
 */
public class MsgBean extends BaseBean {
    private String title;
    private String dis;
    private String url;
    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
