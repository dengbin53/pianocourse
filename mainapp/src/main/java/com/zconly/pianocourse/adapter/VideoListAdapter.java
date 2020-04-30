package com.zconly.pianocourse.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.bean.VideoPackBean;
import com.zconly.pianocourse.util.ArrayUtil;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.DeviceUtils;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.StringUtils;

import java.util.List;

/**
 * @Description: 视频列表
 * @Author: dengbin
 * @CreateDate: 2020/4/3 10:50
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 10:50
 * @UpdateRemark: 更新说明
 */
public class VideoListAdapter<B extends BaseBean> extends BaseMultiItemQuickAdapter<B, BaseViewHolder> {

    public VideoListAdapter(@Nullable List<B> data) {
        super(data);
        addItemType(Constants.VIEW_TYPE_PACK, R.layout.item_list_pack);
        addItemType(Constants.VIEW_TYPE_VIDEO, R.layout.item_list_video);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, B item) {
        if (item instanceof VideoBean) {
            VideoBean vb = (VideoBean) item;
            helper.setText(R.id.item_video_title_tv, vb.getTitle());
            String s = DateUtils.getTime2M(vb.getC_time()) + " | " + vb.getView_count() + "人学过";
            helper.setText(R.id.item_video_time_tv, s);
        } else if (item instanceof VideoPackBean) {
            VideoPackBean vpb = (VideoPackBean) item;
            helper.setText(R.id.item_pack_title_tv, vpb.getTitle());
            helper.setText(R.id.item_pack_teacher_tv, "主讲老师：" + vpb.getTeacher());
            helper.setText(R.id.item_pack_count_tv, "共" + vpb.getVideo_count() + "讲");
            ImgLoader.showImgRound(DataUtil.getImgUrl(vpb.getCover_small()), helper.getView(R.id.item_pack_iv));

            ((ViewGroup.MarginLayoutParams) helper.itemView.getLayoutParams()).topMargin
                    = helper.getLayoutPosition() == 1 ? DeviceUtils.dp2px(8f) : 0;

            if (vpb.isOpened()) {
                helper.setText(R.id.item_pack_btn, "收起课程列表");
                helper.setVisible(R.id.item_pack_video_title_tv, true);
                helper.itemView.getLayoutParams().height = DeviceUtils.dp2px(112f);
            } else {
                helper.setText(R.id.item_pack_btn, "展示课程列表");
                helper.setVisible(R.id.item_pack_video_title_tv, false);
                helper.itemView.getLayoutParams().height = DeviceUtils.dp2px(80f);
            }

            helper.addOnClickListener(R.id.item_pack_btn);
        }
    }

    public void closePack(int position) {
        VideoPackBean obj = (VideoPackBean) getItem(position);
        if (obj == null || ArrayUtil.isEmpty(obj.getVideoBeans()))
            return;

        obj.setOpened(false);
        notifyItemChanged(position + getHeaderLayoutCount());

        for (int i = position + obj.getVideoBeans().size(); i > position; i--) {
            remove(i);
        }
    }

    public boolean openPack(int position, List beans) {
        VideoPackBean obj = (VideoPackBean) getItem(position);
        if (obj == null || ArrayUtil.isEmpty(beans))
            return false;

        obj.setOpened(true);
        notifyItemChanged(position + getHeaderLayoutCount());

        if (ArrayUtil.isEmpty(obj.getVideoBeans()))
            obj.setVideoBeans(beans);
        addData(position + 1, beans);

        return true;
    }
}
