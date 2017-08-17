package tripath.com.qsalesprototypeapp;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;

import tripath.com.qsalesprototypeapp.fragment.ItemListFragment;
import tripath.com.qsalesprototypeapp.gcm.MyGcmListenerService;
import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.restclient.entity.AdvisorInfoItem;
import tripath.com.qsalesprototypeapp.restclient.entity.AuthCode;
import tripath.com.qsalesprototypeapp.restclient.entity.CommonClass;
import tripath.com.qsalesprototypeapp.wiget.DilatingDotsProgressBar;
import tripath.com.qsalesprototypeapp.wiget.DlgDone;
import tripath.com.qsalesprototypeapp.wiget.DlgDoneCancel;

public class GetListActivity extends BaseActivity implements ItemListFragment.OnListFragmentInteractionListener,View.OnClickListener{

    RestApiClient restApiClient;



    ItemListFragment itemListFragment;

    String TAG = getClass().getSimpleName();


    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list);
        backPressActionMode = 1;
//        setTransEnterSlide();
//        setTransExitSlide();


        findViewById(R.id.btnRefresh).setOnClickListener( this );
        findViewById(R.id.setting_Layer).setOnClickListener( this );
        dilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById( R.id.progress);
        restApiClient = new RestApiClient(this);


        Intent i = getIntent();
        Log.d(TAG,"getData String " + i.getDataString());
        String className = i.getStringExtra("twice");
        Log.d(TAG,"className : " + className);

        if ( ChatRoomCoordinatorActivity.class.getSimpleName().equals(className)){
            SharedManager.putString(this,StaticValues.ADVISOR_SEQUENCE,i.getStringExtra("advisorSeq"));

            Intent twice = new Intent( this , ChatRoomCoordinatorActivity.class);
            twice.putExtra("key", i.getStringExtra("key") );
            Log.d(TAG,"key  : " + i.getStringExtra("key"));
            Log.d(TAG,"identifyNumber " + i.getStringExtra("advisorSeq") );

            twice.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP );
            startActivity( twice );
            overridePendingTransition( R.anim.anim_slide_in_right , R.anim.anim_slide_out_left );

        }
        itemListFragment = new ItemListFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.layoutContainer, itemListFragment, StaticValues.FragmentTAG.ITEM_LIST_FRAGMENT)
                .commit();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                Log.d(TAG,"getAction");
                if(action.equals(StaticValues.REFRESH_CHATROOMS)){
                    AuthCode authCode = new AuthCode();
                    authCode.setAdvisorSeq(SharedManager.getString(GetListActivity.this,StaticValues.ADVISOR_SEQUENCE));
                    restApiClient.getAuthCodeList(authCode , GetListActivity.this , GetListActivity.this );

                    showNotiDialog("[알림]", SharedManager.getString(GetListActivity.this,StaticValues.MESSAGE));
                }
            }
        };




    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(StaticValues.REFRESH_CHATROOMS));
        AuthCode authCode = new AuthCode();
        authCode.setAdvisorSeq(SharedManager.getString(this,StaticValues.ADVISOR_SEQUENCE));
        restApiClient.getAuthCodeList(authCode , this , this );

    }

    @Override
    public void onListFragmentInteraction(final String item) {


        Intent intent = new Intent(GetListActivity.this,ChatRoomCoordinatorActivity.class);
        intent.putExtra("key",item);
        startActivity(intent);

    }

    @Override
    public void onRefreshEnd() {
        /**
         * 로딩창을 분명히 핸들러로 지우는게 들어갔는데도 불구하고,
         * 새로고침시 ,
         * 로딩창이 사라지지않는다.
         * 필요 없는 메세지가 더 들어갈 수도 있음에도 불구하고
         * 한번 더넣었다.
         *
         * 무슨 현상이라 명확하게 정의할 수 없다. 다만 안넣으면
         * 로딩창이 사라지지않는 문제가 발생한다.
         */
        removeLoading();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
    }

    @Override
    public void onResponse(Object response) {
        super.onResponse(response);

        if(response != null) {
            if ("0000".equals(((CommonClass) response).getCode())) {
                if (response instanceof AuthCode) {
                    AuthCode authCode = (AuthCode) response;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("codes", authCode.getDataList());
                    itemListFragment.refreshArgs(bundle);

                }
            }
        }
    }

    @Override
    public void onClick(View v) {


        switch ( v.getId()){
            case R.id.btnRefresh:
                String identifyNumber = SharedManager.getString(this,StaticValues.ADVISOR_SEQUENCE);
                AuthCode authCode = new AuthCode();
                authCode.setAdvisorSeq(identifyNumber);

                restApiClient.getAuthCodeList(authCode,this,this);
                customDotsProgressDialog.show();
//                dilatingDotsProgressBar.show();
                break;
            case R.id.setting_Layer:
                startActivity(new Intent( this, SettingActivity.class));
                break;
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    public void onDone(DlgDone dlg) {
        dlg.dismiss();
        Intent intent = new Intent(GetListActivity.this,ChatRoomCoordinatorActivity.class);
        intent.putExtra("key",SharedManager.getString(this,StaticValues.CHATROOM_AUTH_CODE));
        startActivity(intent);
    }

    @Override
    public void onDismiss(DlgDone dlg) {
        super.onDismiss(dlg);
    }

    @Override
    public void onDone(DlgDoneCancel dlg) {
        dlg.dismiss();
        Intent intent = new Intent(GetListActivity.this,ChatRoomCoordinatorActivity.class);
        intent.putExtra("key",SharedManager.getString(this,StaticValues.CHATROOM_AUTH_CODE));
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(intent);
    }

    @Override
    public void onCancel(DlgDoneCancel dlg) {
        super.onCancel(dlg);
    }

    @Override
    public void onDismiss(DlgDoneCancel dlg) {
        super.onDismiss(dlg);
    }
}
