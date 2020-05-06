package com.zconly.pianocourse.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.ExercisePlayActivity;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ImgLoader;

import java.util.List;

/**
 * @Description: 曲目列表
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:49
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:49
 * @UpdateRemark: 更新说明
 */
public class SheetListAdapter extends BaseMultiItemQuickAdapter<SheetBean, BaseViewHolder> implements
        BaseQuickAdapter.OnItemClickListener {

    public SheetListAdapter(List<SheetBean> beans) {
        super(beans);
        addItemType(SheetBean.ITEM, R.layout.item_list_sheet);

        setOnItemClickListener(this);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SheetBean item) {
        ImgLoader.showImgRound(DataUtil.getImgUrl(item.getCover()), helper.getView(R.id.item_list_sheet_iv),
                DeviceUtils.dp2px(4f));
        helper.setText(R.id.item_list_sheet_title_tv, item.getName());
        helper.setText(R.id.item_list_sheet_author_tv, item.getAuthor());
        helper.setText(R.id.item_list_sheet_level_tv, DataUtil.getSheetLevel(item.getLevel()));
        helper.setText(R.id.item_list_sheet_play_tv, item.getView_count() + "");
    }

    @SingleClick
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SheetBean bean = getItem(position);
        if (bean == null)
            return;
        if (bean.getItemType() == SheetBean.ITEM) {
            ExercisePlayActivity.start(mContext, bean);
        }
    }

}
