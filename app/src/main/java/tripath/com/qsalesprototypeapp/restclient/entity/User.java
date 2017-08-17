package tripath.com.qsalesprototypeapp.restclient.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by SSLAB on 2017-06-23.
 */

public class User extends CommonClass {
    @JsonProperty("advisorId")
    private String advisorId;
    @JsonProperty("password")
    private String password;
    @JsonProperty("data")
    private String data;
    @JsonProperty("advisorDvcKey")
    private String advisorDvcKey;

    public User(){
       this("","","");
    }

    public User(String advisorId, String password,String advisorDvcKey) {
        type = "login";
        this.advisorId = advisorId;
        this.password = password;
        this.advisorDvcKey = advisorDvcKey;
    }


    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "advisorId : [ " + advisorId + "] " +
                "password : [ " + password + "] " +
                "code : [ " + code + "] " +
                "data : [ " + data + "] "+
                "Instance ID : [ " + advisorDvcKey + " ] ";
    }
}
