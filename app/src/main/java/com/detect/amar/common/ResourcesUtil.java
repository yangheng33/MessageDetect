package com.detect.amar.common;

import android.app.Application;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;

public class ResourcesUtil {

    private static android.content.res.Resources resources;
    private static Application application;
    public static void setApplication(Application application) {
        resources = application.getResources();
    }

    public static android.content.res.Resources getResources() {
        return resources;
    }

    // 通过资源id查找资源
    public static String getString(int resId) {
        return resources.getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return resources.getString(resId, formatArgs);
    }

    public static String[] getStringArray(int resId) {
        return resources.getStringArray(resId);
    }

    public static int getInteger(int resId) {
        return resources.getInteger(resId);
    }

    public static int[] getIntArray(int resId) {
        return resources.getIntArray(resId);
    }

    public static boolean getBoolean(int resId) {
        return resources.getBoolean(resId);
    }

    public static XmlResourceParser getAnimation(int resId) {
        return resources.getAnimation(resId);
    }

    public static int getColor(int resId) {
        return resources.getColor(resId);
    }

    public static ColorStateList getColorStateList(int resId) {
        return resources.getColorStateList(resId);
    }

    public static float getDimension(int resId) {
        return resources.getDimension(resId);
    }

    public static int getDimensionPixelOffset(int resId) {
        return resources.getDimensionPixelOffset(resId);
    }

    public static int getDimensionPixelSize(int resId) {
        return resources.getDimensionPixelSize(resId);
    }

    public static Drawable getDrawable(int resId) {
        return resources.getDrawable(resId);
    }

    public static XmlResourceParser getLayout(int resId) {
        return resources.getLayout(resId);
    }

    public static Movie getMoive(int resId) {
        return resources.getMovie(resId);
    }

    public static CharSequence getText(int resId) {
        return resources.getText(resId);
    }

    public static CharSequence[] getTextArray(int resId) {
        return resources.getTextArray(resId);
    }

    public static XmlResourceParser getXml(int resId) {
        return resources.getXml(resId);
    }

    public static float getFraction(int resId, int base, int pbase) {
        return resources.getFraction(resId, base, pbase);
    }

    public static CharSequence getQuantityText(int resId, int quantity) {
        return resources.getQuantityText(resId, quantity);
    }

    public static String getQuantityString(int resId, int quantity) {
        return resources.getQuantityString(resId, quantity);
    }

    public static String getQuantityString(int resId, int quantity, Object... formatArgs) {
        return resources.getQuantityString(resId, quantity, formatArgs);
    }

    // 通过资源名查找资源
    // 下面的常量不知道是否准确，请测试
    public static String TYPE_INTEGER = "integer";
    public static String TYPE_BOOL = "bool";
    public static String TYPE_STRING = "string";
    public static String TYPE_ARRAY = "array";
    public static String TYPE_ATTR = "attr";
    public static String TYPE_COLOR = "color";
    public static String TYPE_DECLARE_STYLEABLE = "declare-styleable";
    public static String TYPE_DIMEN = "dimen";
    public static String TYPE_DRAWABLE = "drawable";
    public static String TYPE_STYLE = "style";
    public static String TYPE_INTEGER_ARRAY = "integer-array";
    public static String TYPE_STRING_ARRAY = "string-array";
    public static String TYPE_PLURALS = "plurals";
    public static String TYPE_FRACTION = "fraction";
    public static String TYPE_LAYOUT = "layout";

    public static int getResourcesId(String name, String type, String packageName) {
        return resources.getIdentifier(name, type, packageName);
    }

    public static int getResourcesId(String name, String type) {
        return getResourcesId(name, type, application.getPackageName());
    }

    public static String getString(String name) {
        return getString(getResourcesId(name, TYPE_STRING));
    }

    public static String getString(String name, Object... formatArgs) {
        return getString(getResourcesId(name, TYPE_STRING), formatArgs);
    }

    public static String[] getStringArray(String name) {
        return getStringArray(getResourcesId(name, TYPE_STRING_ARRAY));
    }

    public static int getInteger(String name) {
        return getInteger(getResourcesId(name, TYPE_INTEGER));
    }

    public static int[] getIntArray(String name) {
        return getIntArray(getResourcesId(name, TYPE_INTEGER_ARRAY));
    }

    public static boolean getBoolean(String name) {
        return getBoolean(getResourcesId(name, TYPE_BOOL));
    }
}

