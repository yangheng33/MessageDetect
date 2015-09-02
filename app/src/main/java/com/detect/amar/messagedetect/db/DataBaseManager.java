package com.detect.amar.messagedetect.db;

import android.content.Context;

/**
 * Created by SAM on 2015/9/2.
 */
public class DataBaseManager {

    /**
     * 全局对象
     */
    private static DatabaseHelper mDatabaseHelper;
    private static Context context;

    private DataBaseManager() {
    }

    /**
     * @param context application context
     */
    public static void init(Context context) {
        DataBaseManager.context = context;
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public static DatabaseHelper getHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }
}
