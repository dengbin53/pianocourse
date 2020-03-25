
package com.zconly.pianocourse.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zconly.pianocourse.base.MainApplication;

/**
 * SharedPreferences操作相关
 *
 * @author DengBin
 */
public class SPTool {

    public static Context getContext() {
        return MainApplication.getInstance();
    }

    /**
     * 获取系统变量references
     */
    public static SharedPreferences getPreferences() {
        return getContext().getSharedPreferences("PIANO_COURSE_SYS", Context.MODE_PRIVATE);
    }

    /**
     * 存储boolean数据
     *
     * @param key
     * @param value
     */
    public static void putBool(String key, boolean value) {
        Editor edit = getPreferences().edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 获取存储的boolean数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBool(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    /**
     * 存储int数据
     *
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        Editor edit = getPreferences().edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 获取存储的int数据
     *
     * @param key
     */
    public static int getIntValue(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    /**
     * 存储String数据
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        Editor edit = getPreferences().edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 获取存储的String数据
     *
     * @param key
     */
    public static String getString(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    /**
     * 存储long数据
     *
     * @param key
     * @param value
     */
    public static void putLong(String key, long value) {
        Editor edit = getPreferences().edit();
        edit.putLong(key, value);
        edit.commit();
    }

    /**
     * 获取存储的long数据
     *
     * @param key
     */
    public static long getLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    /**
     * 存储对象
     *
     * @param key
     */
    public static void putObj(String key, Object obj) {
        String json = StringTool.obj2String(obj);
        Editor edit = getPreferences().edit();
        edit.putString(key, json);
        edit.commit();
    }

    /**
     * 获取存储的对象
     *
     * @param key
     */
    public static Object getObj(String key) {
        String ret = getPreferences().getString(key, null);
        Object o = StringTool.string2Obj(ret);
        return o;
    }

    /**
     * 删除String
     *
     * @param key
     */
    public static void deleteString(String key) {
        Editor edit = getPreferences().edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 删除存储的对象
     *
     * @param key
     */
    public static void deleteObj(String key) {
        deleteString(key);
    }

}