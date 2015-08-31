package com.detect.amar.common;

import android.content.Context;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;

/**
 * Created by SAM on 2015/8/28.
 */
public class PhoneUtil {

    /**
     * 获取本机的设备唯一编号,需要申请权限:android.permission.READ_PHONE_STATE
     *
     * @param context
     * @return
     */
    public static String getDeviceNo(Context context) {
        TelephonyManager telephoneMgr = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephoneMgr.getDeviceId();
    }

    public static String getBatteryChargeStatusDescription(int status) {
        String describe = "";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                describe = "充电状态";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                describe = "放电状态";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                describe = "未充电";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                describe = "充满电";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                describe = "未知道状态";
                break;
        }
        return describe;
    }
}
