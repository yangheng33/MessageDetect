package com.detect.amar.messagedetect;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.detect.amar.common.DatetimeUtil;

public class MessageReceiver extends BroadcastReceiver {
    String TAG = "home";
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String AMAR_NOTICE = "amar.android.notice";
    public static final String SMS_CHANGE = "android.intent.action.SIM_STATE_CHANGED";

    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Toast.makeText(context, "call:" + Intent.ACTION_BOOT_COMPLETED, Toast.LENGTH_SHORT).show();
            Intent newIntent = new Intent(context, HomeActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
            context.startActivity(newIntent);

        } else if (intent.getAction().equals(SMS_RECEIVED)) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            if (pdus == null || pdus.length == 0)
                return;
            for (Object pdu : pdus) {
                try {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    String receiver = smsMessage.getServiceCenterAddress();
                    String content = smsMessage.getMessageBody();
                    String time = DatetimeUtil.longToDatetime(smsMessage.getTimestampMillis());

                    Message message = new Message(sender, receiver, content, time, DatetimeUtil.longToDatetime(new Date().getTime()));

                    int slot = intent.getIntExtra("simSlot", -1);//局限三星 gt s5282手机

                    Log.d(TAG, getDeviceInfo(context) + ":==>" + smsMessage.toString() + ",slot:" + slot);

                    Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show();
                    Intent startIntent = new Intent(context, MessageDetectMyService.class);
                    startIntent.putExtra("message", message);
                    context.startService(startIntent);
                    //abortBroadcast();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        } else if (intent.getAction().equals(AMAR_NOTICE)) {
            Log.d(TAG, getDeviceInfo(context));
            Toast.makeText(context, "call:" + AMAR_NOTICE + ":" + getDeviceInfo(context), Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(SMS_CHANGE)) {
            Toast.makeText(context, "call:" + SMS_CHANGE, Toast.LENGTH_SHORT).show();
        }
    }

    String getDeviceInfo(Context context) {
        String info = "";
        TelephonyManager telephoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerial = telephoneMgr.getSimSerialNumber();
        String network = telephoneMgr.getNetworkOperatorName();
        String deviceId = telephoneMgr.getDeviceId();
        // String count = telephoneMgr.getPhoneCount() + "";
        info = "simSerial:" + simSerial + ",network:" + network + ",deviceId:" + deviceId;
        info = ",line1number:" + telephoneMgr.getLine1Number();

        try {
            String device0 = telephoneMgr.getDeviceId(0);
            info = info + ",dev0=" + device0;
            String device1 = telephoneMgr.getDeviceId(1);
            info = info + ",dev1=" + device1;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return info;
    }
}
