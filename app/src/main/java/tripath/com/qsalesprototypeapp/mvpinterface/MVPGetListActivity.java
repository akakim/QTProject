package tripath.com.qsalesprototypeapp.mvpinterface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import tripath.com.qsalesprototypeapp.BaseActivity;
import tripath.com.qsalesprototypeapp.ChatRoomCoordinatorActivity;
import tripath.com.qsalesprototypeapp.GetListActivity;
import tripath.com.qsalesprototypeapp.R;
import tripath.com.qsalesprototypeapp.StaticValues;
import tripath.com.qsalesprototypeapp.fragment.ItemListFragment;
import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.restclient.entity.AuthCode;
import tripath.com.qsalesprototypeapp.wiget.DilatingDotsProgressBar;

public class MVPGetListActivity extends BaseActivity implements View.OnClickListener,GetLIstView{


    ItemListFragment itemListFragment;

    String TAG = getClass().getSimpleName();


    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list);

        backPressActionMode = 1;



        findViewById(R.id.btnRefresh).setOnClickListener( this );
        findViewById(R.id.setting_Layer).setOnClickListener( this );


        Intent i = getIntent();
        Log.d(TAG,"getData String " + i.getDataString());
        String className = i.getStringExtra("twice");

        if ( ChatRoomCoordinatorActivity.class.getSimpleName().equals(className)){
            SharedManager.putString(this, StaticValues.ADVISOR_SEQUENCE,i.getStringExtra("advisorSeq"));

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
                    authCode.setAdvisorSeq(SharedManager.getString(MVPGetListActivity.this,StaticValues.ADVISOR_SEQUENCE));
                    restApiClient.getAuthCodeList(authCode , MVPGetListActivity.this , MVPGetListActivity.this );

                    showNotiDialog("[알림]", SharedManager.getString(MVPGetListActivity.this,StaticValues.MESSAGE));
                }
            }
        };


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgress() {
        showLoading();
    }

    @Override
    public void hideProgress() {
        removeLoading();
    }

    @Override
    public void setItem(List items) {

    }

    @Override
    public void goChattingRoom() {

    }
}
