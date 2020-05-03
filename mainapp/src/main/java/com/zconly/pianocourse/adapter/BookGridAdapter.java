package com.zconly.pianocourse.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.BookDetailActivity;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ImgLoader;

import java.util.List;

/**
 * @Description: 书列表
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:49
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:49
 * @UpdateRemark: 更新说明
 */
public class BookGridAdapter extends BaseQuickAdapter<BookBean, BaseViewHolder> implements
        BaseQuickAdapter.OnItemClickListener {

    public BookGridAdapter(List<BookBean> beans) {
        super(R.layout.item_grid_book, beans);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BookBean item) {
        ImageView iv = helper.getView(R.id.item_book_iv);
        iv.getLayoutParams().height = (int) ((DeviceUtils.getScreenWidth() - DeviceUtils.dp2px(40f)) / 4f);
        ImgLoader.showImg(DataUtil.getImgUrl(item.getCover()), iv);
        helper.setText(R.id.item_book_author_tv, item.getAuthor());
        helper.setText(R.id.item_book_name_tv, item.getName());
    }

    @SingleClick
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BookBean bean = getItem(position);
        if (bean == null)
            return;
        if (bean.getItemType() == SheetBean.ITEM) {
            BookDetailActivity.start(mContext, bean);
        }
    }

}
