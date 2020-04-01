package com.zconly.pianocourse.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.base.BaseFragmentPagerAdapter;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.bean.result.CourseResult;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.mvp.view.CourseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description: 首页
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class HomeFragment extends BaseMvpFragment<CoursePresenter> implements CourseView {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void initView(View view) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CourseListFragment.getInstance(0));
        fragments.add(CourseListFragment.getInstance(1));
        fragments.add(CourseListFragment.getInstance(2));
        List<String> titles = new ArrayList<>();
        titles.add("家长课");
        titles.add("老师课");
        titles.add("曲目精讲");

        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);
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
        return R.layout.fragment_home;
    }

    @Override
    protected CoursePresenter getPresenter() {
        return new CoursePresenter(this);
    }

    @Override
    public void getCourseListSuccess(CourseListResult response) {

    }

    @Override
    public void getCourseSuccess(CourseResult response) {

    }

}
