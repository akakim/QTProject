package tripath.com.qsalesprototypeapp.mvpinterface;

import tripath.com.qsalesprototypeapp.restclient.entity.User;

/**
 * Created by SSLAB on 2017-08-16.
 */

public interface LoginPresenter {

    void validCredentials(User user);
    void onDestroy();

}
