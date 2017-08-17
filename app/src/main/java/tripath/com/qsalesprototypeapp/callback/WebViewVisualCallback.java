package tripath.com.qsalesprototypeapp.callback;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by SSLAB on 2017-06-30.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class WebViewVisualCallback extends WebView.VisualStateCallback {
    OnCompleteAfter onCompleteAfter;

    public interface OnCompleteAfter{
        public void onCompleteAfter();
    }

    public WebViewVisualCallback() {
        this(null);
    }

    public WebViewVisualCallback(OnCompleteAfter onCompleteAfter) {
        this.onCompleteAfter = onCompleteAfter;
    }
    @Override
    public void onComplete(long requestId) {
        Log.d(getClass().getSimpleName(),"get request id : " + requestId);
        if(onCompleteAfter != null){
            onCompleteAfter.onCompleteAfter();
        }
    }
}
