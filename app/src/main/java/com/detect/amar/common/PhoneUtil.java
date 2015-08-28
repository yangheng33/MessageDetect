package com.detect.amar.common;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by SAM on 2015/8/28.
 */
public class PhoneUtil {

    /**
     * 获取本机的设备唯一编号,需要申请权限:android.permission.READ_PHONE_STATE
     * @param context
     * @return
     */
    public static String getDeviceNo(Context context) {
        TelephonyManager telephoneMgr = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephoneMgr.getDeviceId();
    }
}
