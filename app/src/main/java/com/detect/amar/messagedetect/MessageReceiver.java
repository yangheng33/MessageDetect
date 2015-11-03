package com.detect.amar.messagedetect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.common.PhoneUtil;
import com.detect.amar.messagedetect.main.MainActivity;
import com.detect.amar.messagedetect.version.VersionActivity;

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

                    int slot = intent.getIntExtra("simSlot", -2) + 1;//局限三星 gt s5282手机 卡槽1的序号是0，卡槽2的序号是1,负数表示无效值
                    Log.d(TAG, smsMessage.toString() + ",slot:" + slot);
                    Message message = new Message(sender, slot, content, smsDate, receiveDate, PhoneUtil.getDeviceNo(context));
                    Intent startIntent = new Intent(context, MessageTransService.class);
                    startIntent.putExtra("message", message);

                    context.startService(startIntent);
                    gotoMain(context);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        } else if (intent.getAction().equals(AMAR_NOTICE)) {
//            Intent intent1 = new Intent(context, MainActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent1);
        } else if (intent.getAction().equals(SMS_CHANGE)) {
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent sayHelloIntent=new Intent(context,MainActivity.class);
            sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sayHelloIntent);
        }
    }

    public void gotoMain(Context context) {
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }

}
