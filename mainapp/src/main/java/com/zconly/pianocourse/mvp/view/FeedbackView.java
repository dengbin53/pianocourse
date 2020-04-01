package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.BaseBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 19:36
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 19:36
 * @UpdateRemark: 更新说明
 */
public interface FeedbackView extends MvpView {
    void feedbackSuccess(BaseBean response);
}
