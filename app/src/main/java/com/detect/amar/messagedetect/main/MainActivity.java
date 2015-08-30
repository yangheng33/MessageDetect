package com.detect.amar.messagedetect.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.detect.amar.common.ResourcesUtil;
import com.detect.amar.common.ServiceUtils;
import com.detect.amar.messagedetect.CheckStatusService;
import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.setting.SettingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.SystemStatus)
    TextView systemStatusTxt;

    @Bind(R.id.startService)
    TextView startServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean result = ServiceUtils.isServiceRunning(CheckStatusService.class.getName(), MainActivity.this);
                subscriber.onNext(result);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Boolean s) {
                if (s) {
                    systemStatusTxt.setText(ResourcesUtil.getString(R.string.service_is_running));
                    startServiceBtn.setTextColor(ResourcesUtil.getColor(R.color.orangered));
                    startServiceBtn.setVisibility(View.GONE);
                } else {
                    systemStatusTxt.setText(ResourcesUtil.getString(R.string.service_is_not_running));
                    startServiceBtn.setVisibility(View.VISIBLE);
                    startServiceBtn.setTextColor(ResourcesUtil.getColor(R.color.black));
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.startService)
    void serviceStart() {
        Intent startIntent = new Intent(this, CheckStatusService.class);
        startService(startIntent);
        systemStatusTxt.setText(ResourcesUtil.getString(R.string.service_is_running));
    }

    @OnClick(R.id.gotoSystemSetting)
    void gotoSystemSetting() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }
}
