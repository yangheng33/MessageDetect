package com.detect.amar.messagedetect.call;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.messagedetect.DoubleCheck;
import com.detect.amar.messagedetect.HttpService;
import com.detect.amar.messagedetect.log.ErrorLogUtil;
import com.detect.amar.messagedetect.model.StdResponse;
import com.detect.amar.messagedetect.setting.Setting;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;

public class CallTransService extends Service {
    public CallTransService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            CallInfo callInfo = intent.getParcelableExtra("callInfo");
            if (callInfo != null) {
                sendCallInfo(callInfo);
            }
        } catch (Exception e) {
            ErrorLogUtil.add("error in onStartCommand", e.getMessage());
        }
        DoubleCheck.checkService(this);
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendCallInfo(final CallInfo callInfo) {
        String toNumber = "" + callInfo.getSlot();
        if (callInfo.getSlot() == 1) {
            toNumber = PreferencesUtils.getString(Setting.SIM_1, "1");
        } else if (callInfo.getSlot() == 2) {
            toNumber = PreferencesUtils.getString(Setting.SIM_2, "2");
        }
        callInfo.setToNumber(toNumber);

        String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        HttpService service = restAdapter.create(HttpService.class);
        service.sendCallInfo(callInfo.toMap(), new Callback<StdResponse>() {
            @Override
            public void success(StdResponse stdResponse, retrofit.client.Response response2) {
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorLogUtil.add("send call error will resend", callInfo.toString() + "===>" + error.getMessage());
                resendCallInfo(callInfo);
            }
        });
    }

    private void resendCallInfo(final CallInfo callInfo) {

        Observable.timer(10, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ErrorLogUtil.add("error before resend call", callInfo.toString() + "===>" + e.getMessage());
            }

            @Override
            public void onNext(Long aLong) {
                String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
                HttpService service = restAdapter.create(HttpService.class);
                service.sendCallInfo(callInfo.toMap(), new Callback<StdResponse>() {
                    @Override
                    public void success(StdResponse stdResponse, retrofit.client.Response response2) {
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ErrorLogUtil.add("resend call error", callInfo.toString() + "===>" + error.getMessage());
                    }
                });
            }
        });
    }
}
