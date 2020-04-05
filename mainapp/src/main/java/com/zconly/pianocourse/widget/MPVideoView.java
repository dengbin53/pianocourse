package com.zconly.pianocourse.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @Description: 适应全屏显示
 * @Author: dengbin
 * @CreateDate: 2020/4/5 18:20
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/4/5 18:20
 * @UpdateRemark: 更新说明
 */
public class MPVideoView extends VideoView {

    public MPVideoView(Context context) {
        this(context, null);
    }

    public MPVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MPVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

}
