package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.VideoBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 11:00
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 11:00
 * @UpdateRemark: 更新说明
 */
public class VideoListResult extends BaseBean {
    private List<VideoBean> data;

    public List<VideoBean> getData() {
        return data;
    }

    public void setData(List<VideoBean> data) {
        this.data = data;
    }
}
