package com.detect.amar.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SAM on 2015/8/23.
 */
public class DatetimeUtil {

    private static SimpleDateFormat _simpleDateTimeFormat;
    private static SimpleDateFormat _simpleDateFormat;
    public static String DayStart = "00:00:00";
    public static String DayEnd = "23:59:59";


    private static SimpleDateFormat getSimpleDatetimeFormat() {
        if (_simpleDateTimeFormat == null) {
            _simpleDateTimeFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
            _simpleDateTimeFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        }
        return _simpleDateTimeFormat;
    }

    private static SimpleDateFormat getSimpleDateFormat() {
        if (_simpleDateFormat == null) {
            _simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
            _simpleDateFormat.applyPattern("yyyy-MM-dd");
        }
        return _simpleDateFormat;
    }

    /**
     * @param longDate
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String longToDatetime(long longDate) {

        return getSimpleDatetimeFormat().format(new Date(longDate));
    }

    /**
     * @param datetime yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long datetimeToLong(String datetime) {
        long longDate = 0L;
        try {
            longDate = getSimpleDatetimeFormat().parse(datetime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longDate;
    }

    public static String getDurrentDatetime() {
        return getSimpleDatetimeFormat().format(System.currentTimeMillis());
    }

    public static String getDurrentDate() {
        return getSimpleDateFormat().format(System.currentTimeMillis());
    }

    /**
     * @param datetime yyyy-MM-dd
     * @return
     */
    public static long dateToLong(String datetime) {
        long longDate = 0L;
        try {
            longDate = getSimpleDateFormat().parse(datetime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longDate;
    }
}
