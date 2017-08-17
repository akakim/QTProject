package tripath.com.qsalesprototypeapp.mvpinterface;

import android.os.Message;

import com.android.volley.Response;

import tripath.com.qsalesprototypeapp.restclient.entity.AuthCode;
import tripath.com.qsalesprototypeapp.restclient.entity.User;

/**
 * Created by SSLAB on 2017-08-11.
 */

public interface LoginInteractor {

//    interface OnLoginFinishedListener{
//        void onFailed();
//        void onSuccess();
//    }

    void login(User user  );


}
