package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/2 11:39
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/2 11:39
 * @UpdateRemark: 更新说明
 */
public class CourseListBean extends BaseBean {

    private List<CourseBean> data;

    public List<CourseBean> getData() {
        return data;
    }

    public void setData(List<CourseBean> data) {
        this.data = data;
    }
    
}
