package tripath.com.qsalesprototypeapp.mvpinterface;

import android.os.Message;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import tripath.com.qsalesprototypeapp.StaticValues;
import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.restclient.entity.User;

/**
 *  Response.Listener와 ErorrListner는 interactor 대신에 사용한다.
 *
 */

public class LoginInteracotrImpl {

    RestApiClient client;

    public LoginInteracotrImpl(RestApiClient client) {
        this.client = client;
    }

//    @Override
//    public void login(final User user) {
//
//
//
////          client.loginCheck(user, this,this );
//
////        Message msg = Message.obtain();
////
////        msg.what = StaticValues.LOGIN;
////        msg.obj = new Runnable() {
////            @Override
////            public void run() {
////                boolean isError = false;
////                if(TextUtils.isEmpty( username )){
////                    listener.onFailed();
////                    isError = true;
////                    return ;
////                }
////
////                if( TextUtils.isEmpty( password )){
////                    listener.onFailed();
////                    isError = true;
////                    return ;
////                }
////
////                if( isError ){
////                    listener.onSuccess();
////                }
////
////            }
////        };
////
////        return msg;
//
//
//    }


}
