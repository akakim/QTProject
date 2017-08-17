package tripath.com.qsalesprototypeapp.gcm;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import tripath.com.qsalesprototypeapp.R;
import tripath.com.qsalesprototypeapp.SplashScreen;
import tripath.com.qsalesprototypeapp.StaticValues;
import tripath.com.qsalesprototypeapp.manager.SharedManager;

/**
 * Created by SSLAB on 2017-07-21.
 */

public class GCMGetTokenService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    final static String TAG = "GCMGetTokenService";

    public GCMGetTokenService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG,"getTokenService");
        // GCM Instance ID의 토큰을 가져오는 작업이 시작되면 LocalBoardcast로 GENERATING 액션을 알려 ProgressBar가 동작하도록 한다.
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(StaticValues.REGISTRATION_GENERATING));


        // GCM을 위한 Instance ID를 가져온다.
        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;


        try {
            synchronized (TAG) {
                // GCM 앱을 등록하고 획득한 설정파일인 google-services.json을 기반으로 SenderID를 자동으로 가져온다.
                String default_senderId = getString(R.string.project_number);
                // GCM 기본 scope는 "GCM"이다.
                String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
                // Instance ID에 해당하는 토큰을 생성하여 가져온다.
                token = instanceID.getToken(default_senderId, scope, null);

                Log.i(TAG, "GCM Registration Token: " + token + "");
                SharedManager.putString(this,StaticValues.FCM_TOKEN,token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // GCM Instance ID에 해당하는 토큰을 획득하면 LocalBoardcast에 COMPLETE 액션을 알린다.
        // 이때 토큰을 함께 넘겨주어서 UI에 토큰 정보를 활용할 수 있도록 했다.
        Intent registrationComplete = new Intent(StaticValues.REGISTRATION_COMPLETE);
        registrationComplete.putExtra(StaticValues.FCM_TOKEN, token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
