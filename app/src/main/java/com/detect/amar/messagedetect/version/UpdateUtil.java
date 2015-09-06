package com.detect.amar.messagedetect.version;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import com.detect.amar.common.SApplication;

import java.io.File;

/**
 * Created by SAM on 2015/9/6.
 */
public class UpdateUtil extends BroadcastReceiver {

    private static long myDownloadReference;

    public void download(String url) {
        new MyDownloadApkAsyncTask().execute(url);
    }

    private boolean startDownload(String url) {
        boolean success = false;
        try {
            DownloadManager downloadManager = (DownloadManager) SApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            long reference = downloadManager.enqueue(request);

            myDownloadReference = reference;
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    private void addReceiverListing() {
        final DownloadManager downloadManager = (DownloadManager) SApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (myDownloadReference == reference) {
                    DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
                    myDownloadQuery.setFilterById(reference);

                    Cursor myDownload = downloadManager.query(myDownloadQuery);
                    if (myDownload.moveToFirst()) {
                        int fileNameIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                        int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

                        String fileName = myDownload.getString(fileNameIdx);
                        String fileUri = myDownload.getString(fileUriIdx);
                        try {
                            Intent installIntent = new Intent(Intent.ACTION_VIEW);
                            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            installIntent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                            SApplication.getInstance().startActivity(installIntent);
                            // SApplication.getInstance().getCurrentActivity().startActivity( installIntent );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    myDownload.close();
                }
            }
        };
        SApplication.getInstance().registerReceiver(receiver, filter);
    }

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

    public class MyDownloadApkAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            addReceiverListing();
            boolean updateIsSuccess = startDownload(params[0]);
            if (!updateIsSuccess) {
                publishProgress(1);
            }
            //publishProgress( 1 );
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}
