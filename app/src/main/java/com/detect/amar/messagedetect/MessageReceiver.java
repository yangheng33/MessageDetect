package com.detect.amar.messagedetect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.common.PhoneUtil;

public class MessageReceiver extends BroadcastReceiver {
    String TAG = "home";
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String AMAR_NOTICE = "amar.android.notice";
    public static final String SMS_CHANGE = "android.intent.action.SIM_STATE_CHANGED";

    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            if (pdus == null || pdus.length == 0)
                return;
            for (Object pdu : pdus) {
                try {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    String content = smsMessage.getMessageBody();
                    String smsDate = DatetimeUtil.longToDatetime(smsMessage.getTimestampMillis());
                    String receiveDate = DatetimeUtil.longToDatetime(System.currentTimeMillis());

                    int slot = intent.getIntExtra("simSlot", -1);//局限三星 gt s5282手机
                    Log.d(TAG, smsMessage.toString() + ",slot:" + slot);
                    Message message = new Message(sender, slot, content, smsDate, receiveDate, PhoneUtil.getDeviceNo(context));
                    Intent startIntent = new Intent(context, MessageDetectService.class);
                    startIntent.putExtra("message", message);

                    context.startService(startIntent);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        } else if (intent.getAction().equals(AMAR_NOTICE)) {
        } else if (intent.getAction().equals(SMS_CHANGE)) {
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d(TAG, "开机启动啦");
            Intent serviceIntent = new Intent(context, CheckStatusService.class);
            context.startService(serviceIntent);
        }
    }
}
