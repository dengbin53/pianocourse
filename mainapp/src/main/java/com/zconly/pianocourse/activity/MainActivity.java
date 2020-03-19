package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.mvp.base.MvpPresenter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.fragment.HomeFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @Description: 应用主页
 * @Author: dengbin
 * @CreateDate: 2020/3/18 20:35
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 20:35
 * @UpdateRemark: 更新说明
 */
public class MainActivity extends BaseMvpActivity {

    @BindView(R.id.main_tl)
    TabLayout tabLayout;
    @BindView(R.id.main_content_fl)
    FrameLayout contentFl;

    private HomeFragment homeFragment;
    private HomeFragment msgFragment;
    private HomeFragment mineFragment;
    private Fragment mContent;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void switchContent(Fragment to) {
        if (mContent == to)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过
            if (mContent == null) {
                transaction.add(R.id.main_content_fl, to).commitAllowingStateLoss();
            } else {
                transaction.hide(mContent).add(R.id.main_content_fl, to).commitAllowingStateLoss();
            }
        } else { // 隐藏当前的fragment，显示下一个
            transaction.hide(mContent).show(to).commitAllowingStateLoss();
        }
        mContent = to;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        switchContent(homeFragment);
        String[] tabs = getResources().getStringArray(R.array.tab_name_array);
        int[] tabImg = getResources().getIntArray(R.array.tab_img_array);
        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.view_main_tab);
            TextView tv = tab.getCustomView().findViewById(R.id.tab_tv);
            tv.setText(tabs[i]);
            tv.setCompoundDrawablesWithIntrinsicBounds(0, tabImg[i], 0, 0);
            tabLayout.addTab(tab);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MvpPresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Object event) {

    }
}
