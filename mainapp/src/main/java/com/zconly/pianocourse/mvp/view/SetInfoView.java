package com.zconly.pianocourse.mvp.view;

import com.zconly.pianocourse.bean.UserDataBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 22:00
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 22:00
 * @UpdateRemark: 更新说明
 */
public interface SetInfoView extends UploadView, UserView {

    void completionSuccess(UserDataBean.SetInfoResult response);
}
