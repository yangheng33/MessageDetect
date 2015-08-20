package com.detect.amar.messagedetect;

import android.app.ActivityManager;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    String TAG = "home";

    @Bind(R.id.title)
    TextView titleTxt;

    @Bind(R.id.input)
    EditText inputEdit;

    @Bind(R.id.serviceStatus)
    EditText serviceStatusEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        titleTxt.setText("动态设置参数啦");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.serviceStart)
    void serviceStart() {
        Intent startIntent = new Intent(this, MessageDetectMyService.class);
        startService(startIntent);
        serviceStatusEdit.setText("已经启动");
    }

    @OnClick(R.id.serviceStop)
    void serviceStop() {
        if (isServiceRunning(MessageDetectMyService.class.getName())) {
            Intent stopIntent = new Intent(this, MessageDetectMyService.class);
            stopService(stopIntent);
            serviceStatusEdit.setText("已经停止");
        } else {
            serviceStatusEdit.setText("服务不存在");
        }
    }

    @OnClick(R.id.serviceQuery)
    void serviceQuery() {
        boolean isRunning = isServiceRunning(MessageDetectMyService.class.getName());
        if (isRunning)
            serviceStatusEdit.setText("is running");
        else
            serviceStatusEdit.setText("is out running");
    }

    private boolean isServiceRunning(String serviceName) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.title)
    void clickTitle() {
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }


    Subscriber<Long> rxTestSubscriber = null;

    @OnClick(R.id.rxAndroid)
    void testRxAndroid() {

        if (rxTestSubscriber != null && !rxTestSubscriber.isUnsubscribed()) {
            rxTestSubscriber.unsubscribe();
            rxTestSubscriber = null;
            inputEdit.setText("");
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
                    inputEdit.setText(aLong + "");
                }
            };
            Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(rxTestSubscriber);
        }
    }

}
