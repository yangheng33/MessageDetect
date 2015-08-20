package com.detect.amar.messagedetect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageDetectMyService extends Service {

    String TAG = "home";

    public MessageDetectMyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");

        testRxAndroid();

        Log.d(TAG, "onStartCommand() will leave");
        return super.onStartCommand(intent, flags, startId);
    }

    Subscriber<Long> rxTestSubscriber = null;

    void testRxAndroid() {
        if (rxTestSubscriber != null && !rxTestSubscriber.isUnsubscribed()) {
            rxTestSubscriber.unsubscribe();
            rxTestSubscriber = null;
            Log.d(TAG,":::即将停止");
        } else {
            rxTestSubscriber = new Subscriber<Long>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Long aLong) {
                    Log.d(TAG,":::"+aLong);
                }
            };
            Observable.interval(10, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(rxTestSubscriber);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");

        if (rxTestSubscriber != null && !rxTestSubscriber.isUnsubscribed()) {
            rxTestSubscriber.unsubscribe();
            rxTestSubscriber = null;
            Log.d(TAG,":::即将停止");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
