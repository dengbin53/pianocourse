package com.zconly.pianocourse.mvp.view;

import com.mvp.base.MvpView;
import com.zconly.pianocourse.bean.FileBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/25 12:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/25 12:03
 * @UpdateRemark: 更新说明
 */
public interface UploadView extends MvpView {

    void onProgress(int progress);

    void uploadSuccess(FileBean response);
}
