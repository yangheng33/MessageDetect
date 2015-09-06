package com.detect.amar.messagedetect.version;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.detect.amar.messagedetect.R;

/**
 * Created by SAM on 2015/9/6.
 */
public class VersionDialogFragment extends DialogFragment implements android.content.DialogInterface.OnClickListener {

    public interface ClickDialog {
        public static int SURE = 1;
        public static int CANCEL = 2;
        public static int SKIP = 3;

        void callBack(int status);
    }

    private ClickDialog clickDialog;

    public void setClickDialog(ClickDialog clickDialog) {
        this.clickDialog = clickDialog;
    }

    private Version version;

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (version != null) {
            StringBuilder info = new StringBuilder();
            info.append("CurrentVersion:").append(version.getCurrentVersionName()).append("\n");
            info.append("new version:").append(version.getVersionName()).append("\n");
            info.append(version.getVersionDesc());

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.new_version_available))
                    .setMessage(info.toString())
                    .setPositiveButton(getResources().getString(R.string.agree), this)
                    .setNegativeButton(getResources().getString(R.string.next_time), this)
                    .setCancelable(false);
            return builder.create();
        } else {
            return null;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (clickDialog != null) {
            int result = ClickDialog.CANCEL;
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    result = ClickDialog.CANCEL;
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    result = ClickDialog.SKIP;
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    result = ClickDialog.SURE;
                    break;
            }
            clickDialog.callBack(result);
        }
    }


}
