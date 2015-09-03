package com.detect.amar.messagedetect.model;

/**
 * Created by SAM on 2015/8/21.
 */
public class StdResponse <T>{

     protected String status;
     protected String error;
     protected T info;

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StdResponse{");
        sb.append("status='").append(status).append('\'');
        sb.append(", error='").append(error).append('\'');
        sb.append(", info=").append(info);
        sb.append('}');
        return sb.toString();
    }
}
