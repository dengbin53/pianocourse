package com.zconly.pianocourse.mvp.view;

import okhttp3.ResponseBody;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/5/5 03:17
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/5 03:17
 * @UpdateRemark: 更新说明
 */
public interface DownloadView {
    void downloadSuccess();

    ResponseBody download(ResponseBody responseBody);
}
