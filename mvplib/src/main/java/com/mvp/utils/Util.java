package com.mvp.utils;

import android.content.Context;

import com.mvp.R;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2019-12-24 18:05
 * @UpdateUser: dengbin
 * @UpdateDate: 2019-12-24 18:05
 * @UpdateRemark: 更新说明
 */
public class Util {

    public static int dp2px(Context context, float dp) {
        return (int) (context.getResources().getDimensionPixelSize(R.dimen.dp_10) / 10f * dp);
    }

    public static int widthDisplay(Context mContext) {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static int heightDisplay(Context mContext) {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }
}
