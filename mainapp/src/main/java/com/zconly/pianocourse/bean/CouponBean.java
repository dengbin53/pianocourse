package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: 优惠券
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:17
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:17
 * @UpdateRemark: 更新说明
 */
public class CouponBean extends BaseBean {

    private long id;
    private long cash_coupon_id;
    private long lesson_id;
    private long lvp_id;
    private String description;
    private int denomination;
    private int status;
    private long effective;
    private long expire;
    private long c_time;
    private long m_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCash_coupon_id() {
        return cash_coupon_id;
    }

    public void setCash_coupon_id(long cash_coupon_id) {
        this.cash_coupon_id = cash_coupon_id;
    }

    public long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public long getLvp_id() {
        return lvp_id;
    }

    public void setLvp_id(long lvp_id) {
        this.lvp_id = lvp_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getEffective() {
        return effective;
    }

    public void setEffective(long effective) {
        this.effective = effective;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public long getM_time() {
        return m_time;
    }

    public void setM_time(long m_time) {
        this.m_time = m_time;
    }

    public static class CouponListBean extends BaseBean {
        private List<CouponBean> data;

        public List<CouponBean> getData() {
            return data;
        }

        public void setData(List<CouponBean> data) {
            this.data = data;
        }
    }

    public static class CouponListResult extends BaseBean {
        private CouponListBean data;

        public CouponListBean getData() {
            return data;
        }

        public void setData(CouponListBean data) {
            this.data = data;
        }
    }
}
