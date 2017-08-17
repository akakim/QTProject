package tripath.com.qsalesprototypeapp.mvpinterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import tripath.com.qsalesprototypeapp.BaseActivity;
import tripath.com.qsalesprototypeapp.FileUtil;
import tripath.com.qsalesprototypeapp.R;
import tripath.com.qsalesprototypeapp.StaticValues;
import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.restclient.entity.User;

/**
 * Created by SSLAB on 2017-08-14.
 */

public class MVPMainActivity extends BaseActivity implements LoginView,View.OnClickListener{

    String TAG = getClass().getSimpleName();
    EditText edId;
    EditText edPwd;
    CheckBox checkboxAutoLogin;
    Handler handler =null;
    ImageView imgLogo;

    LoginPresenterImpl loginPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        backPressActionMode = 1;

        loginPresenter = new LoginPresenterImpl( this, this );

        findViewById(R.id.btnGoSample).setVisibility(View.GONE);
        findViewById(R.id.btnLogin).setOnClickListener( this );

        edId = ( EditText )findViewById(R.id.edId);
        edPwd = ( EditText )findViewById(R.id.edPwd);

        imgLogo = (ImageView)findViewById( R.id.imageLogo);
        edId.setText("jaehoon_park@kolon.com");
        edPwd.setText("1q2w3e4r5t");
        checkboxAutoLogin = ( CheckBox ) findViewById(R.id.checkboxAutoLogin);
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
    public void setLoginError(String msg) {

        showToast(msg);
    }

    @Override
    public void setCache(Object object) {
        if( object != null){
            if( object instanceof User){
                // something set Cahche

                User userObj = (User) object;


                JSONObject jsonObject = new JSONObject();



                try {
                    jsonObject.put(StaticValues.ID,userObj.getAdvisorId());
                    jsonObject.put(StaticValues.ADVISOR_SEQUENCE,userObj.getData());
                    jsonObject.put(StaticValues.PASSWORD,userObj.getPassword());

                    FileUtil.saveCache(new File(loginCachePath), jsonObject);

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (JSONException e ){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void navigationToHome(User user) {

        SharedManager.putString(this,StaticValues.ADVISOR_SEQUENCE,user.getAdvisorId());

        showToast(user.toString());
//        Intent nextGetListView = new Intent( this , MVPGetListActivity.class);
//
//        nextGetListView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(nextGetListView);
//        overridePendingTransition(R.anim.anim_slide_out_left,R.anim.anim_slide_in_right);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()){

            case R.id.btnLogin:

                User user = new User(edId.getText().toString(),
                        edPwd.getText().toString(),SharedManager.getString(this,StaticValues.FCM_TOKEN) );

                loginPresenter.validCredentials(user);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        loginPresenter.onDestroy();
    }
}
