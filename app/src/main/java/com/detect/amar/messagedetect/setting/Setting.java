package com.detect.amar.messagedetect.setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.detect.amar.common.PreferencesUtils;

/**
 * Created by SAM on 2015/8/24.
 */
public class Setting {

    public static final String API_BASE_URL = "API_BASE_URL";
    public static final String SIM_1 = "SIM_1";
    public static final String SIM_2 = "SIM_2";
    public static final String Cycle_Frequency = "Cycle_Frequency";

    private Context _context;

    public Setting(Context context) {
        this._context = context;
    }

    public void setCycleFrequency(int cycleFrequency) {
        PreferencesUtils.putInt(Cycle_Frequency, cycleFrequency);
    }

    /**
     * @return
     */
    public int getCycleFrequency() {
        return PreferencesUtils.getInt(Cycle_Frequency, 300);
    }

    public String getApiUrl() {
        return PreferencesUtils.getString(API_BASE_URL, "");
    }

    public void setApiUrl(String apiUrl) {
        PreferencesUtils.putString(API_BASE_URL, apiUrl);
    }

    public String getSim1() {
        return PreferencesUtils.getString(SIM_1, "");
    }

    public void setSim1(String sim1) {
        PreferencesUtils.putString(SIM_1, sim1);
    }

    public String getSim2() {
        return PreferencesUtils.getString(SIM_2, "");
    }

    public void setSim2(String sim2) {
        PreferencesUtils.putString(SIM_2, sim2);
    }
}
