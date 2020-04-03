package com.zconly.pianocourse.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.util.DateUtils;

import java.util.List;

/**
 * @Description: 视频列表
 * @Author: dengbin
 * @CreateDate: 2020/4/3 10:50
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/3 10:50
 * @UpdateRemark: 更新说明
 */
public class VideoListAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

    public VideoListAdapter(@Nullable List<VideoBean> data) {
        super(R.layout.item_list_video, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VideoBean item) {
        helper.setText(R.id.item_video_title_tv, item.getTitle());
        String s = DateUtils.getTime2m(item.getC_time()) + " | " + item.getView_count() + "人学过";
        helper.setText(R.id.item_video_time_tv, s);
    }

}
