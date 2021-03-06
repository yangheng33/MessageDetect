package com.detect.amar.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.detect.amar.messagedetect.log.ErrorLogUtil;
import com.detect.amar.messagedetect.main.MainActivity;

/**
 * Created by Administrator on 2015/11/03.
 */
public class RestartException implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static final String TAG = "RestartException";
    SApplication application;

    public RestartException(SApplication application) {
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.application = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        String error = (ex != null && ex.getMessage() != null) ? ex.getMessage() : "i don't know error cause.";
        ErrorLogUtil.add("fatal error", "error:" + error);
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            String currentActivityName = application.getCurrentActivity() == null ? "do not find" : application.getCurrentActivity().getLocalClassName();
            String homeActivityName = MainActivity.class.getName();
            if (homeActivityName.endsWith(currentActivityName)) {
                application.exitApplition();
                return;//主页出错就不重启了，要不就成死循环了
            } else {
                Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
                PendingIntent restartIntent = PendingIntent.getActivity(application.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 3000, restartIntent); // N秒钟后重启应用
            }
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(application.getApplicationContext(), "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        return true;
    }
}