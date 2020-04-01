package com.zconly.pianocourse.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.util.ImgLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:51
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:51
 * @UpdateRemark: 更新说明
 */
public class CourseHolder extends BaseViewHolder {

    @Nullable
    @BindView(R.id.item_list_title_tv)
    TextView singleTitleTv;

    @Nullable
    @BindView(R.id.item_list_course_iv)
    ImageView iv;
    @Nullable
    @BindView(R.id.item_list_course_title_tv)
    TextView titleTv;


    public CourseHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bindData(CourseBean item) {
        switch (item.getItemType()) {
            case CourseBean.TITLE:
                singleTitleTv.setText(item.getTitle());
                break;
            case CourseBean.ITEM:
                ImgLoader.showImgRound(item.getImg(), iv);
                titleTv.setText(item.getTitle());
                break;
            default:
                break;
        }
    }

}
