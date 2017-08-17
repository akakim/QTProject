package tripath.com.qsalesprototypeapp.restclient.client;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import tripath.com.qsalesprototypeapp.restclient.entity.*;

/**
 * Created by SSLAB on 2017-06-22.
 */

public class RestApiClient {
//    public static final String basicURL = "http://qsales.autoground.tripath.work";
    public static final String basicURL = "http://172.19.132.194:8080";
    private Context context;
    private Gson gson;

    private ObjectMapper getConfiguredMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
        return mapper;
    }


//    private ObjectMapper getConfiguredMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
//        return mapper;
//    }

    public RestApiClient(Context context) {
        this.context = context;
        gson = new Gson();
    }




    /**
     *  1. 인증코드 리스트 조회
     */

    public void getAuthCodeList(AuthCode authCode, Response.Listener<AuthCode> listener, Response.ErrorListener error ){

        String params = gson.toJson(authCode);
        Log.d(getClass().getSimpleName(),"getAutCodeList :  " + params);
        Log.d(getClass().getSimpleName(),"URL + " + basicURL+"/external");
        Request<AuthCode> request =  new ApiRequest<>(
                Request.Method.POST,
                basicURL+"/external",
                params,
                listener,
                error,
                AuthCode.class
        );


//        Request<AuthCode> request =  new ApiRequest<>(
//                Request.Method.POST,
//                "http://192.168.0.101:8080/AndroidPushTestServer/getAdvisorInfoList",
//                params,
//                listener,
//                error,
//                AuthCode.class
//        );

        Volley.newRequestQueue(context).add(request);
    }


    /**
     * 2. 로그인 정보 검증
     */

    public void loginCheck(User user, Response.Listener<User> listener, Response.ErrorListener error){
        String params = gson.toJson(user);

        Log.d(getClass().getSimpleName(),"Parameter " + params.toString());
        Log.d(getClass().getSimpleName(),"URL + " + basicURL+"/external");
        Request<User> request =  new ApiRequest<>(
                Request.Method.POST,
                basicURL+"/external",
                params,
                listener,
                error,
                User.class
        );

//        Request<User> request =  new ApiRequest<>(
//                Request.Method.POST,
//                "http://192.168.0.101:8080/AndroidPushTestServer/getTestUserInfo",
//                params,
//                listener,
//                error,
//                User.class
//        );
        Volley.newRequestQueue(context).add(request);
    }
    /**
     * 3. 채팅방 페이지 호출
     */
//    public void getChatroomPage(ChatroomPage chatroomPage,Response.Listener<ChatroomPage> listener, Response.ErrorListener error ){
//        String params = gson.toJson(chatroomPage);
//        Request<ChatroomPage> request =  new ApiRequest<>(
//                Request.Method.POST,
//                basicURL,
//                params,
//                listener,
//                error,
//                ChatroomPage.class
//        );
//
//        Volley.newRequestQueue(context).add(request);
//    }


}
