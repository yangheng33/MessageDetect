package com.detect.amar.messagedetect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.detect.amar.messagedetect.log.ErrorLogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by amar on 15/11/9.
 */
public class AmarTestService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         Toast.makeText(AmarTestService.this, "success1", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(30000);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                Toast.makeText(AmarTestService.this, "success2", Toast.LENGTH_SHORT).show();
        //startCheck();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    void startCheck() {
        Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(AmarTestService.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Long aLong) {

                Toast.makeText(AmarTestService.this, "success1", Toast.LENGTH_SHORT).show();

                try {
                    Thread.sleep(30000);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
//                Observable.timer(10,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        Toast.makeText(AmarTestService.this, "success3", Toast.LENGTH_SHORT).show();
//                    }
//
//                    });
                Toast.makeText(AmarTestService.this, "success2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
