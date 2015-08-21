package com.detect.amar.messagedetect;

/**
 * Created by SAM on 2015/8/21.
 */
public class Response {

    String status;
    String info;

    public Response() {

    }

    public Response(String status, String info) {
        this.status = status;
        this.info = info;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                '}';
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
