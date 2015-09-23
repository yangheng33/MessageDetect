package com.detect.amar.messagedetect.version;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.detect.amar.common.PackageUtil;
import com.detect.amar.common.PreferencesUtils;
import com.detect.amar.common.ResourcesUtil;
import com.detect.amar.messagedetect.HttpService;
import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.model.StdResponse;
import com.detect.amar.messagedetect.model.VersionModel;
import com.detect.amar.messagedetect.setting.Setting;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VersionActivity extends AppCompatActivity {

    public static final String PARAM = "VersionActivity_Param";

    @Bind(R.id.version_name)
    TextView versionNameTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        ButterKnife.bind(this);
        setCurrentVersionName();

        VersionModel versionModel = getIntent().getParcelableExtra(PARAM);
        if (versionModel != null) {
            validateUpdate(versionModel);
        }
    }

    @OnClick(R.id.version_check)
    void checkUpdate() {
        String url = PreferencesUtils.getString(Setting.API_BASE_URL, Setting.Default_Api_Url);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        HttpService service = restAdapter.create(HttpService.class);

        service.getVersion(new Callback<StdResponse<List<VersionModel>>>() {
            @Override
            public void success(StdResponse<List<VersionModel>> versionModelStdResponse, Response response) {
                if (versionModelStdResponse.getInfo() != null && versionModelStdResponse.getInfo().size() > 0) {
                    VersionModel versionModel = versionModelStdResponse.getInfo().get(0);
                    validateUpdate(versionModel);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(VersionActivity.this, ResourcesUtil.getString(R.string.get_version_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void validateUpdate(VersionModel versionModel) {
        Object[] packageInfo = PackageUtil.getAppVersionInfo(VersionActivity.this);
        if (Integer.parseInt(packageInfo[1].toString()) < versionModel.getVersionCode()) {
            Version version = new Version(versionModel.getVersionCode(), versionModel.getVersionName(),
                    versionModel.getDescription(), versionModel.getDownloadUrl(), versionModel.getIsForce(),
                    Integer.parseInt(packageInfo[1].toString()), packageInfo[0].toString());
            if (version.getIsForce() == 1) {
                download(version);
            } else {
                showDialog(version);
            }
        }
    }

    public void showDialog(final Version version) {
        VersionDialogFragment versionDialogFragment = new VersionDialogFragment();
        versionDialogFragment.setClickDialog(new VersionDialogFragment.ClickDialog() {
            @Override
            public void callBack(int status) {
                Log.d("home", "status:" + status + "");

                if (status == VersionDialogFragment.ClickDialog.SURE) {
                    download(version);
                }
            }
        });
        versionDialogFragment.setVersion(version);

        versionDialogFragment.show(getFragmentManager(), "versionDialogFragment");
    }

    void download(final Version version) {
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
