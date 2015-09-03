package com.detect.amar.messagedetect.model;

/**
 * Created by SAM on 2015/8/28.
 */
public class CheckResponse {
    private String status_sim_1;
    private String status_sim_2;
    private int cycle_frequency;

    private String versionCode;
    private String versionName;
    private String versionDesc;
    private String downloadUrl;
    /**
     * 0强制升级，1不是强制升级
     */
    private int isForce;

    public String getStatus_sim_1() {
        return status_sim_1;
    }

    public void setStatus_sim_1(String status_sim_1) {
        this.status_sim_1 = status_sim_1;
    }

    public String getStatus_sim_2() {
        return status_sim_2;
    }

    public void setStatus_sim_2(String status_sim_2) {
        this.status_sim_2 = status_sim_2;
    }

    public int getCycle_frequency() {
        return cycle_frequency;
    }

    public void setCycle_frequency(int cycle_frequency) {
        this.cycle_frequency = cycle_frequency;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
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
        final StringBuilder sb = new StringBuilder("CheckResponse{");
        sb.append("status_sim_1='").append(status_sim_1).append('\'');
        sb.append(", status_sim_2='").append(status_sim_2).append('\'');
        sb.append(", cycle_frequency=").append(cycle_frequency);
        sb.append(", versionCode='").append(versionCode).append('\'');
        sb.append(", versionName='").append(versionName).append('\'');
        sb.append(", versionDesc='").append(versionDesc).append('\'');
        sb.append(", downloadUrl='").append(downloadUrl).append('\'');
        sb.append(", isForce=").append(isForce);
        sb.append('}');
        return sb.toString();
    }
}
