package tripath.com.qsalesprototypeapp.push;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import tripath.com.qsalesprototypeapp.StaticValues;
import tripath.com.qsalesprototypeapp.manager.SharedManager;

/**
 * Created by SSLAB on 2017-07-12.
 */

public class FirebaseInitialize extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

//        String token = FirebaseInstanceId.getInstance().getToken();
//        Log.d (getClass().getSimpleName(),"token : " + token );
//        SharedManager.putString( this , StaticValues.SHARED_FCM_TOKEN, token );
//
//        Log.d( getClass().getSimpleName(), "Firebase Token : " + SharedManager.getString( this, StaticValues.SHARED_FCM_TOKEN ) );
    }
}
