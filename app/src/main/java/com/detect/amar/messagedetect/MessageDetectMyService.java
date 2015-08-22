package com.detect.amar.messagedetect;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
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

        final Message message = intent.getParcelableExtra("message");
        if (message != null) {

            String url = "http://192.168.254.102:8080/simple/";
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
            SendMessageService service = restAdapter.create(SendMessageService.class);
            service.sendMessage(message.toMap(), new Callback<Response>() {
                @Override
                public void success(Response response, retrofit.client.Response response2) {
                    SharedPreferences mySharedPreferences = MessageDetectMyService.this.getSharedPreferences("MessageDetect", Activity.MODE_APPEND);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    message.setIsTrans(true);

                    editor.putString("message", message.toString());
                    editor.apply();
                    Log.d(TAG, "发送成功:"+message.toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    SharedPreferences mySharedPreferences = MessageDetectMyService.this.getSharedPreferences("MessageDetect", Activity.MODE_APPEND);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    message.setIsTrans(false);
                    editor.putString("message", message.toString());
                    editor.apply();
                    Log.d(TAG, "发送失败:"+message.toString()+"\n:原因,\n"+error.getMessage());
                }
            });


        }

        Log.d(TAG, "onStartCommand() will leave");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
