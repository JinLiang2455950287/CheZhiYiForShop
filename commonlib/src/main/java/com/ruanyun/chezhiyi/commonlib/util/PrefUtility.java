package com.ruanyun.chezhiyi.commonlib.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefUtility {

    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "saveInfo";
    public static final String LOGIN_NAME = "login_name";
    private static SharedPreferences pref;

    public static SharedPreferences getPref() {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(AppUtility.getContext());
        }
        return pref;
    }

    public static void put(String name, String value) {
        Editor editor = getPref().edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void del(String name) {
        Editor editor = getPref().edit();
        editor.remove(name);
        editor.commit();
    }

    public static void put(String name, int value) {
        Editor editor = getPref().edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static void put(String name, Long value) {
        Editor editor = getPref().edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static void put(String name, Boolean value) {
        Editor editor = getPref().edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean contains(String name) {
        return getPref().contains(name);
    }

    public static int getInt(String name, int defaultValue) {
        return getPref().getInt(name, defaultValue);
    }

    // 获取一个boolean值 第一个为putBoolean()方法里的key 第二个为默认值
    public static boolean getBoolean(String name, boolean defaultValue) {
        return getPref().getBoolean(name, defaultValue);
    }

    public static Long getLong(String name, Long defaultValue) {
        return getPref().getLong(name, defaultValue);
    }

    public static String get(String name, String defaultValue) {
        return getPref().getString(name, defaultValue);
    }

}
