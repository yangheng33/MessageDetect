package com.detect.amar.messagedetect.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.detect.amar.common.ResourcesUtil;
import com.detect.amar.common.ServiceUtils;
import com.detect.amar.messagedetect.BatteryReceiver;
import com.detect.amar.messagedetect.CheckSelfervice;
import com.detect.amar.messagedetect.CheckStatusService;
import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.db.DataBaseManager;
import com.detect.amar.messagedetect.log.ErrorLog;
import com.detect.amar.messagedetect.log.ErrorLogActivity;
import com.detect.amar.messagedetect.setting.SettingActivity;
import com.detect.amar.messagedetect.version.VersionActivity;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    void test() {
        try {
            ErrorLog errorLog = new ErrorLog("in mainactivity", "sssss");
            DataBaseManager.getHelper().getErrorLogDAO().create(errorLog);

            Log.d("home", errorLog.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不知为何 setServiceStartStatus(boolean isStarted)  中的  startServiceBtn.performClick() 偶尔会不执行，
     * 怀疑是程序没启动完毕，再没找到原因之前先给5秒时间检测
     * todo
     */
    private void checkOnce() {
        Observable.timer(5, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                initUI();
            }
        });
    }

    BatteryReceiver batteryReceiver = null;

    private void startBatteryReceiver() {
        //if (batteryReceiver == null) {
            batteryReceiver = new BatteryReceiver();
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            //intentFilter.setPriority(18);
            registerReceiver(new BatteryReceiver(), intentFilter);
        //}
    }

    private void initUI() {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean statusResult = ServiceUtils.isServiceRunning(CheckStatusService.class.getName(), MainActivity.this);
                boolean checkSelfResult = ServiceUtils.isServiceRunning(CheckSelfervice.class.getName(), MainActivity.this);
                subscriber.onNext(statusResult&&checkSelfResult);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Boolean isStarted) {
                setServiceStartStatus(isStarted);
            }
        });
    }

    public void setServiceStartStatus(boolean isStarted) {
        if (isStarted) {
            systemStatusTxt.setText(ResourcesUtil.getString(R.string.service_is_running));
            systemStatusTxt.setTextColor(ResourcesUtil.getColor(R.color.black));
            startServiceBtn.setVisibility(View.GONE);
        } else {
            systemStatusTxt.setText(ResourcesUtil.getString(R.string.service_is_not_running));
            systemStatusTxt.setTextColor(ResourcesUtil.getColor(R.color.orangered));
            startServiceBtn.setVisibility(View.VISIBLE);
            startServiceBtn.performClick();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBatteryReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (batteryReceiver != null) {
//            unregisterReceiver(batteryReceiver);
//            batteryReceiver = null;
//        }
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

        Intent checkSelfIntent = new Intent(this, CheckSelfervice.class);
        startService(checkSelfIntent);
        systemStatusTxt.setText(ResourcesUtil.getString(R.string.service_is_starting));
        initUI();
    }

    @OnClick(R.id.gotoSystemSetting)
    void gotoSystemSetting() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.gotoErrorLog)
    void gotoErrorLog() {
        Intent intent = new Intent(MainActivity.this, ErrorLogActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.gotoVersion)
    void gotoVersion() {
        Intent intent = new Intent(MainActivity.this, VersionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
