package com.detect.amar.messagedetect.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.detect.amar.messagedetect.Message;
import com.detect.amar.messagedetect.log.ErrorLog;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by SAM on 2015/9/2.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "home";
    // 数据库名称
    private static final String DATABASE_NAME = "Message.db";
    // 数据库version
    private static final int DATABASE_VERSION = 2;

    private Dao<Message, Integer> messageDAO;
    private Dao<ErrorLog, Integer> errorLogDAO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // 可以用配置文件来生成 数据表，有点繁琐，不喜欢用
        // super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //建表
            TableUtils.createTable(connectionSource, Message.class);
            TableUtils.createTable(connectionSource, ErrorLog.class);

            //初始化DAO
            messageDAO = getMessageDAO();
            errorLogDAO = getErrorLogDAO();
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Message.class, true);
            TableUtils.dropTable(connectionSource, ErrorLog.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Message, Integer> getMessageDAO() throws SQLException {
        if (messageDAO == null) {
            messageDAO = getDao(Message.class);
        }
        return messageDAO;
    }

    public Dao<ErrorLog, Integer> getErrorLogDAO() throws SQLException {
        if (errorLogDAO == null) {
            errorLogDAO = getDao(ErrorLog.class);
        }
        return errorLogDAO;
    }

    @Override
    public void close() {
        super.close();
        messageDAO = null;
        errorLogDAO = null;
    }
}
