package com.detect.amar.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Set;


public class PreferencesUtils {
    private static SharedPreferences standardPreferences;

    private static Application curApp;
    public static void setApplication(Application app)
    {
        curApp = app;
        standardPreferences = app.getSharedPreferences("standard", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getStandardUserPreferences() {
        return standardPreferences;
    }

    public static boolean getBoolean(String key) {
        return standardPreferences.getBoolean(key, false);
    }

    public static int getInt(String key) {
        return standardPreferences.getInt(key, 0);
    }

    public static long getLong(String key) {
        return standardPreferences.getLong(key, 0l);
    }

    public static float getFloat(String key) {
        return standardPreferences.getFloat(key, 0f);
    }

    public static String getString(String key) {
        return standardPreferences.getString(key, null);
    }

    public static Set<String> getStringSet(String key) {
        return standardPreferences.getStringSet(key, null);
    }

    public static <T> T getObject(String key, Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(getString(key), cls);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return standardPreferences.getBoolean(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return standardPreferences.getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return standardPreferences.getLong(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return standardPreferences.getFloat(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return standardPreferences.getString(key, defValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return standardPreferences.getStringSet(key, defValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key, T defValue) {
        Object object = getObject(key, defValue.getClass());
        if (null == object) {
            return defValue;
        } else {
            return (T) object;
        }
    }

    public static void putBoolean(String key, boolean value) {
        standardPreferences.edit().putBoolean(key, value).apply();
    }

    public static void putInt(String key, int value) {
        standardPreferences.edit().putInt(key, value).apply();
    }

    public static void putLong(String key, long value) {
        standardPreferences.edit().putLong(key, value).apply();
    }

    public static void putFloat(String key, float value) {
        standardPreferences.edit().putFloat(key, value).apply();
    }

    public static void putString(String key, String value) {
        standardPreferences.edit().putString(key, value).apply();
    }

    public static void putStringSet(String key, Set<String> value) {
        standardPreferences.edit().putStringSet(key, value).apply();
    }

    public static void putObject(String key, Object object) {
        Gson gson = new Gson();
        putString(key, gson.toJson(object));
    }

    public static void clear() {
        standardPreferences.edit().clear().apply();
    }

    public static void remove(String key) {
        standardPreferences.edit().remove(key).apply();
    }

    public static boolean contains(String key) {
        return standardPreferences.contains(key);
    }
}
