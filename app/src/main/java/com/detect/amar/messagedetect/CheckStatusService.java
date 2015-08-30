package com.detect.amar.messagedetect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.detect.amar.common.PhoneUtil;
import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.messagedetect.model.CheckResponse;
import com.detect.amar.messagedetect.model.StdResponse;
import com.detect.amar.messagedetect.setting.Setting;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckStatusService extends Service {

    String Tag = "home";

    public CheckStatusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    boolean isRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isRunning) {
            isRunning = true;
            startCheck();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    void startCheck() {
        final int cycleFrequency = PreferencesUtils.getInt(Setting.Cycle_Frequency, Setting.Cycle_Frequency_Default);
        Observable.timer(cycleFrequency, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(Tag, "in the startCheck_error" + e.getMessage());
                startCheck();
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(Tag, "in the startCheck:" + cycleFrequency + "==>" + System.currentTimeMillis() / 1000);
                startCheck();
            }
        });
    }

    public void setGetStatus() {
        Map<String, String> paramMap = new WeakHashMap<>();
        paramMap.put("device_no", PhoneUtil.getDeviceNo(this));

        String url = PreferencesUtils.getString(Setting.API_BASE_URL, "");
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        SendMessageService service = restAdapter.create(SendMessageService.class);

        service.getSimCardStatus(paramMap, new Callback<CheckResponse>() {
            @Override
            public void success(CheckResponse checkResponse, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}