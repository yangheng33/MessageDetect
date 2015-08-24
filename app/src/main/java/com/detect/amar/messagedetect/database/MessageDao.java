package com.detect.amar.messagedetect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.detect.amar.messagedetect.Message;
import com.detect.amar.common.DatetimeUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amar on 15/8/23.
 */
public class MessageDao extends SQLiteOpenHelper {

    public static final String Datebase_Name = "message.db";
    public static final String Table_Name = "message";
    public static final int Version = 1;
    public static String Tag = "messageDao";

    /**
     * String sql = "create table {0} ( " +
     * "id integer primary key autoincrement," + //主键，无意义
     * "fromnumber text not null, " + //谁发的短信
     * "tonumber text not null," + //接收号码
     * "info text not null," + //短信内容
     * "origindate text nout null,"+ //短信的原始时间
     * "transstatus integer not null," + //转发状态1成功，0不成功
     * "receivedate integer not null," + //接收短信的时间
     * "lastsenddate integer," + //发送成功的时间
     * "transfail text"           //发送失败的原因（失败过才会有值）
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
                "origindate text nout null," +
                "transstatus integer not null," +
                "receivedate integer not null," +
                "lastsenddate integer," +
                "transfail text);";

        return MessageFormat.format(sql, Table_Name);
    }

    public MessageDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public MessageDao(Context context) {
        super(context, Datebase_Name, null, 1, null);
    }

    public boolean insert(Message message) {
        ContentValues values = new ContentValues();
        values.put("fromnumber", message.getFromNumber());
        values.put("tonumber", message.getToNumber());
        values.put("info", message.getInfo());
        values.put("transstatus", message.isTrans() ? 1 + "" : 0 + "");
        values.put("origindate", DatetimeUtil.datetimeToLong(message.getOrigindate()));
        values.put("receivedate", DatetimeUtil.datetimeToLong(message.getReceivedate()));
        values.put("lastsenddate", message.getLastsenddate() != null && !"".equals(message.getLastsenddate()) ? DatetimeUtil.datetimeToLong(message.getLastsenddate()) + "" : "");
        values.put("transfail", message.getTransfail() == null ? "" : message.getTransfail());
        long returnInfo = getReadableDatabase().insert(Table_Name, null, values);
        getWritableDatabase().close();
        return returnInfo != -1;
    }

    public List<Message> findMessage(Message message) {
        List<Message> messageList = new ArrayList<>();

        try {
            String[] columns = new String[]{"id", "fromnumber", "tonumber", "info", "transstatus", "origindate", "receivedate", "lastsenddate", "transfail"};
            String groupBy = null;
            String having = null;
            String orderBy = "id desc";
            String limit = "10";
            //String selection = "id>? and name<>?";
            // String[] selectionArgs = new String[]{"1",""};
//        Cursor c = getReadableDatabase().query(Table_Name,columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            Cursor cursor = getReadableDatabase().query(Table_Name, columns, null, null, null, null, orderBy, limit);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String id = cursor.getString(0);
                String fromNumber = cursor.getString(1);
                String toNumber = cursor.getString(2);
                String info = cursor.getString(3);
                boolean transstatus = !(null == cursor.getString(4) || "".equals(cursor.getString(4)) || "0".equals(cursor.getString(4)));
                String origindate = dbIntegerToDateString(cursor.getString(5));
                String receivedate = dbIntegerToDateString(cursor.getString(6));
                String lastsenddate = dbIntegerToDateString(cursor.getString(7));
                String transfail = cursor.getString(8);
                //public Message( long id,String fromNumber,String toNumber, String info, String origindate, boolean isTrans,  String lastsenddate, String transfail, String receivedate)
                Message messageItem = new Message(id, fromNumber, toNumber, info,origindate,transstatus,lastsenddate,transfail,receivedate);
                messageList.add(messageItem);
                cursor.moveToNext();
            }
            cursor.close();
            getReadableDatabase().close();
        } catch (Exception e){
            Log.e("db",e.getMessage());
        }
        return messageList;
    }

    private String dbIntegerToDateString(String IntegerString) {
        String date = "";
        try {
            if (IntegerString != null && !"".equals(IntegerString)) {
                date = DatetimeUtil.longToDatetime(Long.parseLong(IntegerString));
            }
        } catch (NumberFormatException e) {
            Log.e("db",e.getMessage());
        }
        return date;
    }

    /**
     * 根据ID进行更新
     *
     * @param message
     */
    public boolean update(Message message) {
        int resentcount = 0;
        ContentValues values = new ContentValues();
        values.put("fromnumber", message.getFromNumber());
        values.put("tonumber", message.getToNumber());
        values.put("info", message.getInfo());
        values.put("transstatus", message.isTrans() ? 1 + "" : 0 + "");
        values.put("origindate", DatetimeUtil.datetimeToLong(message.getOrigindate()));
        values.put("receivedate", DatetimeUtil.datetimeToLong(message.getReceivedate()));
        values.put("lastsenddate", message.isTrans() ? DatetimeUtil.longToDatetime(new Date().getTime()) : "");
        values.put("transfail", message.getTransfail() == null ? "" : message.getTransfail());
        int count = getReadableDatabase().update(Table_Name, values, "id=?", new String[]{message.getId() + ""});
        getWritableDatabase().close();
        return count > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = getCreateTableSQL();
        sqlLog(sql);
        db.execSQL(sql);
    }

    public void sqlLog(String info) {
        Log.d(Tag, "sql:" + info);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = MessageFormat.format("drop table if exists {0};", Table_Name);
        sqlLog(sql);
        db.execSQL(sql);
        onCreate(db);
    }
}
