package com.zconly.pianocourse.mvp;

import com.mvp.interceptor.ParamsInterceptor;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.util.DeviceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 添加统一header
 * @Author: dengbin
 * @CreateDate: 2020-01-03 18:29
 * @UpdateUser: dengbin
 * @UpdateDate: 2020-01-03 18:29
 * @UpdateRemark: 更新说明
 */
public class MHeaderInterceptor extends ParamsInterceptor {

    @Override
    protected Map<String, String> getHeader() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("platform", "1");
        headerMap.put("User-Agent", "msb-apk");
        headerMap.put("cversion", Constants.CLIENT_VERSION + "");
        headerMap.put("opertaion", Constants.SYSTEM_VERSION);
        headerMap.put("Timestamp", System.currentTimeMillis() + "");
        headerMap.put("network", Constants.NETWORK + "");
        headerMap.put("userid", Constants.USER_ID);
        headerMap.put("token", Constants.TOKEN);
        headerMap.put("screenwidth", DeviceUtils.getScreenWidth() + "");
        headerMap.put("screenheight", DeviceUtils.getScreenHeight() + "");
        headerMap.put("channel", Constants.UMENG_CHANNEL);
        return headerMap;
    }
}
