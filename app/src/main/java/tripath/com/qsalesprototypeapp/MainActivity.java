package tripath.com.qsalesprototypeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Timer;

import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.restclient.entity.CommonClass;
import tripath.com.qsalesprototypeapp.restclient.entity.User;
import tripath.com.qsalesprototypeapp.wiget.DilatingDotsProgressBar;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    final String TAG = this.getClass().getSimpleName();


    EditText edId;
    EditText edPwd;
    CheckBox checkboxAutoLogin;
    Handler handler =null;
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_main);

        backPressActionMode = 1;
        // backButton에 대한 액션 정의 .


        dilatingDotsProgressBar = (DilatingDotsProgressBar )findViewById( R.id.progress);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch ( msg.what){
                    case 0:

                        break;
                }
            }
        };

//        findViewById(R.id.btnGoSample).setOnClickListener( this );

        findViewById(R.id.btnGoSample).setVisibility(View.GONE);
        findViewById(R.id.btnLogin).setOnClickListener( this );

        edId = ( EditText )findViewById(R.id.edId);
        edPwd = ( EditText )findViewById(R.id.edPwd);

        imgLogo = (ImageView)findViewById( R.id.imageLogo);
        edId.setText("jaehoon_park@kolon.com");
        edPwd.setText("1q2w3e4r5t");
        checkboxAutoLogin = ( CheckBox ) findViewById(R.id.checkboxAutoLogin);

//        registerInBackground();


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch ( view.getId()){
            case R.id.btnGoSample:
                intent = new Intent (this ,QSampleActivity.class);
                startActivity( intent );
                break;

            case R.id.btnLogin:

                findViewById(R.id.btnLogin).setClickable(false);
                User user = new User(edId.getText().toString(),
                        edPwd.getText().toString(),SharedManager.getString(this,StaticValues.FCM_TOKEN) );
                restApiClient.loginCheck(user, this ,this );
                customDotsProgressDialog.show();
                Log.d(TAG,"user class TAG " + user.hashCode() );

                break;
            case R.id.checkboxAutoLogin:
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
        findViewById(R.id.btnLogin).setClickable(true);
    }

    @Override
    public void onResponse(Object response) {
        super.onResponse(response);

        removeLoading();

        if(response != null) {
            String responseCode = (((CommonClass) response).getCode());
            if ("0000".equals(responseCode)) {

                if (response instanceof User) {
                    User user = (User) response;
                    Log.d(TAG," user : " + user.toString());
                    String code = user.getCode();

                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(StaticValues.ADVISOR_SEQUENCE, user.getData());
                    editor.commit();

                    String get = sharedPreferences.getString(StaticValues.ADVISOR_SEQUENCE, "");


                    File loginCache = new File(loginCachePath);

                    try {
                        if (!loginCache.exists()) {
                            loginCache.createNewFile();

                            if (loginCache.canWrite()) {
                                FileOutputStream outputStream = new FileOutputStream(loginCache);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put(StaticValues.ID, edId.getText().toString());
                                jsonObject.put(StaticValues.PASSWORD, edPwd.getText().toString());
                                jsonObject.put(StaticValues.AUTO_LOGIN, checkboxAutoLogin.isChecked());
                                outputStream.write(jsonObject.toString().getBytes());
                                Log.d(TAG, "cacheCreated data : " + jsonObject.toString());
                                outputStream.close();
                            } else {
                                Log.e(TAG, "foo.. it can not write");
                            }
                        }else {
                            if (loginCache.canWrite()) {

                                FileOutputStream outputStream = new FileOutputStream(loginCache);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put(StaticValues.ID, edId.getText().toString());
                                jsonObject.put(StaticValues.PASSWORD, edPwd.getText().toString());
                                jsonObject.put(StaticValues.AUTO_LOGIN, checkboxAutoLogin.isChecked());
                                outputStream.write(jsonObject.toString().getBytes());
                                Log.d(TAG, "cacheCreated data : " + jsonObject.toString());


                            } else {
                                Log.e(TAG, "foo.. it can not write");
                            }
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "create file failed");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    int flags [] = new int[] {Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK };
                    Intent i = new Intent(this,GetListActivity.class );
//                    i.putExtra()
                    for(int flag : flags)
                        i.addFlags(flag);

                    startActivity(i);
                    overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_slide_out_top );
                }

            }
        }

    }
}
