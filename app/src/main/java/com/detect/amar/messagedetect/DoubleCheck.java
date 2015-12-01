package com.detect.amar.messagedetect;

import android.content.Context;
import android.content.Intent;

import com.detect.amar.common.ServiceUtils;

/**
 * Created by Administrator on 2015/11/30.
 */
public class DoubleCheck {

    public static void checkService(Context context)
    {
        if (!ServiceUtils.isServiceRunning(CheckStatusService.class.getName(), context)) {
            Intent intent = new Intent(context,CheckStatusService.class);
            context.startService(intent);
        }

        if (!ServiceUtils.isServiceRunning( CheckSelfervice.class.getName(),context)) {
            Intent intent = new Intent(context,CheckSelfervice.class);
            context.startService(intent);
        }
    }
}
