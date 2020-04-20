package com.zconly.pianocourse.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.widget.MKeyValueView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 课程列表
 * @Author: dengbin
 * @CreateDate: 2020/3/30 21:51
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 21:51
 * @UpdateRemark: 更新说明
 */
public class CourseHolder extends BaseViewHolder {

    // title
    @Nullable
    @BindView(R.id.item_list_title_tv)
    MKeyValueView singleTitleTv;

    // item
    @Nullable
    @BindView(R.id.item_list_course_iv)
    ImageView iv;
    @Nullable
    @BindView(R.id.item_list_course_title_tv)
    TextView titleTv;
    @Nullable
    @BindView(R.id.item_list_course_teacher_tv)
    TextView teacherTv;
    @Nullable
    @BindView(R.id.item_list_course_desc_tv)
    TextView descTv;
    @Nullable
    @BindView(R.id.item_list_course_count_tv)
    TextView countTv;
    @Nullable
    @BindView(R.id.item_list_course_status_tv)
    TextView statusTv;

    // live
    @Nullable
    @BindView(R.id.item_list_live_iv)
    ImageView liveIv;

    public CourseHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bindData(CourseBean item) {
        switch (item.getItemType()) {
            case CourseBean.TITLE:
            case CourseBean.LIVE_TITLE:
                if (singleTitleTv == null)
                    break;
                singleTitleTv.setName(item.getTitle());
                break;
            case CourseBean.ITEM:
                String url = TextUtils.isEmpty(item.getCover_small()) ? item.getCover() : item.getCover_small();
                if (iv != null)
                    ImgLoader.showImgRound(DataUtil.getImgUrl(url), iv);
                if (titleTv != null)
                    titleTv.setText(item.getTitle());
                if (descTv != null)
                    descTv.setText(item.getDescription());
                if (teacherTv != null)
                    teacherTv.setText(item.getTeacher());
                if (countTv != null)
                    countTv.setText(item.getView_count() + "次观看");
                if (statusTv != null)
                    statusTv.setText("");
                break;
            case CourseBean.LIVE:
                ImgLoader.showImg(DataUtil.getImgUrl(item.getCover()), liveIv);
                break;
            default:
                break;
        }
    }

}
