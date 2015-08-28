package com.detect.amar.common;

import android.app.Application;

/**
 * Created by SAM on 2015/8/28.
 */
public class SApplication extends Application {

    private static SApplication _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.setApplication(this);
        Thread.setDefaultUncaughtExceptionHandler(new UncatchException());
    }

    public static SApplication getInstance() {
        return _instance;
    }
}
