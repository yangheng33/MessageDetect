package com.detect.amar.messagedetect;

import android.os.Parcel;
import android.os.Parcelable;

import com.detect.amar.common.Encryption;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SAM on 2015/8/21.
 */
public class Message implements Parcelable {

    private String fromNumber;
    private String info;
    private String origindate;//短信的原始时间
    private boolean isTrans;//转发成功
    private String toNumber;

    private String sign;//签名字段，之前4个重要字段的md5值

    //数据库使用字段
    private String id;
    private String lastsenddate;//发送成功时间
    private String transfail;//最后一次发送失败原因（如果有）
    private String receivedate;//我们接收到短信的时间

    public Message(String fromNumber, String toNumber, String info, String date, String receivedate) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.info = info;
        this.origindate = date;
        this.receivedate = receivedate;
    }

    public Message(String id, String fromNumber, String toNumber, String info, String origindate, boolean isTrans, String lastsenddate, String transfail, String receivedate) {
        this.fromNumber = fromNumber;
        this.info = info;
        this.origindate = origindate;
        this.isTrans = isTrans;
        this.toNumber = toNumber;
        this.id = id;
        this.lastsenddate = lastsenddate;
        this.transfail = transfail;
        this.receivedate = receivedate;
    }


    public void calculateSign() {
        sign = Encryption.parseStrToMd5U32(fromNumber + toNumber + info + origindate);
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("from", fromNumber);
        map.put("to", toNumber);
        map.put("date", origindate);
        map.put("info", info);
        map.put("sign", sign);
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

    public boolean isTrans() {
        return isTrans;
    }

    public void setIsTrans(boolean isTrans) {
        this.isTrans = isTrans;
    }

    public String getReceivedate() {
        return receivedate;
    }

    public void setReceivedate(String receivedate) {
        this.receivedate = receivedate;
    }

    public Message() {
    }

    public String toUrl() {
        return "info=" + info + "&from=" + fromNumber + "&to=" + toNumber + "&date=" + origindate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastsenddate() {
        return lastsenddate;
    }

    public void setLastsenddate(String lastsenddate) {
        this.lastsenddate = lastsenddate;
    }

    public String getTransfail() {
        return transfail;
    }

    public void setTransfail(String transfail) {
        this.transfail = transfail;
    }

    public String getOrigindate() {
        return origindate;
    }

    public void setOrigindate(String origindate) {
        this.origindate = origindate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fromNumber);
        dest.writeString(this.info);
        dest.writeString(this.origindate);
        dest.writeByte(isTrans ? (byte) 1 : (byte) 0);
        dest.writeString(this.toNumber);
        dest.writeString(this.sign);
        dest.writeString(this.id);
        dest.writeString(this.lastsenddate);
        dest.writeString(this.transfail);
        dest.writeString(this.receivedate);
    }

    protected Message(Parcel in) {
        this.fromNumber = in.readString();
        this.info = in.readString();
        this.origindate = in.readString();
        this.isTrans = in.readByte() != 0;
        this.toNumber = in.readString();
        this.sign = in.readString();
        this.id = in.readString();
        this.lastsenddate = in.readString();
        this.transfail = in.readString();
        this.receivedate = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public String toString() {
        return "Message{" +
                "fromNumber='" + fromNumber + '\'' +
                ", info='" + info + '\'' +
                ", origindate='" + origindate + '\'' +
                ", isTrans=" + isTrans +
                ", toNumber='" + toNumber + '\'' +
                ", sign='" + sign + '\'' +
                ", id='" + id + '\'' +
                ", lastsenddate='" + lastsenddate + '\'' +
                ", transfail='" + transfail + '\'' +
                ", receivedate='" + receivedate + '\'' +
                '}';
    }
}