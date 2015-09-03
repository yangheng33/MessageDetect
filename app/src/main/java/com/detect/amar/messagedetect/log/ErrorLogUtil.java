package com.detect.amar.messagedetect.log;

import com.detect.amar.messagedetect.db.DataBaseManager;

import java.sql.SQLException;

/**
 * Created by SAM on 2015/9/3.
 */
public class ErrorLogUtil {

    public static void add(String title, String detail) {
        ErrorLog errorLog = new ErrorLog(title, detail);
        try {
            DataBaseManager.getHelper().getErrorLogDAO().create(errorLog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
