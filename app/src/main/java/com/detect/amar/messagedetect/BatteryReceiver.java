package com.detect.amar.messagedetect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.messagedetect.log.ErrorLogUtil;
import com.detect.amar.messagedetect.main.MainActivity;
import com.detect.amar.messagedetect.setting.Setting;

/**
 * 很奇怪，xml注册的广播收不到消息，只能动态注册
 */
public class BatteryReceiver extends BroadcastReceiver {
    public BatteryReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 100);
            PreferencesUtils.putString(Setting.Current_Battery, (level * 100 / scale) + "");
            int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
            PreferencesUtils.putInt(Setting.Battery_Status, status);
            PreferencesUtils.putString(Setting.Battery_Status_Time, DatetimeUtil.getDurrentDatetime());
        }
    }

    public void gotoMain(Context context) {
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
