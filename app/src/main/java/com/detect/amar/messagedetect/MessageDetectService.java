package com.detect.amar.messagedetect;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.messagedetect.model.StdResponse;
import com.detect.amar.messagedetect.setting.Setting;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MessageDetectService extends Service {

    String TAG = "home";

    public MessageDetectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");

        final Message message = intent.getParcelableExtra("message");
        if (message != null) {
            String messageTo = message.getSimSlot() + "";
            if (message.getSimSlot() == 1)
                messageTo = PreferencesUtils.getString(Setting.SIM_1);
            else if (message.getSimSlot() == 2)
                messageTo = PreferencesUtils.getString(Setting.SIM_2);

            message.setToNumber(messageTo);

            String url = PreferencesUtils.getString(Setting.API_BASE_URL, "");
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
            SendMessageService service = restAdapter.create(SendMessageService.class);
            service.sendMessage(message.toMap(), new Callback<StdResponse>() {
                @Override
                public void success(StdResponse stdResponse, retrofit.client.Response response2) {

                    SharedPreferences mySharedPreferences = MessageDetectService.this.getSharedPreferences("MessageDetect", Activity.MODE_APPEND);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    message.setIsTrans(true);

                    editor.putString("message", message.toString());
                    editor.apply();
                    Log.d(TAG, "发送成功:" + message.toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    SharedPreferences mySharedPreferences = MessageDetectService.this.getSharedPreferences("MessageDetect", Activity.MODE_APPEND);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    message.setIsTrans(false);
                    editor.putString("message", message.toString());
                    editor.apply();
                    Log.d(TAG, "发送失败:" + message.toString() + "\n:原因,\n" + error.getMessage());
                }
            });


        }

        Log.d(TAG, "onStartCommand() will leave");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
