package com.detect.amar.messagedetect.call;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/30.
 */
public class CallInfo implements Parcelable {

    public static int CALL_TYPE_CALL_MISS = 1;
    public static int CALL_TYPE_CALL_RECEIVE = 2;

    private String fromNumber;
    private String toNumber;
    private long noticeDate;
    private int slot;
    private int callType; //1未接来电，2已接来电

    public CallInfo(String fromNumber, long noticeDate, int slot, int callType) {
        this.fromNumber = fromNumber;
        this.noticeDate = noticeDate;
        this.slot = slot;
        this.callType = callType;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("from_number", fromNumber);
        map.put("to_number", toNumber);
        map.put("notice_date", noticeDate + "");
        map.put("slot", slot + "");
        map.put("call_type", callType + "");
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

    public long getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(long noticeDate) {
        this.noticeDate = noticeDate;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    @Override
    public String toString() {
        return "CallInfo{" +
                "fromNumber='" + fromNumber + '\'' +
                ", toNumber='" + toNumber + '\'' +
                ", noticeDate=" + noticeDate +
                ", slot=" + slot +
                ", callType=" + callType +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fromNumber);
        dest.writeString(this.toNumber);
        dest.writeLong(this.noticeDate);
        dest.writeInt(this.slot);
        dest.writeInt(this.callType);
    }

    protected CallInfo(Parcel in) {
        this.fromNumber = in.readString();
        this.toNumber = in.readString();
        this.noticeDate = in.readLong();
        this.slot = in.readInt();
        this.callType = in.readInt();
    }

    public static final Creator<CallInfo> CREATOR = new Creator<CallInfo>() {
        public CallInfo createFromParcel(Parcel source) {
            return new CallInfo(source);
        }

        public CallInfo[] newArray(int size) {
            return new CallInfo[size];
        }
    };
}
