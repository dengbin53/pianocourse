package com.zconly.pianocourse.base.callback;

import android.view.View;

public interface MClickCallback {
	public static final int CLICK_DOUBLE = 1;
	public static final int CLICK_SINGLE = 0;

	void callback(int type, View view);
}
