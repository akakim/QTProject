package tripath.com.qsalesprototypeapp.mvpinterface;


import android.os.Message;

import java.util.List;

/**
 *  RecyclerView와 상호작용을 알려주는 인터페이스
 */
public interface GetListInteractorImplement {

    interface OnFinishListener{
        void onFinished(List items);
    }


    Message findItems(OnFinishListener listener,List items);
}
