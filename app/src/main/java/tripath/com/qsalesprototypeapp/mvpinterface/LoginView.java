package tripath.com.qsalesprototypeapp.mvpinterface;

import tripath.com.qsalesprototypeapp.restclient.entity.User;

/**
 * Created by SSLAB on 2017-08-14.
 */

public interface LoginView {

    void showProgress();
    void hideProgress();
    void setLoginError(String msg);
    void navigationToHome(User user);
    void setCache(Object object);
}
