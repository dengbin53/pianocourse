package com.zconly.pianocourse.bean;

import java.util.List;

/**
 * @Description: 邀请码
 * @Author: dengbin
 * @CreateDate: 2020/5/7 21:18
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/7 21:18
 * @UpdateRemark: 更新说明
 */
public class InvitationBean extends BaseBean {

    private boolean enable;
    private List<String> codes;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

}
