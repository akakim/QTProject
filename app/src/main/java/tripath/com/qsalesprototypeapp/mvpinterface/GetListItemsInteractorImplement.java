package tripath.com.qsalesprototypeapp.mvpinterface;

import android.os.Message;

import java.util.List;

/**
 * Created by SSLAB on 2017-08-14.
 */

public class GetListItemsInteractorImplement implements GetListInteractorImplement {

    @Override
    public Message findItems(final OnFinishListener listener,final List items) {

        Message msg = Message.obtain();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                listener.onFinished( items );
            }
        };

        msg.what = 4000;
        msg.obj = runnable;

        return msg;
    }
}
