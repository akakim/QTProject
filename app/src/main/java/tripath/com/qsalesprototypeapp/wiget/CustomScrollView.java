package tripath.com.qsalesprototypeapp.wiget;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by SSLAB on 2017-07-04.
 */

public class CustomScrollView extends ScrollView {
    final String TAG = getClass().getSimpleName();
    Handler childControllHandler;

    private boolean scrollable = true;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    public void setChildControllHandler(Handler childControllHandler){
        this.childControllHandler = childControllHandler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Don't do anything with intercepted touch events if
        // we are not scrollable
        if (!scrollable) return false;
        else return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        if(childControllHandler !=null) {
//
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    for(int i =0;i<getChildCount();i++){
//                        View view = getChildAt(i);
//                        view.invalidate(view.getLeft(),view.getTop(),view.getRight(),view.getBottom()/2);
//                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(view.getLayoutParams());
//                        params.height = params.height/2;
//                        view.setLayoutParams(params);
//                        view.requestLayout();
//                    }
//                }
//            };
//
//
////            Message msg = Message.obtain();
//            childControllHandler.post(runnable);
////            childControllHandler.sendEmptyMessage(0);
//
//
//        }
//        else {
//            Log.e(TAG,"handler is null");
//        }
        fullScroll(ScrollView.FOCUS_DOWN);
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
