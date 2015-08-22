package com.detect.amar.messagedetect.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.MessageFormat;

/**
 * Created by amar on 15/8/23.
 */
public class MessageDao extends SQLiteOpenHelper {

    public static final String Datebase_Name = "message.db";
    public static final String Table_Name = "message";
    public static final int Version = 1;
    public static String Tag = "messageDao";

    public String getCreateDatabaseSQL() {
        String sql = "";
        return sql;
    }

    /**
     * String sql = "create table {0} ( " +
     * "id integer primary key autoincrement," + //主键，无意义
     * "fromnumber text not null, " + //谁发的短信
     * "tonumber text not null," + //接收号码
     * "info text not null," + //短信内容
     * "transstatus integer not null," + //转发状态1成功，0不成功
     * "receivedate integer not null," + //接收短信的时间
     * "lastsenddate integer," + //发送成功的时间
     * "resentcount integer,     //重发发送的次数（发送失败过才会有值）
     * "transfail text"           //发送失败的原因（最后一次发送失败过才会有值）
     * );";
     *
     * @return
     */
    public String getCreateTableSQL() {
        String sql = "create table {0} ( " +
                "id integer primary key autoincrement," +
                "fromnumber text not null, " +
                "tonumber text not null," +
                "info text not null," +
                "transstatus integer not null," +
                "receivedate integer not null," +
                "lastsenddate integer," +
                "resentcount integer," +
                "transfail text);";

        return MessageFormat.format(sql, Table_Name);
    }

    public MessageDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = getCreateDatabaseSQL();
        sqlLog(sql);
        db.execSQL(sql);
    }

    public void sqlLog(String info)
    {
        Log.d(Tag,"sql:"+info);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql =  MessageFormat.format( "drop table if exists {0};", Table_Name);
        sqlLog(sql);
        db.execSQL(sql);
        onCreate(db);
    }


}
