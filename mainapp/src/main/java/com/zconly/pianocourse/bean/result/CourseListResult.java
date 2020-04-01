package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CourseBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/31 16:47
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/31 16:47
 * @UpdateRemark: 更新说明
 */
public class CourseListResult extends BaseBean {
    private List<CourseBean> data;

    public List<CourseBean> getData() {
        return data;
    }

    public void setData(List<CourseBean> data) {
        this.data = data;
    }
}
