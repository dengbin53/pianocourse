package com.zconly.pianocourse.bean.result;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.TokenBean;

/**
 * Created by dengbin on 17/4/27.
 */
public class TokenResult extends BaseBean {

    private TokenBean data;

    public TokenBean getData() {
        return data;
    }

    public void setData(TokenBean data) {
        this.data = data;
    }
}
