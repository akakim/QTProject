package tripath.com.qsalesprototypeapp.mvpinterface;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.restclient.entity.User;

/**
 * AutoLogin + Logout?
 */

public class LoginPresenterImpl implements LoginPresenter,LoginInteractor,Response.Listener,Response.ErrorListener {


    private Context context;
    private LoginView loginView;

    private RestApiClient client;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.loginView = loginView;
        this.context = context;
        client = new RestApiClient( context );
    }


    @Override
    public void validCredentials(User user) {
        if( loginView != null){
            loginView.showProgress();
        }
        this.login( user );
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if( loginView != null){
            loginView.hideProgress();
        }

        loginView.setLoginError(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {

        if( loginView != null){
            loginView.hideProgress();
        }

        if(response == null){
            loginView.setLoginError("response is null");
        }else if ( response instanceof User){
            User user = ( User ) response ;
            loginView.setCache( response );
            loginView.navigationToHome( user );
        }

    }

    @Override
    public void login(User user) {
        client.loginCheck( user, this ,this );
    }
}
