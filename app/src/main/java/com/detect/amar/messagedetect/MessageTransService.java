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

public class MessageTransService extends Service {

    String TAG = "home";

    public MessageTransService() {
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
            boolean allowTrans = true;
            if (message.getSimSlot() == 1) {
                messageTo = PreferencesUtils.getString(Setting.SIM_1);
                allowTrans = PreferencesUtils.getBoolean(Setting.Sim_Status_1_Is_Allow, true);
            } else if (message.getSimSlot() == 2) {
                messageTo = PreferencesUtils.getString(Setting.SIM_2);
                allowTrans = PreferencesUtils.getBoolean(Setting.Sim_Status_2_Is_Allow, true);
            }
            if (allowTrans) {
                message.setToNumber(messageTo);
                String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
                HttpService service = restAdapter.create(HttpService.class);
                service.sendMessage(message.toMap(), new Callback<StdResponse>() {
                    @Override
                    public void success(StdResponse stdResponse, retrofit.client.Response response2) {
                        Log.d(TAG, "发送成功:" + message.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "发送失败:" + message.toString() + "\n:原因,\n" + error.getMessage());
                    }
                });
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
