package tripath.com.qsalesprototypeapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.restclient.entity.CommonClass;
import tripath.com.qsalesprototypeapp.restclient.entity.User;
import tripath.com.qsalesprototypeapp.wiget.DilatingDotsProgressBar;

public class SplashScreen extends BaseActivity {


    ImageView imgLogo;
    private Timer timer;
    private final String TAG = getClass().getSimpleName();

//    GoogleCloudMessaging gcm;on
    String regid;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash_screen);

        backPressActionMode = 1;

        imgLogo = ( ImageView )findViewById(R.id.imageLogo) ;
        ((TextView)findViewById(R.id.textVer)).setText( BuildConfig.VERSION_NAME );
        dilatingDotsProgressBar = (DilatingDotsProgressBar )findViewById( R.id.progress);


        customDotsProgressDialog.show();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                // IntentAction 중 GCM을 위한 action들 .
                if(action.equals(StaticValues.REGISTRATION_READY)){
                    // 액션이 READY일 경우
                    String token = intent.getStringExtra(StaticValues.FCM_TOKEN);
                    Log.d(SplashScreen.this.getClass().getName(),"token : "+token);
                } else if(action.equals(StaticValues.REGISTRATION_GENERATING)){
                    // 액션이 GENERATING일 경우
                    String token = intent.getStringExtra(StaticValues.FCM_TOKEN);
                    Log.d(SplashScreen.this.getClass().getName(),"token : "+token);
                } else if(action.equals(StaticValues.REGISTRATION_COMPLETE)){
                    // 액션이 COMPLETE일 경우
                    String token = intent.getStringExtra(StaticValues.FCM_TOKEN);
                    Log.d(SplashScreen.this.getClass().getName(),"REGISTRATION_COMPLETE token : "+token);
                    SharedManager.putString(SplashScreen.this,StaticValues.FCM_TOKEN,token);
                }
            }
        };

        getInstanceIdToken();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(StaticValues.REGISTRATION_READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(StaticValues.REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(StaticValues.REGISTRATION_COMPLETE));

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                    File loginCache = new File ( loginCachePath );

                    try {

                        if( loginCache.exists() ) {


                            InputStream inputStream = null;

                            inputStream = new FileInputStream(loginCache);
                            int content = 0;
                            StringBuilder builder = new StringBuilder();

                            while ((content = inputStream.read()) != -1) {
                                builder.append((char) content);
                            }


                            JSONObject jsonObject = new JSONObject(builder.toString());

                            final String id = jsonObject.getString(StaticValues.ID);
                            final String passwd = jsonObject.getString(StaticValues.PASSWORD);
                            boolean isAutoLoginChecked = jsonObject.getBoolean(StaticValues.AUTO_LOGIN);
                            inputStream.close();


                            /**
                             * 이렇게 처리를 굳이 안해도 되는데 Dialog가 timing문제 때문에안보여서 ...
                             */
                            if( isAutoLoginChecked ) {
                                baseHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        User user = new User(id, passwd, SharedManager.getString(SplashScreen.this, StaticValues.FCM_TOKEN));
                                        restApiClient.loginCheck(user, SplashScreen.this, SplashScreen.this);
                                    }
                                }, 2000);
                            }else {
                                Log.d(TAG,"cache exists... but not AutoLogin Clicked" );
                                Runnable runnable = new Runnable(){
                                    @Override
                                    public void run() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                            Log.d(TAG,"not cache exists... " );

                                            redirectMainActivityWithAnim( SplashScreen.this);
                                        }
                                    }
                                };

                                baseHandler.postDelayed(runnable,2000);
                            }
                        } else {
                        Runnable runnable = new Runnable(){
                            @Override
                            public void run() {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    Log.d(TAG,"not cache exists... " );
                                    redirectMainActivityWithAnim( SplashScreen.this);
                                }
                            }
                        };


                        baseHandler.postDelayed(runnable,2000);

                    }
                }catch (IOException e){
                    dilatingDotsProgressBar.hide();
                    showToast( "사용자 정보를 불러올 수 없습니다.");
                    redirectMainActivityWithAnim( SplashScreen.this);
                }catch ( Exception e ){
                    dilatingDotsProgressBar.hide();
                    showToast( "예상치못한 에러발생");
                    redirectMainActivityWithAnim( SplashScreen.this );
                }finally {
                        timer.cancel();
                }

            }
        };




        timer.schedule( task, 0, 2000);
    }

    private void checkFCM(){
        if( dilatingDotsProgressBar != null && !dilatingDotsProgressBar.isShown()){
            dilatingDotsProgressBar.show();
        }

        timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {

//                File systemProperty = new File( systemPropertyPath );


                Runnable runnable = new Runnable(){

                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                int flags [] = new int[] {Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK };
                                Intent i = new Intent(SplashScreen.this,MainActivity.class );
                                for(int flag : flags)
                                    i.addFlags(flag);

                                startActivity(i);
                            }
                        }
                    };

                    baseHandler.sendEmptyMessage( StaticValues.REMOVE_DIALOG_MESSAGE );
                   baseHandler.post(runnable);
                    timer.cancel();

            }

            @Override
            public boolean cancel() {

                return super.cancel();
            }
        };

        timer.schedule( task, 0, 100);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void checkPermission() {    // 필요 없는 듯하지만 버전 올라가면 또 모르니까
        // permission 에 대한 정보는 ActivityCompat이
        // 현재 나의 상태가 어떤지에 대해선 PackageManger가. ..
        int permitNetwork = ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_NETWORK_STATE );
        int permitWIFI = ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_WIFI_STATE );
        if ( permitNetwork != PackageManager.PERMISSION_GRANTED || permitWIFI != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[]{ Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE }, 0 );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
    }

    @Override
    public void onResponse(Object response) {
        super.onResponse(response);

        if(response != null) {
            String responseCode = (((CommonClass) response).getCode());
            if ("0000".equals(responseCode)) {
                if(dilatingDotsProgressBar != null){
                    if(dilatingDotsProgressBar.isShown()){
                        dilatingDotsProgressBar.hide();
                    }
                }

                if (response instanceof User) {
                    User user = (User) response;
                    String code = user.getCode();

                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(StaticValues.ADVISOR_SEQUENCE, user.getData());
                    editor.commit();

                    Intent i = new Intent(this,GetListActivity.class);
                    i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );

                    if(customDotsProgressDialog != null && customDotsProgressDialog.isShowing()){
                        customDotsProgressDialog.dismiss();
                    }
//                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);

                    startActivity( i );
                    overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_slide_out_top );
                }

            }else {
                redirectMainActivityWithAnim( this );
            }
        }else {
            redirectMainActivityWithAnim( this );
        }
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
