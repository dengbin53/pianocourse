package com.zconly.pianocourse.bean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 16:42
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 16:42
 * @UpdateRemark: 更新说明
 */
public class LiveBean extends BaseBean {
    
    private String cover;
    private String url;

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
}
