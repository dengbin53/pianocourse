package com.zconly.pianocourse.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zconly.pianocourse.base.MainApplication;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020-01-02 10:49
 * @UpdateUser: dengbin
 * @UpdateDate: 2020-01-02 10:49
 * @UpdateRemark: 更新说明
 */
public class SPHelper {

    private final SharedPreferences sharedPreference;

    private SPHelper(SharedPreferences sharedPreferences) {
        this.sharedPreference = sharedPreferences;
    }

    public static SPHelper getInstance(String name) {
        return new SPHelper(MainApplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE));
    }


    /**
     * 保存一个Boolean类型的值！
     *
     * @param key
     * @param value
     */
    public boolean putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 保存一个int类型的值！
     *
     * @param key
     * @param value
     */
    public boolean putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 保存一个float类型的值！
     *
     * @param key
     * @param value
     */
    public boolean putFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 保存一个long类型的值！
     *
     * @param key
     * @param value
     */
    public boolean putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 保存一个String类型的值！
     *
     * @param key
     * @param value
     */
    public boolean putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 获取String的value
     *
     * @param key      名字
     * @param defValue 默认值
     */
    public String getString(String key, String defValue) {
        return sharedPreference.getString(key, defValue);
    }

    /**
     * 获取int的value
     *
     * @param key      名字
     * @param defValue 默认值
     */
    public int getInt(String key, int defValue) {
        return sharedPreference.getInt(key, defValue);
    }

    /**
     * 获取float的value
     *
     * @param key      名字
     * @param defValue 默认值
     */
    public float getFloat(String key, Float defValue) {
        return sharedPreference.getFloat(key, defValue);
    }

    /**
     * 获取boolean的value
     *
     * @param key      名字
     * @param defValue 默认值
     */
    public boolean getBoolean(String key, Boolean defValue) {
        return sharedPreference.getBoolean(key, defValue);
    }

    /**
     * 获取long的value
     *
     * @param key      名字
     * @param defValue 默认值
     */
    public long getLong(String key, long defValue) {
        return sharedPreference.getLong(key, defValue);
    }

}
