package com.detect.amar.messagedetect.version;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.detect.amar.common.ResourcesUtil;
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

        final Version version = new Version(2, "1.0.2", "重大升级", "http://192.168.254.102:8080/examples/1.apk", 0, 1, "1.0.0");
        versionDialogFragment.setClickDialog(new VersionDialogFragment.ClickDialog() {
            @Override
            public void callBack(int status) {
                Log.d("home", "status:" + status + "");

                if (status == VersionDialogFragment.ClickDialog.SURE) {
                    showDownload();

                    new UpdateUtil(VersionActivity.this, version.getDownloadUrl(), new UpdateUtil.DownLoadProgress() {
                        @Override
                        public void onProgress(int id, long totalBytes, int progress) {
                            if (downloadDialog != null) {
                                downloadDialog.setProgress(progress);
                                downloadDialog.setMessage(ResourcesUtil.getString(R.string.waiting_please) + progress + "%");
                            }
                        }
                    });
                }
            }
        });
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

    ProgressDialog downloadDialog;

    void showDownload() {
        downloadDialog = new ProgressDialog(this);

        // 设置进度条风格，风格为圆形，旋转的
        downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // 设置ProgressDialog 标题
        downloadDialog.setTitle(ResourcesUtil.getString(R.string.updating));

        // 设置ProgressDialog提示信息
        downloadDialog.setMessage(ResourcesUtil.getString(R.string.waiting_please));

        // 设置ProgressDialog标题图标
//        downloadDialog.setIcon(R.drawable.img2);

        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        downloadDialog.setIndeterminate(false);

        // 设置ProgressDialog 进度条进度
        downloadDialog.setProgress(100);

        // 设置ProgressDialog 是否可以按退回键取消
        downloadDialog.setCancelable(true);

        // 让ProgressDialog显示
        downloadDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (downloadDialog != null) {
            downloadDialog.dismiss();
            downloadDialog = null;
        }
    }
}
