package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CourseListBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/31 16:47
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/31 16:47
 * @UpdateRemark: 更新说明
 */
public class CourseListResult extends BaseBean {

    private CourseListBean data;

    public CourseListBean getData() {
        return data;
    }

    public void setData(CourseListBean data) {
        this.data = data;
    }

}
