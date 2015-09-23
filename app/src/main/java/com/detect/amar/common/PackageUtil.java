package com.detect.amar.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2015/9/23 0023.
 */
public class PackageUtil {

    /**
     *
     * @param context
     * @return 数组0：给用户看的版本号，数组1：实际使用的版本号
     */
    public static Object[] getAppVersionInfo(Context context) {
        Object[] info = new Object[2];
        info[0] = "v1.0";
        info[1] = 1;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            info[0] = packageInfo.versionName;
            info[1] = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }


}
