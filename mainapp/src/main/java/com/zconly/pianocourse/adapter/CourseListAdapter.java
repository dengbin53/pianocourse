package com.zconly.pianocourse.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.CourseDetailActivity;
import com.zconly.pianocourse.adapter.viewholder.CourseHolder;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.CourseBean;

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
        addItemType(CourseBean.ITEM, R.layout.item_list_course);

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
        if (bean.getItemType() != CourseBean.ITEM)
            return;
        CourseDetailActivity.start(mContext, bean);
    }
}
