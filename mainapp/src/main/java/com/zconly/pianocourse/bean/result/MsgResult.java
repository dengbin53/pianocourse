package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.MsgBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/29 23:08
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/29 23:08
 * @UpdateRemark: 更新说明
 */
public class MsgResult extends BaseBean {
    private List<MsgBean> data;

    public List<MsgBean> getData() {
        return data;
    }

    public void setData(List<MsgBean> data) {
        this.data = data;
    }
}
