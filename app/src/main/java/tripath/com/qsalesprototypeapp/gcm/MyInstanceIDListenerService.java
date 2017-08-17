package tripath.com.qsalesprototypeapp.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by SSLAB on 2017-07-21.
 */

public class MyInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMGetTokenService.class);
        startService(intent);
    }
}
