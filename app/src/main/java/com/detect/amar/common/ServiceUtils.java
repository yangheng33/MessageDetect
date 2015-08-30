package com.detect.amar.common;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by SAM on 2015/8/30.
 */
public class ServiceUtils {

    /**
     *  example :boolean isRunning = isServiceRunning(MessageDetectService.class.getName(),context);
     * @param serviceName
     * @param context
     * @return
     */
    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
