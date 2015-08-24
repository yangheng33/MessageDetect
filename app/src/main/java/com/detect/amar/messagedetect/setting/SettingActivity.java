package com.detect.amar.messagedetect.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.detect.amar.messagedetect.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.setting_api_url)
    EditText apiUrlEdit;
    @Bind(R.id.setting_sim_1)
    EditText sim1Edit;
    @Bind(R.id.setting_sim_2)
    EditText sim2Edit;

    Setting setting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setting = new Setting(this);
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
    }

    @OnClick(R.id.setting_recovery)
    void clickRecovery() {
        apiUrlEdit.setText(setting.getApiUrl());
        sim1Edit.setText(setting.getSim1());
        sim2Edit.setText(setting.getSim2());
    }
}
