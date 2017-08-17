package tripath.com.qsalesprototypeapp.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;

import tripath.com.qsalesprototypeapp.MainActivity;
import tripath.com.qsalesprototypeapp.R;

/**
 * Created by SSLAB on 2017-07-12.
 */

public class FirebaseListenerService{
//public class FirebaseListenerService extends FirebaseMessagingService {
//
//    @Override
//    public void onMessageReceived( RemoteMessage remoteMessage ) {
//        super.onMessageReceived(remoteMessage);
//
//        Log.d(getClass().getSimpleName(), "Firebase Message : " + remoteMessage.getNotification().toString() );
//
//
//        String title = remoteMessage.getNotification().getTitle();
//        String message = remoteMessage.getNotification().getBody();
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder( this );
//        builder.setContentTitle( title ).setContentText( message ).setSmallIcon( R.mipmap.ag_app_icon);
//
//
//        Intent i = new Intent( this, MainActivity.class);
//
//        i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // 코드를 줘서 어디서 온지 구분할 수 있다.
//        PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT );
//        builder.setContentIntent( pendingIntent );
//        builder.setAutoCancel( true );
//        Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri( this, RingtoneManager.TYPE_NOTIFICATION );
//        builder.setSound( soundUri );
//
//        NotificationManager managerCompat = ( NotificationManager ) getSystemService(Context.NOTIFICATION_SERVICE );
//
//        managerCompat.notify("TAG",0,builder.build());
//    }
}
