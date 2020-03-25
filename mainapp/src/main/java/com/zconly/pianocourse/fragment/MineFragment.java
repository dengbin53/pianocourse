package com.zconly.pianocourse.fragment;

import android.view.View;

import com.mvp.base.MvpPresenter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.LoginActivity;
import com.zconly.pianocourse.base.BaseMvpFragment;

import butterknife.OnClick;

/**
 * @Description: 首页
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class MineFragment extends BaseMvpFragment {

    @OnClick({R.id.mine_tv})
    void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_tv:
                LoginActivity.start(mContext);
                break;
        }
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MvpPresenter getPresenter() {
        return null;
    }
}
