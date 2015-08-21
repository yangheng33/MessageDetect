package com.detect.amar.messagedetect;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SAM on 2015/8/21.
 */
public class Message implements Parcelable {

    private String fromNumber;
    private String info;
    private String date;
    private boolean isTrans;//转发成功
    private String toNumber;

    public Message(String fromNumber, String toNumber, String info, String date) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.info = info;
        this.date = date;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("from", fromNumber);
        map.put("to", toNumber);
        map.put("date", date);
        map.put("info", info);
        return map;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isTrans() {
        return isTrans;
    }

    public void setIsTrans(boolean isTrans) {
        this.isTrans = isTrans;
    }

    public Message() {
    }

    public String toUrl() {
        return "info=" + info + "&from=" + fromNumber + "&to=" + toNumber + "&date=" + date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "短信来自='" + fromNumber + '\'' +
                ", 短信接收='" + toNumber + '\'' +
                ", 短信内容='" + info + '\'' +
                ", 短信时间='" + date + '\'' +
                ", 转发成功='" + isTrans + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fromNumber);
        dest.writeString(this.info);
        dest.writeString(this.date);
        dest.writeByte(isTrans ? (byte) 1 : (byte) 0);
        dest.writeString(this.toNumber);
    }

    protected Message(Parcel in) {
        this.fromNumber = in.readString();
        this.info = in.readString();
        this.date = in.readString();
        this.isTrans = in.readByte() != 0;
        this.toNumber = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
