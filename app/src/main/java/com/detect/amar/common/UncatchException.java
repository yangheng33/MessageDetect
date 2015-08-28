package com.detect.amar.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Looper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by SAM on 2015/8/28.
 */
public class UncatchException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        final String crashReport = getCrashReport(ex);
        saveErrorLog(crashReport);
//        new Thread() {
//            public void run() {
//                Looper.prepare();
//                saveErrorLog(crashReport);
//                Looper.loop();
//            }
//        }.start();
        return true;
    }

    public void saveErrorLog(String crashReport) {
        String errorLog = "message_error.txt";
        String savePath = "";
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            //判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MessageLog/";
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                logFilePath = savePath + errorLog;
            }
            if (logFilePath.equals("")) {
                return;
            }
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);

            //fw.write(crashReport);
            pw.write(crashReport);

            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * 获取APP崩溃异常报告
     *
     * @param ex
     * @return
     */
    private String getCrashReport(Throwable ex) {
        StringBuilder exceptionStr = new StringBuilder();
        exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.MODEL + ")\n");
        exceptionStr.append("datetime: " + DatetimeUtil.longToDatetime(System.currentTimeMillis()) + "\n");
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");

        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        return exceptionStr.toString();
    }
}
