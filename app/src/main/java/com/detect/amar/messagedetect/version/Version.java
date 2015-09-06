package com.detect.amar.messagedetect.version;

/**
 * Created by SAM on 2015/9/6.
 */
public class Version {

    private int versionCode;
    private String versionName;
    private String versionDesc;
    private String downloadUrl;
    /**
     * 0强制升级，1不是强制升级
     */
    private int isForce;

    private int currentVersionCode;
    private String currentVersionName;

    public Version() {
    }

    public Version(int versionCode, String versionName, String versionDesc, String downloadUrl, int isForce, int currentVersionCode, String currentVersionName) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.versionDesc = versionDesc;
        this.downloadUrl = downloadUrl;
        this.isForce = isForce;
        this.currentVersionCode = currentVersionCode;
        this.currentVersionName = currentVersionName;
    }

    public int getCurrentVersionCode() {
        return currentVersionCode;
    }

    public void setCurrentVersionCode(int currentVersionCode) {
        this.currentVersionCode = currentVersionCode;
    }

    public String getCurrentVersionName() {
        return currentVersionName;
    }

    public void setCurrentVersionName(String currentVersionName) {
        this.currentVersionName = currentVersionName;
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

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Version{");
        sb.append("versionCode='").append(versionCode).append('\'');
        sb.append(", versionName='").append(versionName).append('\'');
        sb.append(", versionDesc='").append(versionDesc).append('\'');
        sb.append(", downloadUrl='").append(downloadUrl).append('\'');
        sb.append(", isForce=").append(isForce);
        sb.append('}');
        return sb.toString();
    }
}
