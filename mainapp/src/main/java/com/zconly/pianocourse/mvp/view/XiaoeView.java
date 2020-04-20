package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.XiaoeTokenBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/19 01:04
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/19 01:04
 * @UpdateRemark: 更新说明
 */
public interface XiaoeView extends MvpView {

    void getXiaoeTokenSuccess(XiaoeTokenBean.XiaoeTokenResult response);
}
