package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.result.MsgResult;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/29 23:07
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/29 23:07
 * @UpdateRemark: 更新说明
 */
public interface MsgView extends MvpView {
    void getMsgSuccess(MsgResult result);
}
