package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;

import java.util.List;


/**
 * Created by SSLAB on 2017-06-30.
 */

public class NestedWebView extends WebView implements NestedScrollingChild, Handler.Callback {
    final String TAG = getClass().getSimpleName();
    private int mLastY;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private int mNestedOffsetY;
    private NestedScrollingChildHelper mChildHelper;

    public NestedWebView( Context context ) {
        this( context, null );
    }

    public NestedWebView( Context context, AttributeSet attrs ) {
        this( context, attrs, android.R.attr.webViewStyle );
    }

    public NestedWebView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        mChildHelper = new NestedScrollingChildHelper( this );
        setNestedScrollingEnabled( true );
        Log.d(TAG,"requestFocus DowN");
        requestFocus(View.FOCUS_DOWN);
    }

    @Override
      public boolean onTouchEvent( MotionEvent ev ) {
        boolean returnValue = false;

        MotionEvent event = MotionEvent.obtain( ev );

        final int action = MotionEventCompat.getActionMasked( event );
        if ( action == MotionEvent.ACTION_DOWN ) {
            Log.d(TAG,"onTouch Action Up mNestedOffsetY initialize");
            mNestedOffsetY = 0;
        }
        int eventY = ( int ) event.getY();
        event.offsetLocation( 0, mNestedOffsetY );
        switch ( action ) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = mLastY - eventY;
                // NestedPreScroll
                // AppBarLayout의 변화를 막기위해 막음 .
//                if ( dispatchNestedPreScroll( 0, deltaY, mScrollConsumed, mScrollOffset ) ) {
//                    deltaY -= mScrollConsumed[1];
//                    mLastY = eventY - mScrollOffset[1];
//                    event.offsetLocation( 0, -mScrollOffset[1] );
//                    mNestedOffsetY += mScrollOffset[1];
//                }
                returnValue = super.onTouchEvent( event );

                // NestedScroll
                if ( dispatchNestedScroll( 0, mScrollOffset[1], 0, deltaY, mScrollOffset ) ) {
                    event.offsetLocation( 0, mScrollOffset[1] );
                    mNestedOffsetY += mScrollOffset[1];
                    mLastY -= mScrollOffset[1];
                }
                break;
            case MotionEvent.ACTION_DOWN:
                returnValue = super.onTouchEvent( event );
                mLastY = eventY;
                // start NestedScroll
                startNestedScroll( ViewCompat.SCROLL_AXIS_VERTICAL );
                break;
            case MotionEvent.ACTION_UP:
                returnValue = super.onTouchEvent( event );
                break;
            case MotionEvent.ACTION_CANCEL:
                returnValue = super.onTouchEvent( event );
                // end NestedScroll
                stopNestedScroll();
                break;
        }
        return returnValue;
    }

    @Override
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {

        Log.d(TAG,"script : " + script );
        Log.d(TAG,"resultCallback " +resultCallback.toString());

        super.evaluateJavascript(script, resultCallback);
    }


    // Nested Scroll implements
    @Override
    public void setNestedScrollingEnabled( boolean enabled ) {
        mChildHelper.setNestedScrollingEnabled( enabled );
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll( int axes ) {
        return mChildHelper.startNestedScroll( axes );
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll( int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                                         int[] offsetInWindow ) {
        return mChildHelper.dispatchNestedScroll( dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow );
    }

    @Override
    public boolean dispatchNestedPreScroll( int dx, int dy, int[] consumed, int[] offsetInWindow ) {
        return mChildHelper.dispatchNestedPreScroll( dx, dy, consumed, offsetInWindow );
    }

    @Override
    public boolean dispatchNestedFling( float velocityX, float velocityY, boolean consumed ) {
        return mChildHelper.dispatchNestedFling( velocityX, velocityY, consumed );
    }

    @Override
    public boolean dispatchNestedPreFling( float velocityX, float velocityY ) {
        return mChildHelper.dispatchNestedPreFling( velocityX, velocityY );
    }

    @Override
    public void addJavascriptInterface(Object object, String name) {
        Log.d(TAG,"object " + object.toString());
        Log.d(TAG,"name " + name.toString());

        super.addJavascriptInterface(object, name);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
//        Log.d(TAG,"onSizeChange");
//        int height = h;
//        int containerTop = getScrollY();
//        int containerBottom = containerTop + height;
//        int bottom = getBottom();
//
//        Log.d(TAG,"width " + w );
//        Log.d(TAG,"height " + h );
//        Log.d(TAG,"old width " + ow );
//        Log.d(TAG,"old height " + oh );
//
//        Log.d(TAG,"bottom" + bottom );
//        scrollBy(0, bottom);
        super.onSizeChanged(w, h, ow, oh);
    }

    @Override
    public void flingScroll(int vx, int vy) {
        super.flingScroll(vx, vy);
        Log.d(TAG,"flingScroll vx : "+ vx + " vy : " + vy  );
    }

    @Override
    public void postVisualStateCallback(long requestId, VisualStateCallback callback) {
        super.postVisualStateCallback(requestId, callback);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
//        Log.d(TAG,"onCreateInputConnection");
//        return super.onCreateInputConnection(outAttrs);


        return super.onCreateInputConnection(outAttrs);
    }


    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG,"msg what " + msg.what);
        Log.d(TAG,"msg obj " + msg.obj.toString());
        return false;
    }


}
