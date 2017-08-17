package tripath.com.qsalesprototypeapp.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by SSLAB on 2017-07-21.
 */

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {


    public GcmBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(), GCMGetTokenService.class.getName());


        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
        Log.d("**","************************************************* Receiver 실행");

    }
}
