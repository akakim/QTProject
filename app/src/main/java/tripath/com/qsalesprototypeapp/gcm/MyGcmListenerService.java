package tripath.com.qsalesprototypeapp.gcm;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import tripath.com.qsalesprototypeapp.ChatRoomCoordinatorActivity;
import tripath.com.qsalesprototypeapp.GetListActivity;
import tripath.com.qsalesprototypeapp.R;
import tripath.com.qsalesprototypeapp.StaticValues;
import tripath.com.qsalesprototypeapp.manager.SharedManager;

/**
 * Created by SSLAB on 2017-07-21.
 */

public class MyGcmListenerService extends GcmListenerService {
    private final String TAG = getClass().getSimpleName();

    public MyGcmListenerService(){}



    @Override
    public void onMessageReceived(String from, Bundle data) {

        String id = data.getString("id");
        String authCode = data.getString("authCode");
        String advisorSeq = data.getString("advisorSeq");
        String strMessage = data.getString("message");
        String title=data.getString("title","");
        // notification의 ID는

//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + strMessage);
        Log.d(TAG,"data set : " + data.toString());


        /**
         * [
         * {google.sent_time=1500874758368, ticker=[QSALES],
         * id=jaehoon_park@kolon.com,
         * title=[QSALES],
         * google.message_id=0:1500874758373755%2d74dd6515dad38c,
         * message=[오토그라운드-QSALES] 상담채팅요청이 접수,
         * authCode=PAK5637988,
         * collapse_key=1.1940041365110248}]
         */


        String loginCachePath = this.getCacheDir().getPath() + StaticValues.USER_CACHE;
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder( this );
        notiBuilder.setContentTitle( title ).setContentText( strMessage ).setSmallIcon( R.mipmap.ag_app_icon);

        File cacheFile = new File(loginCachePath);

        Intent i = null;
        // 캐쉬의 데이터부터 조회한다.
        if(cacheFile.exists()){
            try {
                InputStream inputStream = null;

                inputStream = new FileInputStream(cacheFile);
                int content = 0;
                StringBuilder builder = new StringBuilder();

                while ((content = inputStream.read()) != -1) {
                    builder.append((char) content);
                }


                JSONObject jsonObject = new JSONObject(builder.toString());

                final String cacheId = jsonObject.getString(StaticValues.ID);

                if(id.equals(cacheId)){

                    if( isForeground( getPackageName() )){
                        SharedManager.putString(this,StaticValues.ADVISOR_SEQUENCE, advisorSeq);
                        SharedManager.putString(this,StaticValues.CHATROOM_AUTH_CODE, authCode );
                        SharedManager.putString(this,StaticValues.MESSAGE,strMessage);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(StaticValues.REFRESH_CHATROOMS));
                    }else {
                        i = new Intent(this, GetListActivity.class);
                        i.putExtra("twice", ChatRoomCoordinatorActivity.class.getSimpleName());
                        i.putExtra("advisorSeq", advisorSeq);
                        i.putExtra("key", authCode);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // 코드를 줘서 어디서 온지 구분할 수 있다.
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                        notiBuilder.setContentIntent(pendingIntent);
                        notiBuilder.setAutoCancel(true);
                        Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
                        notiBuilder.setSound(soundUri);

                        NotificationManager managerCompat = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        managerCompat.notify("TAG", 0, notiBuilder.build());
                    }

//                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(StaticValues.REFRESH_CHATROOMS));
//
//                    ActivityManager activityManager = (ActivityManager)getSystemService( Context.ACTIVITY_SERVICE );
//                    List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
//                    activityManager.get
//                    for(ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos){
//                        runningAppProcessInfo.
//                    }
                }
                inputStream.close();
            }catch (IOException e ){
                e.printStackTrace();
            }catch ( Exception e ){
                e.printStackTrace();
            }
        }
    }

    public boolean isForeground(String myPackage){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningTaskInfos = manager.getRunningAppProcesses();
        String packages ="";
        for (ActivityManager.RunningAppProcessInfo processInfo : runningTaskInfos){
            if(processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                packages = processInfo.processName;
                return true;
            }
        }
        return false;
    }


    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

    }
}
