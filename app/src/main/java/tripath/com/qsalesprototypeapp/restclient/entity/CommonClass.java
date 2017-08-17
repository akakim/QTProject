package tripath.com.qsalesprototypeapp.restclient.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by SSLAB on 2017-06-26.
 */

public class CommonClass {
    @JsonProperty("code")
    protected String code;  // 서버의 응답값

    protected String message;

    @JsonProperty("type")
    protected String type;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
