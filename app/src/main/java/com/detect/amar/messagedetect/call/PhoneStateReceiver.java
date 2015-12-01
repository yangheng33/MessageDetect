package com.detect.amar.messagedetect.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.detect.amar.messagedetect.main.MainActivity;

public class PhoneStateReceiver extends BroadcastReceiver {

    private static int lastCallState = TelephonyManager.CALL_STATE_IDLE;

    @Override
    public void onReceive(Context context, Intent intent) {
        int slot = intent.getIntExtra("simSlot", -1) + 1;//局限三星 gt s5282手机 卡槽1的序号是0，卡槽2的序号是1,负数表示无效值
        Bundle bundle = intent.getExtras();
        String incomingNumber = bundle.getString("incoming_number");//来电号码

        String action = intent.getAction();
        Log.d("PhoneStateReceiver", action);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int currentCallState = telephonyManager.getCallState();
        Log.d("PhoneStateReceiver", "currentCallState=" + currentCallState);
        if (currentCallState == TelephonyManager.CALL_STATE_IDLE) {// 空闲
        } else if (currentCallState == TelephonyManager.CALL_STATE_RINGING) {// 响铃
        } else if (currentCallState == TelephonyManager.CALL_STATE_OFFHOOK) {// 接听
            CallInfo callInfo = new CallInfo(incomingNumber, System.currentTimeMillis(), slot, CallInfo.CALL_TYPE_CALL_RECEIVE);//已接来电
            transCall(context, callInfo);
        }
        if (lastCallState == TelephonyManager.CALL_STATE_RINGING && currentCallState == TelephonyManager.CALL_STATE_IDLE) {
            //Toast.makeText(context, "有未接来电" + incomingNumber + "," + slot, Toast.LENGTH_SHORT).show();
            CallInfo callInfo = new CallInfo(incomingNumber, System.currentTimeMillis(), slot, CallInfo.CALL_TYPE_CALL_MISS);//未接来电
            transCall(context, callInfo);
        }
        lastCallState = currentCallState;
    }

    public void transCall(Context context, CallInfo callInfo) {
        Intent startIntent = new Intent(context, CallTransService.class);
        startIntent.putExtra("callInfo", callInfo);
        context.startService(startIntent);
        //gotoMain(context);
    }

    public void gotoMain(Context context) {
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent1);
    }
}
