package com.detect.amar.messagedetect.setting;

import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.detect.amar.common.PhoneUtil;
import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.common.ResourcesUtil;
import com.detect.amar.messagedetect.HttpService;
import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.model.StdResponse;

import java.util.WeakHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.setting_api_url)
    EditText apiUrlEdit;
    @Bind(R.id.setting_cycle_frequency)
    EditText cycleFrequencyEdit;
    @Bind(R.id.setting_sim_1)
    EditText sim1Edit;
    @Bind(R.id.setting_sim_2)
    EditText sim2Edit;

    @Bind(R.id.setting_dev_no)
    EditText devNoEdit;

    @Bind(R.id.currentBattery)
    EditText currentBatteryEdit;

    Setting setting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setting = new Setting(this);
        initUI(setting);
    }

    void initUI(Setting setting) {
        apiUrlEdit.setText(setting.getApiUrl());
        cycleFrequencyEdit.setText(setting.getCycleFrequency() + "");
        sim1Edit.setText(setting.getSim1());
        sim2Edit.setText(setting.getSim2());
        devNoEdit.setText(PhoneUtil.getDeviceNo(this));

        String batteryChargeStatus = PhoneUtil.getBatteryChargeStatusDescription(PreferencesUtils.getInt(Setting.Battery_Status, BatteryManager.BATTERY_STATUS_UNKNOWN));
        currentBatteryEdit.setText(setting.getCurrentBattery() + "% " + batteryChargeStatus);

        if (!PreferencesUtils.getBoolean(Setting.Sim_Status_1_Is_Allow, true)) {
            sim1Edit.setTextColor(ResourcesUtil.getColor(R.color.red));
        } else {
            sim1Edit.setTextColor(ResourcesUtil.getColor(R.color.black));
        }

        if (!PreferencesUtils.getBoolean(Setting.Sim_Status_2_Is_Allow, true)) {
            sim2Edit.setTextColor(ResourcesUtil.getColor(R.color.red));
        } else {
            sim2Edit.setTextColor(ResourcesUtil.getColor(R.color.black));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.setting_submit)
    void clickSubmit() {
        setting.setApiUrl(apiUrlEdit.getText().toString());
        setting.setSim1(sim1Edit.getText().toString());
        setting.setSim2(sim2Edit.getText().toString());
        setting.setCycleFrequency(Integer.parseInt(cycleFrequencyEdit.getText().toString()));

        WeakHashMap<String, String> paramMap = new WeakHashMap<>();
        paramMap.put("mobile_number_1", sim1Edit.getText().toString());
        paramMap.put("mobile_number_2", sim2Edit.getText().toString());
        paramMap.put("device_no", PhoneUtil.getDeviceNo(this));

        String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        HttpService service = restAdapter.create(HttpService.class);
        service.sendSetting(paramMap, new Callback<StdResponse>() {
            @Override
            public void success(StdResponse stdResponse, Response response) {
                Toast.makeText(SettingActivity.this, ResourcesUtil.getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                PreferencesUtils.putBoolean(Setting.Is_Initiated, true);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(SettingActivity.this, ResourcesUtil.getString(R.string.save_fail_check_please), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
