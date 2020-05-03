package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.base.BaseFragmentPagerAdapter;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.fragment.CourseListFragment;
import com.zconly.pianocourse.mvp.presenter.CoursePresenter;
import com.zconly.pianocourse.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description: 大师课首页
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class CourseListActivity extends BaseMvpActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public static void start(Context context, int category) {
        Intent intent = new Intent(context, CourseListActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_COURSE_TYPE, category);
        context.startActivity(intent);
    }

    @Override
    protected boolean initView() {
        ((TitleView) mTitleView).setTitle(getString(R.string.title_classroom));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CourseListFragment.getInstance(Constants.CATEGORY_PARENTS_COURSE));
        fragments.add(CourseListFragment.getInstance(Constants.CATEGORY_TEACHER_COURSE));
        fragments.add(CourseListFragment.getInstance(Constants.CATEGORY_OTHER_COURSE));
        List<String> titles = new ArrayList<>();
        titles.add("家长课");
        titles.add("老师课");
        titles.add("曲目精讲");

        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);

        return true;
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
        return R.layout.view_tab_viewpager;
    }

    @Override
    protected CoursePresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

}
