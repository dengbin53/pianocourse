package com.zconly.pianocourse.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 21:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 21:54
 * @UpdateRemark: 更新说明
 */
public class ActionUtil {
    public static void startActForRet(Activity context, Intent intent, int requestCode) {
        if (context == null)
            return;
        context.startActivityForResult(intent, requestCode);
    }

    public static void startActForRet(Activity context, Class to, int requestCode) {
        if (context == null)
            return;
        Intent intent = new Intent(context, to);
        context.startActivityForResult(intent, requestCode);
    }

    public static void startAct(Activity context, Class to) {
        if (context == null)
            return;
        Intent intent = new Intent(context, to);
        context.startActivity(intent);
    }

    public static void startAct(Activity context, Intent to) {
        if (context == null)
            return;
        context.startActivity(to);
    }

    public static void call(Context context, String phone) {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//        startAct(mContext, intent);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startAct(context, intent);
    }

    private static void startAct(Context context, Intent intent) {
        if (context == null)
            return;
        context.startActivity(intent);
    }

    public static void startWebBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
