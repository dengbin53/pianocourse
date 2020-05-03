package com.zconly.pianocourse.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.zconly.pianocourse.constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class UmengUtil {

    static public void onEvent(Context context, String event_id) {
        MobclickAgent.onEvent(context, event_id);
    }
	
	/*static public void onEvent(Context context, String event_id, int acc) {
		if(!bStatStarted)return;
		MobclickAgent.onEvent(context, event_id, acc);
	}*/

    static public void onEvent(Context context, String event_id, String... keyval) {
        HashMap<String, String> map = genMap(keyval);
        onEvent(context, event_id, map);
    }

    private static HashMap<String, String> genMap(String... keyval) {
        HashMap<String, String> status = new HashMap<>();
        if (Constants.USER_BEAN != null)
            status.put("user_id", Constants.USER_BEAN.getId() + "");
        else
            status.put("user_id", "");
        if (keyval != null && keyval.length > 0) {
            for (int i = 0; i + 1 < keyval.length; i += 2) {
                status.put(keyval[i], keyval[i + 1]);
            }
        }
        return status;
    }

    static public void onEvent(Context context, String event_id, Map<String, String> map) {
        MobclickAgent.onEvent(context, event_id, map);
    }

    static public void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    static public void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    static public void onPageStart(String pageName) {
        MobclickAgent.onPageStart(pageName);
    }

    static public void onPageEnd(String pageName) {
        MobclickAgent.onPageEnd(pageName);
    }
}
