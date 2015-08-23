package com.detect.amar.messagedetect;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.detect.amar.messagedetect.common.DatetimeUtil;

public class MessageReceiver extends BroadcastReceiver {
    String TAG = "home";
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String AMAR_NOTICE = "amar.android.notice";

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
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                String sender = smsMessage.getDisplayOriginatingAddress();
                String receiver = smsMessage.getServiceCenterAddress();
                String content = smsMessage.getMessageBody();
                String time = DatetimeUtil.longToDatetime(smsMessage.getTimestampMillis());

                Message message = new Message(sender, receiver, content, time, DatetimeUtil.longToDatetime(new Date().getTime()));
                Log.d(TAG, message.toString());
                Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, MessageDetectMyService.class);
                startIntent.putExtra("message", message);
                context.startService(startIntent);
                //abortBroadcast();
            }
        } else if (intent.getAction().equals(AMAR_NOTICE)) {

            Toast.makeText(context, "call:" + AMAR_NOTICE, Toast.LENGTH_SHORT).show();
        }
    }
}
