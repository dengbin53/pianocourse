package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.LiveBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.VideoListResult;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 20:26
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 20:26
 * @UpdateRemark: 更新说明
 */
public interface CourseView extends MvpView {

    void getCourseListSuccess(CourseListResult response);

    void getVideoListSuccess(VideoListResult response);

    void getBannerListSuccess(BannerBean.BannerListResult response);

    void getLiveDataSuccess(LiveBean response);

    void getEvaluateSuccess(EvaluateBean.EvaluateListResult response);
}
