package com.detect.amar.messagedetect.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.detect.amar.common.PhoneUtil;
import com.detect.amar.messagedetect.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        Toast.makeText(this, "setup is complete", Toast.LENGTH_SHORT).show();
    }


}
