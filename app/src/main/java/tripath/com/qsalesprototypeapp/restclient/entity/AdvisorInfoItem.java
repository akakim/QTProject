package tripath.com.qsalesprototypeapp.restclient.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by SSLAB on 2017-06-26.
 */

public class AdvisorInfoItem implements Parcelable{

    @JsonProperty("roomSeq")
    private String roomSeq;
    @JsonProperty("advisorSeq")
    private String advisorSeq;
    @JsonProperty("authCode")
    private  String authCode = "";
    @JsonProperty("roomStatus")
    private  String roomStatus = "";

    public AdvisorInfoItem(){}


    protected AdvisorInfoItem(Parcel in) {
        roomSeq = in.readString();
        authCode = in.readString();
        advisorSeq = in.readString();
        roomStatus = in.readString();

    }

    public static final Creator<AdvisorInfoItem> CREATOR = new Creator<AdvisorInfoItem>() {
        @Override
        public AdvisorInfoItem createFromParcel(Parcel in) {
            return new AdvisorInfoItem(in);
        }

        @Override
        public AdvisorInfoItem[] newArray(int size) {
            return new AdvisorInfoItem[size];
        }
    };

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }


    public String getRoomSeq() {
        return roomSeq;
    }

    public void setRoomSeq(String roomSeq) {
        this.roomSeq = roomSeq;
    }

    public String getAdvisorSeq() {
        return advisorSeq;
    }

    public void setAdvisorSeq(String advisorSeq) {
        this.advisorSeq = advisorSeq;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( this.authCode );
        dest.writeString( this.roomSeq );
        dest.writeString( this.advisorSeq );
        dest.writeString( this.roomStatus );

    }

    @Override
    public String toString() {
        String result = "";
        result += "roomSeq : [ " + roomSeq + "]";
        result += "advisorSeq : [ " + advisorSeq + "]";
        result += "authCode : [ " + authCode + "]";
        result += "roomStatus : [ " + roomStatus + "]";
      return result;
    }
}
