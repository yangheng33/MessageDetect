package com.detect.amar.messagedetect.setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SAM on 2015/8/24.
 */
public class Setting {

    public static final String API_BASE_URL = "API_BASE_URL";
    public static final String SIM_1 = "SIM_1";
    public static final String SIM_2 = "SIM_2";
    public static final String PreferenceName = "MessageDetect";

    private Context _context;

    public Setting(Context context) {
        this._context = context;
    }

    public SharedPreferences getPreference() {
        return _context.getSharedPreferences(PreferenceName, Activity.MODE_PRIVATE);
    }

    public String getApiUrl() {
        return getPreference().getString(API_BASE_URL, "");
    }

    public void setApiUrl(String apiUrl) {
        getPreference().edit().putString(API_BASE_URL, apiUrl).apply();
    }

    public String getSim1() {
        return getPreference().getString(SIM_1, "");
    }

    public void setSim1(String sim1) {
        getPreference().edit().putString(SIM_1, sim1).apply();
    }

    public String getSim2() {
        return getPreference().getString(SIM_2, "");
    }

    public void setSim2(String sim2) {
        getPreference().edit().putString(SIM_2, sim2).apply();
    }
}
