package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.UpdateBean;
import com.zconly.pianocourse.fragment.ClassroomFragment;
import com.zconly.pianocourse.fragment.HomeFragment;
import com.zconly.pianocourse.fragment.MineFragment;
import com.zconly.pianocourse.fragment.QinfangFragment;
import com.zconly.pianocourse.mvp.presenter.MainPresenter;
import com.zconly.pianocourse.mvp.view.MainView;
import com.zconly.pianocourse.util.ActionUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.StringUtils;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.widget.dialog.DialogConfirm;

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
 * <p>
 * Course  课程(课)
 * SectionPack 章节包（视频包）
 * Section 章节(视频)
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView {

    @BindView(R.id.main_tl)
    TabLayout tabLayout;
    @BindView(R.id.main_content_fl)
    FrameLayout contentFl;

    private Fragment[] fragments = new Fragment[4];
    private Fragment mContent;
    private long backPressTime;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    // 升级提示
    private void showUpdateDialog(UpdateBean result) {
        DialogConfirm dialog = new DialogConfirm(mContext, v1 -> {
            if (v1.getId() == R.id.dialog_confirm_btnleft) { // 确定
                ActionUtil.startWebBrowser(mContext, result.getUrl());
                if (result.isMust())
                    tabLayout.postDelayed(this::finish, 3000);
            } else if (result.isMust()) {
                showConfirmDialog(result);
            }
        }, result.getDesc());
        dialog.setCancelable(!result.isMust());
        dialog.show(getSupportFragmentManager(), null);
    }

    // 二次确认
    private void showConfirmDialog(UpdateBean result) {
        DialogConfirm dialog = new DialogConfirm(mContext, v1 -> {
            if (v1.getId() == R.id.dialog_confirm_btnleft) {
                ActionUtil.startWebBrowser(mContext, result.getUrl());
            }
            finish();
        }, "不升级将退出程序，是否继续更新？");
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), null);
    }

    private void switchContent(Fragment to) {
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

    private void refresh(int tag) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // initView();
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected boolean initView() {
        fragments[0] = new HomeFragment();
        fragments[1] = new ClassroomFragment();
        fragments[2] = new QinfangFragment();
        fragments[3] = new MineFragment();
        switchContent(fragments[0]);

        String[] tabs = getResources().getStringArray(R.array.tab_name_array);
        int[] icon = new int[]{R.drawable.selector_tab_home, R.drawable.selector_tab_classroom,
                R.drawable.selector_tab_qinfang, R.drawable.selector_tab_mine};
        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.view_main_tab);
            TextView tv = tab.getCustomView().findViewById(R.id.tab_tv);
            tv.setText(tabs[i]);
            tv.setCompoundDrawablesWithIntrinsicBounds(0, icon[i], 0, 0);
            tab.setTag(i);
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = (int) tab.getTag();
                switchContent(fragments[pos]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                refresh((int) tab.getTag());
            }
        });

        return true;
    }

    @Override
    protected void initData() {
        mPresenter.getUpdateResult();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - backPressTime > 2000) {
                ToastUtil.toast("再按一次退出程序");
                backPressTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Object event) {

    }

    @Override
    public void getUpdateSuccess(UpdateBean.UpdateResult result) {
        if (result.getAndroid() == null) return;
        String vn = DeviceUtils.getAppVersionName();
        if (vn == null) return;
        String[] ver = vn.split("\\.");
        if (ver.length != 3) return;
        // 版本号用的大版本3.3.4这样。比如1.1.4就是  （1 001 0004）
        int v = StringUtils.string2int(ver[0]) * 10000000 + StringUtils.string2int(ver[1]) * 10000
                + StringUtils.string2int(ver[2]);
        if (v < result.getAndroid().getVer()) return;

        // 有更新
        showUpdateDialog(result.getAndroid());
    }

}
