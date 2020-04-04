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
    private int currentPage;
    private int pageSize;
    private int total;
    private int totalPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

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
