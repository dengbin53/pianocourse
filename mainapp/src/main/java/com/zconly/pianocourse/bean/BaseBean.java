package com.zconly.pianocourse.bean;

import com.mvp.base.MvpModel;
import com.zconly.pianocourse.base.ErrorCode;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 20:13
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 20:13
 * @UpdateRemark: 更新说明
 */
public class BaseBean extends MvpModel {

    private String msg;
    private int code;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean isSuccess() {
        return getCode() == ErrorCode.SUCCESS;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}
