package tripath.com.qsalesprototypeapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.net.HttpURLConnection;

import tripath.com.qsalesprototypeapp.gcm.GCMGetTokenService;
import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.mvpinterface.MVPMainActivity;
import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.restclient.entity.CommonClass;
import tripath.com.qsalesprototypeapp.wiget.DilatingDotsProgressBar;
import tripath.com.qsalesprototypeapp.wiget.DlgDone;
import tripath.com.qsalesprototypeapp.wiget.DlgDoneCancel;
import tripath.com.qsalesprototypeapp.wiget.DotsProgressBarDialog;

import com.android.volley.NoConnectionError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by SSLAB on 2017-06-22.
 */

public class BaseActivity extends AppCompatActivity implements Response.Listener,Response.ErrorListener
,DlgDone.OnListener,DlgDoneCancel.OnListener{

    protected RestApiClient restApiClient;
    boolean isPause = false;
    boolean doFinish = false;
    boolean doBack = false;



    // 1 이면 종료
    // 2 이면 더블 backAction
    // default는 단순 back button 액션
    protected int backPressActionMode = 0;

    protected DilatingDotsProgressBar dilatingDotsProgressBar;
    DotsProgressBarDialog customDotsProgressDialog;
    final String TAG = BaseActivity.class.getSimpleName();
    protected Handler baseHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(BaseActivity.this.getClass().getSimpleName(),"get Message");
            switch ( msg.what){

                case StaticValues.SHOW_DIALOG_MESSAGE:
                    if( dilatingDotsProgressBar != null && !dilatingDotsProgressBar.isShown()){
                        dilatingDotsProgressBar.show();
                    }


                    break;
                case StaticValues.REMOVE_DIALOG_MESSAGE:
                    if( dilatingDotsProgressBar == null){
                        Log.e(BaseActivity.this.getClass().getSimpleName(),"dilating Progress Bar is null>..");
                    }
                    if( dilatingDotsProgressBar != null && dilatingDotsProgressBar.isShown()){
                        Log.d(BaseActivity.this.getClass().getSimpleName(),"dialog will be removed>..");
                        dilatingDotsProgressBar.hide();
                    }else {
                        Log.e(BaseActivity.this.getClass().getSimpleName(),"dialog is not shown ....... ");
                    }


                    break;
                case StaticValues.JAVA_SCRIPT_BACK_CALLBACK:
                      onBackPressed();
//                    Log.d(BaseActivity.this.getClass().getSimpleName(),"javascriptBackCallback");
//                    if(msg.obj != null){
//                        Log.d(BaseActivity.this.getClass().getSimpleName(),"msg obj " + msg.obj.toString() );
//                    }else {
//                        Log.e(TAG,"msg's obj is null ");
//                    }
//                    try {
//                        JSONObject jsonObject = new JSONObject((String) msg.obj);
//                        Log.d  (BaseActivity.this.getClass().getSimpleName(),"get javascript call back" + jsonObject.toString());
//                        String typeStr = jsonObject.getString("type");
//                        switch (typeStr){
//                            case "back":
//                                break;
//                        }
//                        onBackPressed();
//                    }catch (Exception e ){
//                        Toast.makeText(BaseActivity.this,"get javascript callback error occured ",Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
                    break;
                case StaticValues.ON_PAGE_FINISHED:
                    doBack = true;
                    break;

                case StaticValues.SHOW_DILATING_DIALOG_MESSAGE:
                    if( customDotsProgressDialog != null && !customDotsProgressDialog.isShowing() ){
                    customDotsProgressDialog.show();
                    }
                    break;
                case StaticValues.REMOVE_DILATING_DIALOG_MESSAGE:
                    if( customDotsProgressDialog != null && customDotsProgressDialog.isShowing() ){
                        customDotsProgressDialog.dismiss();
                    }
                    break;
            }
        }
    };
    DlgDone alertDialog;

    AlertDialog loadingDialog;

    DlgDone alertDlgDone;
    DlgDoneCancel notificationDlg;
    protected String loginCachePath;          // 캐쉬파일 경로 .
    protected String systemPropertyPath;


    protected final int TRANS_ANIM_DURATION = 250;
    protected final int TRANS_ANIM_DELAY = 250;
    protected final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restApiClient = new RestApiClient( this );
        loginCachePath = this.getCacheDir().getPath() + StaticValues.USER_CACHE;
        systemPropertyPath = this.getCacheDir().getPath() + StaticValues.SYSTEM_CACHE;


        AlertDialog.Builder networkBuilder = new AlertDialog.Builder(this);
        networkBuilder.setTitle("네트워크 연결 오류 ").setMessage("네트워크 상태를 확인해주세요").setPositiveButton(
                "확인",
                new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        baseHandler.sendEmptyMessage(StaticValues.REMOVE_DIALOG_MESSAGE);
                        redirectNetowrkSetting();
                    }
                }
        ).setOnDismissListener( new AlertDialog.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                baseHandler.sendEmptyMessage(StaticValues.REMOVE_DIALOG_MESSAGE);
            }
        });

        alertDialog= new DlgDone(this ).setTitle( "네트워크 연결 오류").setContent("네트워크 상태를 확인해주세요")
                .setButtonText("확인").setOnListener(
                        new DlgDone.OnListener() {
                            @Override
                            public void onDone(DlgDone dlg) {
                                dlg.dismiss();
                                baseHandler.sendEmptyMessage(StaticValues.REMOVE_DIALOG_MESSAGE);
                               redirectNetowrkSetting();
                            }

                            @Override
                            public void onDismiss(DlgDone dlg) {
                                dlg.dismiss();
                                baseHandler.sendEmptyMessage(StaticValues.REMOVE_DIALOG_MESSAGE);
                                redirectNetowrkSetting();
                            }
                        }
                );

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dilatingDotsProgressDefaultTheme);
        builder.setView( R.layout.dlgprogress_layout);
        loadingDialog = builder.create();

        loadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                DilatingDotsProgressBar progressBar = (DilatingDotsProgressBar)loadingDialog.findViewById(R.id.progress);
                progressBar.show();
            }
        });

        customDotsProgressDialog = new DotsProgressBarDialog( this );
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isPause = false;
        baseHandler.sendEmptyMessage(StaticValues.REMOVE_DILATING_DIALOG_MESSAGE);
    }

    @Override
    protected void onPause() {


        removeLoading();
        super.onPause();
        isPause = true;
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        StringBuilder builder = new StringBuilder();

//        error.printStackTrace();

        if(error == null){
            showToast("알수없는 에러발생");
        }else {
            if(error instanceof NoConnectionError){



                if( !alertDialog.isShowing() ){
                    alertDialog.show();
                }

                Log.e(getClass().getSimpleName(),"NetworkConnectionError");
            }


            if( error.networkResponse != null ) {
                switch (error.networkResponse.statusCode) {
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        builder.append("NOT_FOUND");
                        break;
                    case HttpURLConnection.HTTP_ACCEPTED:
                        builder.append("ACCEPTED");
                        break;
                    case HttpURLConnection.HTTP_BAD_GATEWAY:
                        builder.append("BAD_GATEWAY");
                        break;
                    case HttpURLConnection.HTTP_BAD_METHOD:
                        builder.append("BAD_METHOD");
                        break;
                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        builder.append("BAD_REQUEST");
                        break;
                    case HttpURLConnection.HTTP_FORBIDDEN:
                        builder.append("FORBIDDEN");
                        break;
                    default:
                        builder.append("UNKNOWN ERROR");
                        break;
                }

                showToast(builder.toString() + " code : " + error.networkResponse.statusCode);
            }else {
                showNetworkDialog( "서버에서 응답을 실패했습니다. \n 서버 관리자에게 문의해주세요\n 010-9999-2222");
            }
        }


        if(isLoading())
            removeLoading();
    }

    @Override
    public void onResponse(Object response) {


        removeLoading();
        if(response != null){
            if(response instanceof CommonClass){
                if (!"0000".equals(((CommonClass) response).getCode())){
//                    showToast("에러코드 : " +  ((CommonClass) response).getCode() +"\n"+((CommonClass) response).getMessage());
                    showNetworkDialog(((CommonClass) response).getMessage() );
                }
            }
        }else {
            showNetworkDialog( "서버에서 정상적인 응답을 못받았습니다. \n 서버 관리자에게 문의해주세요\n 010-9999-2222");
        }
    }

    protected void redirectSharedElement(Activity activity, Class<?> cls,int flags[] , Pair<View,String>... pairs  ){
        Intent itt = new Intent( activity, cls );

        for( int i = 0;i< flags.length;i++){
            itt.addFlags(flags[i]);
        }
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        activity.startActivity(itt, transitionActivityOptions.toBundle());

    }


    @Override
    public void onBackPressed() {
        switch ( backPressActionMode ){
            case 1:
                if (doFinish) {
                    finish();
                } else {
                    Toast.makeText(this, "한 번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
                    doFinish = true;
                    baseHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doFinish = false;
                        }
                    }, 2000);
                }
                break;
            case 2:
                if( doBack ){
                    super.onBackPressed();
                }else {
                    Toast.makeText(this, "한 번 더 누르면 뒤로갑니다.", Toast.LENGTH_SHORT).show();
                    Toast customToast;
                    doBack = true;
                    baseHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doBack = false;
                        }
                    }, 2000);
                }
                break;
            default :
                super.onBackPressed();
                break;
        }
    }

    /**
     * 설정으로 이동
     */
    protected void redirectNetowrkSetting() {
        Intent itt = new Intent( android.provider.Settings.ACTION_WIFI_SETTINGS );
        itt.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );  // 이걸 실행해야 설정 화면이 따로 뜸
        startActivityForResult( itt, StaticValues.SYSTEM_SETTING );
    }

    public void showNetworkDialog(String msg){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("알림").setMessage(msg).setPositiveButton(
//                "확인",
//                this
//        );
//        builder.setOnDismissListener( this ).setNegativeButton("취소", null);
//       builder.show();

        alertDlgDone = new DlgDone( this ).setTitle( "알림" ).setContent( msg ).setButtonText("확인").setOnListener(
                new DlgDone.OnListener() {
                    @Override
                    public void onDone(DlgDone dlg) {
                        dlg.dismiss();
                        baseHandler.sendEmptyMessage(StaticValues.REMOVE_DIALOG_MESSAGE);
                    }

                    @Override
                    public void onDismiss(DlgDone dlg) {
                        baseHandler.sendEmptyMessage(StaticValues.REMOVE_DIALOG_MESSAGE);
                    }
                }
        );
        alertDlgDone.show();
    }


    public void showNotiDialog(String title, String msg){
        notificationDlg = new DlgDoneCancel( this ).setTitle( title ).setContent( msg ).setButtonDoneText("확인").setButtonCancelText("취소").setOnListener( this );
        notificationDlg.show();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }




    public void  redirectMainActivityWithAnim(Activity acv){

        if (customDotsProgressDialog != null && customDotsProgressDialog.isShowing()){
            customDotsProgressDialog.dismiss();
        }
        int flags [] = {  Intent.FLAG_ACTIVITY_NEW_TASK};

        Intent i = new Intent(acv,MVPMainActivity.class );
        for(int flag : flags)
            i.addFlags(flag);


//        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(acv, pairs);
//        Log.d("redirectSharedElement",transitionActivityOptions.toBundle().toString());
//        acv.startActivity(i, transitionActivityOptions.toBundle());

        startActivity(i);
        finish();
        overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out );
    }


    protected void setupWindowAnimationSlide() {
        // Re-enter transition is executed when returning to this activity
        if ( StaticValues.IS_LOLLIPOP ) {
            Slide slideLeft = new Slide();
            slideLeft.setSlideEdge( Gravity.LEFT );
            slideLeft.setDuration( TRANS_ANIM_DURATION );
            Slide slideRight = new Slide();
            slideRight.setSlideEdge( Gravity.RIGHT );
            slideRight.setDuration( TRANS_ANIM_DURATION );
            slideRight.excludeTarget( android.R.id.statusBarBackground, true );
            slideRight.excludeTarget( android.R.id.navigationBarBackground, true );
            getWindow().setEnterTransition( slideRight );
            getWindow().setReenterTransition( slideLeft );
            getWindow().setExitTransition( slideLeft );
        }
    }

    protected void setTransEnterSlide() {
        if ( StaticValues.IS_LOLLIPOP ) {
            Slide slide = new Slide();
            slide.setSlideEdge( Gravity.RIGHT );
            slide.setStartDelay( TRANS_ANIM_DELAY );
            slide.setDuration( TRANS_ANIM_DURATION );
            slide.excludeTarget( android.R.id.statusBarBackground, true );
//            slide.excludeTarget( android.R.id.navigationBarBackground, true );
            getWindow().setEnterTransition( slide );
        }
    }

    protected void setTransEnterSlideDown() {
        if ( StaticValues.IS_LOLLIPOP ) {
            Slide slide = new Slide();
            slide.setSlideEdge( Gravity.TOP );
            slide.setStartDelay( TRANS_ANIM_DELAY );
            slide.excludeTarget( android.R.id.statusBarBackground, true );
//            slide.excludeTarget( android.R.id.navigationBarBackground, true );
            slide.setDuration( TRANS_ANIM_DURATION );
            getWindow().setEnterTransition( slide );
        }
    }

    protected void setTransEnterExplode() {
        if ( StaticValues.IS_LOLLIPOP ) {
            Explode explode = new Explode();
            explode.setStartDelay( TRANS_ANIM_DELAY );
            explode.setDuration( TRANS_ANIM_DURATION );
            explode.excludeTarget( android.R.id.statusBarBackground, true );
//            explode.excludeTarget( android.R.id.navigationBarBackground, true );
            getWindow().setEnterTransition( explode );

        }
    }

    protected void setTransExitSlide() {
        if ( StaticValues.IS_LOLLIPOP ) {
            Slide slide = new Slide();
            slide.setSlideEdge( Gravity.LEFT );
            slide.setDuration( TRANS_ANIM_DURATION );
            slide.excludeTarget( android.R.id.statusBarBackground, true );
//            slide.excludeTarget( android.R.id.navigationBarBackground, true );
            getWindow().setExitTransition( slide );
            getWindow().setReenterTransition( slide );
        }
    }

    protected void setTransExitSlideDown() {
        if ( StaticValues.IS_LOLLIPOP ) {
            Slide slide = new Slide();
            slide.setSlideEdge( Gravity.BOTTOM );
            slide.setDuration( TRANS_ANIM_DURATION );
            slide.excludeTarget( android.R.id.statusBarBackground, true );
//            slide.excludeTarget( android.R.id.navigationBarBackground, true );
            getWindow().setExitTransition( slide );
            getWindow().setReenterTransition( slide );
        }
    }

    protected void setTransExitExplode() {
        if ( StaticValues.IS_LOLLIPOP ) {
            Explode explode = new Explode();
            explode.setDuration( TRANS_ANIM_DURATION );
            explode.excludeTarget( android.R.id.statusBarBackground, true );
//            explode.excludeTarget( android.R.id.navigationBarBackground, true );
            getWindow().setExitTransition( explode );
            getWindow().setReenterTransition( explode );
        }
    }


    public void showLoading(){
        baseHandler.sendEmptyMessage(StaticValues.SHOW_DIALOG_MESSAGE);
    }
    public boolean isLoading(){
        if(customDotsProgressDialog == null){
            return false;
        }else {
            return customDotsProgressDialog.isShowing();
        }
    }
    public void removeLoading(){
        baseHandler.sendEmptyMessageDelayed(StaticValues.REMOVE_DILATING_DIALOG_MESSAGE, 0);
    }


    boolean checkPlayServices() {
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int resultCode = googleApi.isGooglePlayServicesAvailable(this);

//                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApi.isUserResolvableError(resultCode)) {
                googleApi.getErrorDialog(this,resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(getClass().getSimpleName(), "This device is not supported.");
                showToast("구글 플레이 서비스를 지원하지 않는 device입니다.");
            }
            return false;
        }
        return true;
    }

    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            Log.d("getInstanceIdToken","getTokenService");
            if(SharedManager.getString(this , StaticValues.FCM_TOKEN).equals("")) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, GCMGetTokenService.class);
                startService(intent);
            }

        }
    }

    @Override
    public void onDone(DlgDone dlg) {

    }

    @Override
    public void onDismiss(DlgDone dlg) {
        dlg.dismiss();
    }

    // Cancleable Interface
    @Override
    public void onDone(DlgDoneCancel dlg) {

    }

    @Override
    public void onCancel(DlgDoneCancel dlg) {
        dlg.dismiss();
    }

    @Override
    public void onDismiss(DlgDoneCancel dlg) {
        dlg.dismiss();
    }

    @Override
    public void finish() {
        baseHandler.removeCallbacksAndMessages( null );
        super.finish();
    }
}
