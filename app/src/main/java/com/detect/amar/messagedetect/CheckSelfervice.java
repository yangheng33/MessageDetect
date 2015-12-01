package com.detect.amar.messagedetect;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.common.ServiceUtils;
import com.detect.amar.messagedetect.log.ErrorLogUtil;
import com.detect.amar.messagedetect.setting.Setting;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckSelfervice extends Service {
    public CheckSelfervice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCheck();
        return super.onStartCommand(intent, flags, startId);
    }

    void startCheck() {
        final int cycleFrequency = 60;//PreferencesUtils.getInt(Setting.Cycle_Frequency, Setting.Cycle_Frequency_Default);
        Observable.timer(cycleFrequency, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ErrorLogUtil.add("check self error", e.getMessage());
                startCheck();
            }

            @Override
            public void onNext(Long aLong) {
                DoubleCheck.checkService(CheckSelfervice.this);
                startCheck();
            }
        });
    }
}
