package tripath.com.qsalesprototypeapp;

import android.app.Application;

import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;

/**
 * Created by SSLAB on 2017-07-20.
 */

public class GlobalApplication extends Application {

    final String TAG = getClass().getSimpleName();
    Thread.UncaughtExceptionHandler uncaughtHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Application Created");

        uncaughtHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
               e.printStackTrace();

                Log.e(TAG,"unexpected Error occured");

                System.exit(1);
            }
        };

        Thread.setDefaultUncaughtExceptionHandler( uncaughtHandler );
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.d(TAG,"get Service Action " + service.getAction().toString() );
        return super.bindService(service, conn, flags);
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG,"onTrimMemory Level : " + level );
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG,"onTerminate" );
    }
}
