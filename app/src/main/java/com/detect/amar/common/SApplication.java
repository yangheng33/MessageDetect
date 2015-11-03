package com.detect.amar.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.detect.amar.messagedetect.db.DataBaseManager;
import com.detect.amar.messagedetect.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 2015/8/28.
 */
public class SApplication extends Application implements android.app.Application.ActivityLifecycleCallbacks{

    private static SApplication _instance;

    private DatabaseHelper databaseHelper;
    private List<Activity> activitieslist = null;
    private Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        activitieslist = new ArrayList<>();

        PreferencesUtils.setApplication(this);
        ResourcesUtil.setApplication(this);
        DataBaseManager.init(this);
        //Thread.setDefaultUncaughtExceptionHandler(new UncatchException());
        Thread.setDefaultUncaughtExceptionHandler(new RestartException(this));
        registerActivityLifecycleCallbacks(this);
    }


    public Activity getCurrentActivity(){
        return currentActivity;
    }
    public void onActivityCreated(Activity activity, Bundle savedInstanceState){

    }
    public void onActivityStarted(Activity activity){
        currentActivity = activity;
        activitieslist.add(activity);
    }
    public void onActivityResumed(Activity activity){

    }
    public void onActivityPaused(Activity activity){

    }
    public void onActivityStopped(Activity activity){

    }
    public void onActivitySaveInstanceState(Activity activity, Bundle outState){

    }
    public void onActivityDestroyed(Activity activity){
        activitieslist.remove(activity);
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void exitApplition(){
        for (Activity activity : activitieslist) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static SApplication getInstance() {
        return _instance;
    }
}
