package com.detect.amar.messagedetect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.messagedetect.setting.Setting;

public class BatteryStageReceiver extends BroadcastReceiver {
    public BatteryStageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW) ) {
            PreferencesUtils.putString(Setting.Current_Battery, 15 + "");
            PreferencesUtils.putInt(Setting.Battery_Status, BatteryManager.BATTERY_STATUS_DISCHARGING);
            PreferencesUtils.putString(Setting.Battery_Status_Time, DatetimeUtil.getDurrentDatetime());
        }
        else if(intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)){
            PreferencesUtils.putString(Setting.Current_Battery, 100 + "");
            PreferencesUtils.putInt(Setting.Battery_Status, BatteryManager.BATTERY_STATUS_FULL);
            PreferencesUtils.putString(Setting.Battery_Status_Time, DatetimeUtil.getDurrentDatetime());
        }
    }

}
