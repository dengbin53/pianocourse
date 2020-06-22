package com.zconly.pianocourse.bean;

/**
 * @Description: 作用描述
 * @Author: dengbin
 * @CreateDate: 2020/6/21 23:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/6/21 23:54
 * @UpdateRemark: 更新说明
 */
public class BalanceBean extends BaseBean {

    private long id;
    private int balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public static class BalanceResult extends BaseBean {

        private BalanceBean data;

        public BalanceBean getData() {
            return data;
        }

        public void setData(BalanceBean data) {
            this.data = data;
        }
    }

}
