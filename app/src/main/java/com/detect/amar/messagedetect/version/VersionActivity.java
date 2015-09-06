package com.detect.amar.messagedetect.version;

import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.detect.amar.messagedetect.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VersionActivity extends AppCompatActivity {

    @Bind(R.id.version_name)
    TextView versionNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        ButterKnife.bind(this);

        setCurrentVersionName();
    }

    @OnClick(R.id.version_check)
    void checkUpdate() {
        VersionDialogFragment versionDialogFragment = new VersionDialogFragment();
        versionDialogFragment.setClickDialog(new VersionDialogFragment.ClickDialog() {
            @Override
            public void callBack(int status) {
                Log.d("home", "status:" + status + "");
            }
        });
        Version version = new Version(2, "1.0.2", "重大升级", "http://www.baidu.com", 0, 1, "1.0.0");
        versionDialogFragment.setVersion(version);

        versionDialogFragment.show(getFragmentManager(), "versionDialogFragment");
    }


    void setCurrentVersionName() {
        try {
            PackageInfo manager = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            versionNameTxt.setText(manager.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    void setCurrentVersionCode() {
        try {
            PackageInfo manager = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            versionNameTxt.setText(manager.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
