package com.detect.amar.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SAM on 2015/8/23.
 */
public class DatetimeUtil {

    private static SimpleDateFormat _simpleDateFormat;

    private static SimpleDateFormat getSimpleDateFormat() {
        if (_simpleDateFormat == null) {
            _simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
            _simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        }
        return _simpleDateFormat;
    }

    /**
     *
     * @param longDate
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String longToDatetime(long longDate) {

        return getSimpleDateFormat().format(new Date(longDate));
    }

    /**
     * @param datetime yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long datetimeToLong(String datetime) {
        long longDate = 0L;
        try {
            longDate = getSimpleDateFormat().parse(datetime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longDate;
    }
}
