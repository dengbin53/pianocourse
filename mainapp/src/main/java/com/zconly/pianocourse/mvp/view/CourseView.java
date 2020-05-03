package com.zconly.pianocourse.mvp.view;

import com.zconly.pianocourse.bean.CommentBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.HomePageBean;
import com.zconly.pianocourse.bean.VideoBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 20:26
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 20:26
 * @UpdateRemark: 更新说明
 */
public interface CourseView extends BaseView {

    void getCourseListSuccess(CourseBean.CourseListResult response);

    void getVideoListSuccess(VideoBean.VideoListResult response);

    void getCommentSuccess(CommentBean.CommentListResult response);

    void addCommentSuccess(CommentBean.CommentResult response);

    void getHomePageSuccess(HomePageBean.HomePageResult response);

}
