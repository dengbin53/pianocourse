package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.HomePageBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.bean.VideoPackBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 20:26
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 20:26
 * @UpdateRemark: 更新说明
 */
public interface CourseDetailView extends CourseView {

    void getCourseVideoPack(VideoPackBean.VideoPackResult response);
}
