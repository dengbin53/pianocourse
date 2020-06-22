package com.zconly.pianocourse.mvp.view;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CouponBean;

/**
 * @Description:
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:24
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:24
 * @UpdateRemark: 更新说明
 */
public interface CouponView extends BaseView {

    void getCouponListSuccess(CouponBean.CouponListResult result);

    void obtainCouponSuccess(BaseBean response);
}
