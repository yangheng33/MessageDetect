package com.detect.amar.messagedetect.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/23 0023.
 */
public class VersionModel implements Parcelable {
    private int id;
    private int versionCode;
    private String versionName;
    private String description;
    private String downloadUrl;
    /**
     * 0强制升级，1不是强制升级
     */
    private int isForce;
    private int isEnable;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
        dest.writeString(this.description);
        dest.writeString(this.downloadUrl);
        dest.writeInt(this.isForce);
        dest.writeInt(this.isEnable);
    }

    public VersionModel() {
    }

    protected VersionModel(Parcel in) {
        this.id = in.readInt();
        this.versionCode = in.readInt();
        this.versionName = in.readString();
        this.description = in.readString();
        this.downloadUrl = in.readString();
        this.isForce = in.readInt();
        this.isEnable = in.readInt();
    }

    public static final Parcelable.Creator<VersionModel> CREATOR = new Parcelable.Creator<VersionModel>() {
        public VersionModel createFromParcel(Parcel source) {
            return new VersionModel(source);
        }

        public VersionModel[] newArray(int size) {
            return new VersionModel[size];
        }
    };
}
