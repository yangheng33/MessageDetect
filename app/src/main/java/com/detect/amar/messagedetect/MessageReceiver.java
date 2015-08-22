package com.detect.amar.messagedetect;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {
    String TAG = "home";

    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        if (pdus == null || pdus.length == 0)
            return;
        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
            String sender = smsMessage.getDisplayOriginatingAddress();
            String receiver = smsMessage.getServiceCenterAddress();
            String content = smsMessage.getMessageBody();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(new Date(smsMessage.getTimestampMillis()));

            Message message = new Message(sender, receiver, content, time);
            Log.d(TAG, message.toString());
            Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show();
            Intent startIntent = new Intent(context, MessageDetectMyService.class);
            startIntent.putExtra("message", message);
            context.startService(startIntent);
            //abortBroadcast();
        }
    }
}
