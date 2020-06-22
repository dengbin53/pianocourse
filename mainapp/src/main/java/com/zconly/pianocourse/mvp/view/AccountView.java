package com.zconly.pianocourse.mvp.view;

import com.zconly.pianocourse.bean.BalanceBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.BoughtRecordBean;
import com.zconly.pianocourse.bean.ChangeRecordBean;

/**
 * @Description:
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:24
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:24
 * @UpdateRemark: 更新说明
 */
public interface AccountView extends BaseView {

    void balanceSuccess(BalanceBean.BalanceResult response);

    void changeRecordListSuccess(ChangeRecordBean.ChangeRecordListResult response);

    void boughtRecordListSuccess(BoughtRecordBean.BoughtRecordListResult response);

    void buySuccess(BaseBean response);
}
