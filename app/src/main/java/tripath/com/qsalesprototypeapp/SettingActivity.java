package tripath.com.qsalesprototypeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import tripath.com.qsalesprototypeapp.manager.SharedManager;


/**
 * 자동로그인 여부도 확인할 수 있어야하는가 ?
 *
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        TextView tvIdValue = (TextView) findViewById(R.id.tvIdValue);


        File loginCache = new File(loginCachePath);
        if( loginCache.exists() ){
            try{
                InputStream inputStream = new FileInputStream ( loginCache );

                int content = 0;
                StringBuilder builder = new StringBuilder();

                while ((content = inputStream.read()) != -1) {
                    builder.append((char) content);
                }

                JSONObject jsonObject = new JSONObject(builder.toString());

                String id = jsonObject.getString(StaticValues.ID);
                Log.d(TAG,"jsonObject : " + jsonObject.toString());
                tvIdValue.setText( id );
            }catch (IOException e ){
                tvIdValue.setText( " 사용자 정보를 불러오기 실패. ");
            }catch (Exception e ){
                tvIdValue.setText( " 사용자 정보를 불러오기 실패. ");
            }
        }else {
            tvIdValue.setText( " 사용자 정보를 불러오기 실패. ");
        }
//        tvIdValue.setText(SharedManager.getString(this,StaticValues.ID));

        findViewById( R.id.btnLogout).setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogout:


                File loginCache = new File(loginCachePath);
                if ( loginCache.delete() ){
                    showToast("로그아웃합니다.");
                    Intent i = new Intent(this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    break;
                }

                if( !loginCache.exists()){
                    showToast("로그아웃합니다.");
                    Intent i = new Intent(this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    break;
                }

                break;
        }
    }
}
