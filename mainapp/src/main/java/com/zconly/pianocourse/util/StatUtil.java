package com.zconly.pianocourse.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.zconly.pianocourse.base.Constants;

import java.util.HashMap;
import java.util.Map;


public class StatUtil {

    static public void onEvent(Context context, String event_id) {
        MobclickAgent.onEvent(context, event_id);
    }
	
	/*static public void onEvent(Context context, String event_id, int acc) {
		if(!bStatStarted)return;
		MobclickAgent.onEvent(context, event_id, acc);
	}*/

    static public void onEvent(Context context, String event_id, String... keyval) {
        HashMap<String, String> map = genMap(keyval);
        StatUtil.onEvent(context, event_id, map);
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

//	static public void onEventBegin(Context context,String event_id) {
//		if(MainApplication.getInstance().abc)return;
//		MobclickAgent.onEventBegin(context, event_id);
//	}
//
//	static public void onEventEnd(Context context,String event_id) {
//		if(MainApplication.getInstance().abc)return;
//		MobclickAgent.onEventBegin(context, event_id);
//	}
//
//	static public void onEventBegin(Context context,String event_id, String... keyval) {
//		if(MainApplication.getInstance().abc)return;
//		MobclickAgent.onEventBegin(context, event_id);
//	}
//
//	static public void onEventEnd(Context context,String event_id, String... keyval) {
//		if(MainApplication.getInstance().abc)return;
//		HashMap<String, String> map = genMap(keyval);
//		StatUtil.onKVEventBegin(context, event_id, map);
//	}

//	static public void onKVEventBegin(Context context, String event_id, HashMap<String,String> map) {
//		if(MainApplication.getInstance().abc)return;
//		MobclickAgent.onKVEventBegin(context, event_id, map, "");
//	}
//
//	static public void onKVEventEnd(Context context, String event_id, HashMap<String,String> map) {
//		if(MainApplication.getInstance().abc)return;
//		MobclickAgent.onKVEventEnd(context, event_id, "");
//	}

//	static public void onSocialEvent(Context context, UMedia type, String user_id, GENDER gender, String id){
//		if(MainApplication.getInstance().abc)return;
//		UMPlatformData platform = new UMPlatformData(type, user_id);
//		platform.setGender(gender); //optional GENDER.MALE
//		platform.setWeiboId(id);  //optional "weiboId"
//
//		MobclickAgent.onSocialEvent(context, platform);
//	}

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
