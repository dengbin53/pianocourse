package com.mvp.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2019-12-25 18:52
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-25 18:52
 * @UpdateRemark: 更新说明
 */
public abstract class TestActivity<P extends MvpPresenter> extends MvpActivity<P, View> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent();
    }

}
