package tripath.com.qsalesprototypeapp.restclient.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by SSLAB on 2017-06-23.
 */

public class Status {

    @JsonProperty("code")
    private String code;
    @JsonProperty("advisorSeq")
    private String advisorSeq;

    public Status(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdvisorSeq() {
        return advisorSeq;
    }

    public void setAdvisorSeq(String advisorSeq) {
        this.advisorSeq = advisorSeq;
    }
}
