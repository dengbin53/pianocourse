package com.zconly.pianocourse.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.ExerciseReportActivity;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.ImgLoader;

import java.util.List;

/**
 * @Description: 练习列表
 * @Author: dengbin
 * @CreateDate: 2020/5/3 16:25
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/3 16:25
 * @UpdateRemark: 更新说明
 */
public class ExerciseListAdapter extends BaseQuickAdapter<ExerciseBean, BaseViewHolder> implements
        BaseQuickAdapter.OnItemClickListener {

    public ExerciseListAdapter(List<ExerciseBean> beans) {
        super(R.layout.item_list_exercise, beans);

        setOnItemClickListener(this);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ExerciseBean item) {
        ImgLoader.showImgSmall(DataUtil.getImgUrl(item.getSheet().getCover()),
                helper.getView(R.id.item_list_exercise_iv));
        helper.setText(R.id.item_list_exercise_title_tv, item.getSheet().getDesc_name());
        helper.setText(R.id.item_list_exercise_author_tv, item.getSheet().getAuthor());
        helper.setText(R.id.item_list_exercise_time_tv, DateUtils.getTime2M(item.getC_time()));
    }

    @SingleClick
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ExerciseBean bean = getItem(position);
        if (bean == null)
            return;
        ExerciseReportActivity.start(mContext, bean);
    }

}
