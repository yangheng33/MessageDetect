package com.detect.amar.messagedetect.version;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;

/**
 * Created by SAM on 2015/9/6.
 */
public class UpdateUtil extends BroadcastReceiver {

    Context context;

    String fileName = "";

    int DOWNLOAD_THREAD_POOL_SIZE = 1;
    ThinDownloadManager downloadManager;
    int downloadId;

    DownLoadProgress downLoadProgress;

    public UpdateUtil(Context context, String apkUrl, DownLoadProgress downLoadProgress) {
        this.context = context;
        this.downLoadProgress = downLoadProgress;
        download(apkUrl);
    }

    public void download(String url) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/install/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        fileName = path + url.substring(url.lastIndexOf("/"));
        downloadManager = new ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
        Uri downloadUri = Uri.parse(url);
        Uri destinationUri = Uri.parse(fileName);
        final DownloadRequest downloadRequest1 = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW)
                .setDownloadListener(downloadStatusListener);
        downloadId = downloadManager.add(downloadRequest1);
    }

    DownloadStatusListener downloadStatusListener = new DownloadStatusListener() {
        @Override
        public void onDownloadComplete(int i) {
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
            context.startActivity(installIntent);
        }

        @Override
        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
            Log.d("home", "onDownloadFailed");
        }

        @Override
        public void onProgress(int id, long totalBytes, int progress) {
            Log.d("home", "onProgress" + id + "," + totalBytes + "," + progress);
            if (downLoadProgress != null) {
                downLoadProgress.onProgress(id, totalBytes, progress);
            }
        }
    };


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String packageName = intent.getData().getSchemeSpecificPart();
        if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            //Toast.makeText(context, ResourcesUtils.getString(R.string.Utils_T4), Toast.LENGTH_LONG).show();
            PackageManager pm = context.getPackageManager();
            Intent intent1 = new Intent();
            try {
                intent1 = pm.getLaunchIntentForPackage(packageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            //Log.d( tag, "添加了新的应用2" );
        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
            //Log.d( tag, "有应用被删除" );
            //Toast.makeText( context, "有应用被删除", Toast.LENGTH_LONG ).show();
        } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
            //Log.d( tag, "有应用被替换" );
            //Toast.makeText( context, "有应用被替换", Toast.LENGTH_LONG ).show();
        }
    }

    public interface DownLoadProgress {
        void onProgress(int id, long totalBytes, int progress);
    }
}
