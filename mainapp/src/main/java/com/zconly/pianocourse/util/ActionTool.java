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
public class ActionTool {
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

    public static void startChat(Context context, String contactId) {
        // Intent intent = new Intent(context, ActChat.class);
        // intent.putExtra("user", contactId);
        // ActionTool.startAct(context, intent);
    }

}
