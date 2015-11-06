package com.detect.amar.messagedetect;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.messagedetect.log.ErrorLogUtil;
import com.detect.amar.messagedetect.model.StdResponse;
import com.detect.amar.messagedetect.setting.Setting;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;

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
        try {
            Message message = intent.getParcelableExtra("message");
            if (message != null) {
                sendMessage(message);
            }
        }catch (Exception e)
        {
            ErrorLogUtil.add("error in onStartCommand", e.getMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendMessage(final Message message) {
        String messageTo = message.getSimSlot() + "";
        boolean allowTrans = true;
        if (message.getSimSlot() == 1) {
            messageTo = PreferencesUtils.getString(Setting.SIM_1, "1");
            //allowTrans = PreferencesUtils.getBoolean(Setting.Sim_Status_1_Is_Allow, true);
        } else if (message.getSimSlot() == 2) {
            messageTo = PreferencesUtils.getString(Setting.SIM_2, "2");
            //allowTrans = PreferencesUtils.getBoolean(Setting.Sim_Status_2_Is_Allow, true);
        }
        message.setToNumber(messageTo);

        String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        HttpService service = restAdapter.create(HttpService.class);
        service.sendMessage(message.toMap(), new Callback<StdResponse>() {
            @Override
            public void success(StdResponse stdResponse, retrofit.client.Response response2) {
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorLogUtil.add("send msg error will resend", message.getInfo() + "===>" + error.getMessage());
                resendMessage(message);
            }
        });
    }
    private void resendMessage(final Message message) {

        Observable.timer(10, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ErrorLogUtil.add("error before resend", message.getInfo() + "===>" + e.getMessage());
            }

            @Override
            public void onNext(Long aLong) {
                String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
                HttpService service = restAdapter.create(HttpService.class);
                service.sendMessage(message.toMap(), new Callback<StdResponse>() {
                    @Override
                    public void success(StdResponse stdResponse, retrofit.client.Response response2) {
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ErrorLogUtil.add("resend msg error", message.getInfo() + "===>" + error.getMessage());
                    }
                });
            }
        });


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
