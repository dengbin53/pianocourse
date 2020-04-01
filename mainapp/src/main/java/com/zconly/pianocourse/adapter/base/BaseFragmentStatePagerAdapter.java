package com.zconly.pianocourse.adapter.base;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @Description: 通用 FragmentStatePagerAdapter
 * @Author: dengbin
 * @CreateDate: 2020/3/11 14:36
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/11 14:36
 * @UpdateRemark: 更新说明
 */
public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
        this(fm, null);
    }

    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, null);
    }

    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles.get(position);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
