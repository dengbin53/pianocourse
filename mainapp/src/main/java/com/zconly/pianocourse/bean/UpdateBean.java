package com.zconly.pianocourse.bean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 23:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 23:54
 * @UpdateRemark: 更新说明
 */
public class UpdateBean extends BaseBean {

    private int ver;
    private String title;
    private String desc;
    private String url;
    private boolean must;

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    public static class UpdateResult extends BaseBean {
        private UpdateBean android;

        public UpdateBean getAndroid() {
            return android;
        }

        public void setAndroid(UpdateBean android) {
            this.android = android;
        }
    }

}
