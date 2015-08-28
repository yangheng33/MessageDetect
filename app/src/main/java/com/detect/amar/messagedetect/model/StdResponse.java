package com.detect.amar.messagedetect.model;

/**
 * Created by SAM on 2015/8/21.
 */
public class StdResponse {

     protected String status;
     protected String error;
     protected String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
