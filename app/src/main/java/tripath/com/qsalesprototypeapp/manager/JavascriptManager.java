package tripath.com.qsalesprototypeapp.manager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;

import tripath.com.qsalesprototypeapp.StaticValues;

/**
 * Created by SSLAB on 2017-07-13.
 */

public class JavascriptManager {
    private Handler h;

    public JavascriptManager(Handler h ) {
        this.h = h;
    }

    @JavascriptInterface
    public void Message() {
//        Log.d( "Tag", "java msg : " + msg );
        Message m = Message.obtain();
        m.what = StaticValues.JAVA_SCRIPT_BACK_CALLBACK;
        m.obj = null;
        h.sendMessage( m );
    }

}
