package tripath.com.qsalesprototypeapp.mvpinterface;

import java.util.List;

/**
 * Created by SSLAB on 2017-08-14.
 */

public interface GetLIstView {
    void showProgress();
    void hideProgress();
    void setItem(List items);
    void goChattingRoom();
}
