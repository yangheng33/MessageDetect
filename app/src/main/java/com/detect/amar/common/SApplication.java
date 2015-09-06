package com.detect.amar.common;

import android.app.Application;

import com.detect.amar.messagedetect.db.DataBaseManager;
import com.detect.amar.messagedetect.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by SAM on 2015/8/28.
 */
public class SApplication extends Application {

    private static SApplication _instance;

    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.setApplication(this);
        ResourcesUtil.setApplication(this);
        DataBaseManager.init(this);
        Thread.setDefaultUncaughtExceptionHandler(new UncatchException());
    }

    public static SApplication getInstance() {
        return _instance;
    }
}
