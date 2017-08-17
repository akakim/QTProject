package tripath.com.qsalesprototypeapp.restclient.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by SSLAB on 2017-06-23.
 */

public class AuthCode extends CommonClass implements Parcelable{
    @JsonProperty("advisorSeq")
    private String advisorSeq;

    @JsonProperty("data")
    ArrayList<AdvisorInfoItem> dataList;
    public AuthCode(){
        Log.d(getClass().getSimpleName(),"default Constructor");
        type="selectAuthCodeList";
        dataList=  new ArrayList<>();
    }

    protected AuthCode(Parcel in) {
        Log.d(getClass().getSimpleName(),"default Constructor");
        type="selectAuthCodeList";
        advisorSeq = in.readString();
        dataList = in.createTypedArrayList(AdvisorInfoItem.CREATOR);
    }

    public static final Creator<AuthCode> CREATOR = new Creator<AuthCode>() {
        @Override
        public AuthCode createFromParcel(Parcel in) {
            return new AuthCode(in);
        }

        @Override
        public AuthCode[] newArray(int size) {
            return new AuthCode[size];
        }
    };

    public String getAdvisorSeq() {
        return advisorSeq;
    }

    public void setAdvisorSeq(String advisorSeq) {
        this.advisorSeq = advisorSeq;
    }

    public ArrayList<AdvisorInfoItem> getDataList() {
        return dataList;
    }

    @JsonProperty("data")
    public void setDataList(ArrayList<AdvisorInfoItem> dataList) {

        this.dataList.clear();
        if(dataList == null) {
            this.dataList = new ArrayList<AdvisorInfoItem>();
        }
        else {
            this.dataList.addAll(dataList);
        }

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(advisorSeq);

        dest.writeTypedList(dataList);
    }

    @Override
    public String toString() {

        String result="";
        result += " advisorSeq : " + advisorSeq;


        for( AdvisorInfoItem item : dataList){
            result += " AdvisorInfo Item " + item.toString();
        }
        return result;
    }
}
