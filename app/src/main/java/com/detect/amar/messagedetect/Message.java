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
    private String toNumber;
    private String info;
    private String origindate;//短信的原始时间
    private String receiveDate;//我们接收到短信的时间
    private String sign;//签名字段，fromNumber+toNumber+info+origindate的md5值
    private int simSlot = 1;

    //仅限于本地数据库使用字段
    private String id;
    private String lastsenddate;//发送成功时间
    private String transfail;//最后一次发送失败原因（如果有）
    private boolean isTrans;//转发成功

    //仅限于转发数据时使用的字段
    private String deviceSerial;//设备唯一识别号

    public Message() {
    }

    public Message(String fromNumber, int simSlot, String info, String origindate, String receiveDate, String deviceSerial) {
        this.fromNumber = fromNumber;
        this.simSlot = simSlot;
        this.info = info;
        this.origindate = origindate;
        this.receiveDate = receiveDate;
        this.deviceSerial = deviceSerial;
    }

    public Message(String fromNumber, String toNumber, String info, String origindate, String receiveDate, String sign, String lastsenddate, String transfail, boolean isTrans, String deviceSerial) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.info = info;
        this.origindate = origindate;
        this.receiveDate = receiveDate;
        this.sign = sign;
        this.lastsenddate = lastsenddate;
        this.transfail = transfail;
        this.isTrans = isTrans;
        this.deviceSerial = deviceSerial;
    }

    public String calculateSign() {
        sign = Encryption.parseStrToMd5U32(fromNumber + toNumber + info + origindate);
        return sign;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("from_number", fromNumber);
        map.put("to_number", toNumber);
        map.put("message", info);
        map.put("origin_date", origindate);
        map.put("receive_date", receiveDate);
        map.put("sign", calculateSign());
        map.put("device_no", sign);
        return map;
    }


    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOrigindate() {
        return origindate;
    }

    public void setOrigindate(String origindate) {
        this.origindate = origindate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public boolean isTrans() {
        return isTrans;
    }

    public void setIsTrans(boolean isTrans) {
        this.isTrans = isTrans;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fromNumber);
        dest.writeString(this.toNumber);
        dest.writeString(this.info);
        dest.writeString(this.origindate);
        dest.writeString(this.receiveDate);
        dest.writeString(this.sign);
        dest.writeString(this.id);
        dest.writeString(this.lastsenddate);
        dest.writeString(this.transfail);
        dest.writeByte(isTrans ? (byte) 1 : (byte) 0);
        dest.writeString(this.deviceSerial);
    }

    protected Message(Parcel in) {
        this.fromNumber = in.readString();
        this.toNumber = in.readString();
        this.info = in.readString();
        this.origindate = in.readString();
        this.receiveDate = in.readString();
        this.sign = in.readString();
        this.id = in.readString();
        this.lastsenddate = in.readString();
        this.transfail = in.readString();
        this.isTrans = in.readByte() != 0;
        this.deviceSerial = in.readString();
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
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("fromNumber='").append(fromNumber).append('\'');
        sb.append(", toNumber='").append(toNumber).append('\'');
        sb.append(", info='").append(info).append('\'');
        sb.append(", origindate='").append(origindate).append('\'');
        sb.append(", receiveDate='").append(receiveDate).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", lastsenddate='").append(lastsenddate).append('\'');
        sb.append(", transfail='").append(transfail).append('\'');
        sb.append(", isTrans=").append(isTrans);
        sb.append(", deviceSerial='").append(deviceSerial).append('\'');
        sb.append('}');
        return sb.toString();
    }
}