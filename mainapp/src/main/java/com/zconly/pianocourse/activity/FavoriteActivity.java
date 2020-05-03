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
import com.zconly.pianocourse.fragment.VideoListFragment;
import com.zconly.pianocourse.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description: 收藏
 * @Author: dengbin
 * @CreateDate: 2020/4/3 10:36
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 10:36
 * @UpdateRemark: 更新说明
 */
public class FavoriteActivity extends BaseMvpActivity<BasePresenter> {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public static void start(Context context, int category) {
        Intent intent = new Intent(context, FavoriteActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_COURSE_TYPE, category);
        context.startActivity(intent);
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle("我的收藏");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CourseListFragment.getInstance(Constants.CATEGORY_FAVORITE_COURSE));
        fragments.add(VideoListFragment.getInstance(0));
        List<String> titles = new ArrayList<>();
        titles.add("课程");
        titles.add("视频");

        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);

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
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

}
