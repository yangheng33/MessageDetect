package com.detect.amar.messagedetect;

import android.app.Service;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.common.PackageUtil;
import com.detect.amar.common.PhoneUtil;
import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.messagedetect.log.ErrorLogUtil;
import com.detect.amar.messagedetect.model.CheckResponse;
import com.detect.amar.messagedetect.model.StdResponse;
import com.detect.amar.messagedetect.setting.Setting;
import com.detect.amar.messagedetect.version.VersionActivity;

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
                ErrorLogUtil.add("cycle", e.getMessage());
                startCheck();
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(Tag, "in the startCheck:" + cycleFrequency + "==>" + System.currentTimeMillis() / 1000);
               // if (PreferencesUtils.getBoolean(Setting.Is_Initiated, false)) { //初始化过才进行监听
                    setStatus();
                //}
                startCheck();
            }
        });
    }

    public void setStatus() {
        Map<String, String> paramMap = new WeakHashMap<>();
        paramMap.put("device_no", PhoneUtil.getDeviceNo(this));
        paramMap.put("battery_percentage", PreferencesUtils.getString(Setting.Current_Battery, "0"));
        paramMap.put("battery_time", PreferencesUtils.getString(Setting.Battery_Status_Time, DatetimeUtil.getDurrentDatetime()));
        paramMap.put("battery_status", PreferencesUtils.getInt(Setting.Battery_Status, BatteryManager.BATTERY_STATUS_UNKNOWN) + "");

        String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        HttpService service = restAdapter.create(HttpService.class);

        service.getSimCardStatus(paramMap, new Callback<StdResponse<CheckResponse>>() {
            @Override
            public void success(StdResponse<CheckResponse> stdResponse, Response response) {
                Log.d(Tag, "success:" + stdResponse.toString());
                try {
                    boolean sim_1_status = !(stdResponse.getInfo().getStatus_sim_1() == null || "".equals(stdResponse.getInfo().getStatus_sim_1()) || "0".equals(stdResponse.getInfo().getStatus_sim_1()));
                    boolean sim_2_status = !(stdResponse.getInfo().getStatus_sim_2() == null || "".equals(stdResponse.getInfo().getStatus_sim_2()) || "0".equals(stdResponse.getInfo().getStatus_sim_2()));
                    PreferencesUtils.putInt(Setting.Cycle_Frequency, stdResponse.getInfo().getCycle_frequency());
                    PreferencesUtils.putBoolean(Setting.Sim_Status_1_Is_Allow, sim_1_status);
                    PreferencesUtils.putBoolean(Setting.Sim_Status_2_Is_Allow, sim_2_status);

                    checkUpdate(stdResponse.getInfo());
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorLogUtil.add("get Status success ,but", e.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(Tag, "error:" + error.getMessage());
                ErrorLogUtil.add("get Status failure", error.getMessage());
            }
        });
    }

    public void checkUpdate(CheckResponse checkResponse) {
        Object[] packageInfo = PackageUtil.getAppVersionInfo(CheckStatusService.this);
        if (Integer.parseInt(packageInfo[1].toString()) < checkResponse.getVersionCode()) {
            Intent intent = new Intent(CheckStatusService.this, VersionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putParcelable(VersionActivity.PARAM, checkResponse.toVersionModel());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}