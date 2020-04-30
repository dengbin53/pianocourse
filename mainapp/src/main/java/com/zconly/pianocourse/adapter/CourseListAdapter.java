package com.zconly.pianocourse.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.CourseDetailActivity;
import com.zconly.pianocourse.activity.CourseListActivity;
import com.zconly.pianocourse.activity.WebViewActivity;
import com.zconly.pianocourse.activity.XiaoeActivity;
import com.zconly.pianocourse.adapter.viewholder.CourseHolder;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.HomePageBean;

import java.util.List;

/**
 * @Description: 课程列表
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:49
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:49
 * @UpdateRemark: 更新说明
 */
public class CourseListAdapter extends BaseMultiItemQuickAdapter<CourseBean, CourseHolder> implements
        BaseQuickAdapter.OnItemClickListener {

    public CourseListAdapter(List<CourseBean> beans) {
        super(beans);
        addItemType(CourseBean.TITLE, R.layout.item_list_title);
        addItemType(CourseBean.LIVE_TITLE, R.layout.item_list_title);
        addItemType(CourseBean.ITEM, R.layout.item_list_course);
        addItemType(CourseBean.LIVE, R.layout.item_list_live);
        addItemType(CourseBean.BANNER, R.layout.item_list_course_banner);

        setOnItemClickListener(this);
    }

    @Override
    protected void convert(@NonNull CourseHolder helper, CourseBean item) {
        helper.bindData(item);
    }

    @SingleClick
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CourseBean bean = getItem(position);
        if (bean == null)
            return;
        if (bean.getItemType() == CourseBean.ITEM) {
            if (bean.getType() == HomePageBean.RecommendBean.TYPE_RECOMMEND_COURSE) {
                CourseDetailActivity.start(mContext, bean);
            } else {
                XiaoeActivity.start(mContext, bean.getUrl());
            }
        } else if (bean.getItemType() == CourseBean.LIVE || bean.getItemType() == CourseBean.LIVE_TITLE) {
            if (bean.getType() == HomePageBean.RecommendBean.TYPE_RECOMMEND_COURSE) {
                CourseDetailActivity.start(mContext, bean);
            } else {
                XiaoeActivity.start(mContext, bean.getUrl());
            }
        } else if (bean.getItemType() == CourseBean.BANNER) { // 大师课-课程列表-上方banner

        } else {
            if (TextUtils.isEmpty(bean.getUrl())) {
                CourseListActivity.start(mContext, bean.getViewType());
            } else {
                WebViewActivity.start(mContext, bean.getTitle(), bean.getUrl());
            }
        }
    }

}
