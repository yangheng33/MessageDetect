package com.detect.amar.messagedetect.log;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SAM on 2015/9/2.
 */
@DatabaseTable(tableName = "errorlog")
public class ErrorLog {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(dataType = DataType.LONG)
    private long date;
    @DatabaseField(dataType = DataType.STRING)
    private String log;
    @DatabaseField
    private String title;


    public ErrorLog() {
    }

    public ErrorLog(String title, String log) {
        this.date = System.currentTimeMillis();
        this.log = log;
        this.title = title;
    }

    public ErrorLog(long date, String log) {
        this.date = date;
        this.log = log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorLog{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", log='").append(log).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
