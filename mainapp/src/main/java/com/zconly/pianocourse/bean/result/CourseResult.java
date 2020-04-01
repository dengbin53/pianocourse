package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CourseBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/31 16:47
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/31 16:47
 * @UpdateRemark: 更新说明
 */
public class CourseResult extends BaseBean {

    private CourseBean data;

    public CourseBean getData() {
        return data;
    }

    public void setData(CourseBean data) {
        this.data = data;
    }
}
