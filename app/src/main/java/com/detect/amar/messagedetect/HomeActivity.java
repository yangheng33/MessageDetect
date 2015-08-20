package com.detect.amar.messagedetect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Bind(R.id.title)
    TextView titleTxt;

    @Bind(R.id.input)
    EditText inputEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        titleTxt.setText("动态设置参数啦");
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
