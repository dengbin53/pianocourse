package com.zconly.pianocourse.mvp.view;

import com.mvp.exception.ApiException;

import okhttp3.ResponseBody;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/4/3 17:32
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 17:32
 * @UpdateRemark: 更新说明
 */
public abstract class AbstractDownloadView implements DownloadView {
    @Override
    public void loading(String msg) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onError(ApiException ae) {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void downloadSuccess() {

    }

    @Override
    public ResponseBody download(ResponseBody responseBody) {
        return null;
    }
}
