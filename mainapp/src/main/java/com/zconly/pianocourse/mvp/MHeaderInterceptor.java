package com.zconly.pianocourse.mvp;

import com.mvp.interceptor.ParamsInterceptor;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.SysConfigTool;

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
        headerMap.put("Authorization", "Bearer " + SysConfigTool.getToken());
        headerMap.put("platform", "1");
        headerMap.put("User-Agent", "piano-course-android");
        headerMap.put("cversion", Constants.CLIENT_VERSION + "");
        headerMap.put("Timestamp", System.currentTimeMillis() + "");
        headerMap.put("imei", Constants.IMEI);
        headerMap.put("screenwidth", DeviceUtils.getScreenWidth() + "");
        headerMap.put("screenheight", DeviceUtils.getScreenHeight() + "");
        headerMap.put("channel", Constants.UMENG_CHANNEL);
        return headerMap;
    }
}
