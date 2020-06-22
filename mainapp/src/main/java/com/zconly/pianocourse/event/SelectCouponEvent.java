package com.zconly.pianocourse.event;

import com.zconly.pianocourse.bean.CouponBean;

/**
 * @Description: 选择优惠券
 * @Author: dengbin
 * @CreateDate: 2020/3/24 20:55
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/24 20:55
 * @UpdateRemark: 更新说明
 */
public class SelectCouponEvent {
    private CouponBean couponBean;

    public SelectCouponEvent(CouponBean couponBean) {
        this.couponBean = couponBean;
    }

    public CouponBean getCouponBean() {
        return couponBean;
    }

    public void setCouponBean(CouponBean couponBean) {
        this.couponBean = couponBean;
    }
}
