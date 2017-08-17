package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by SSLAB on 2017-07-07.
 */

public class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V>{

    private final String TAG = getClass().getSimpleName();
    private ViewOffsetHelper mViewOffsetHelper;
    private int mTempTopBottomOffset = 0;


    public ViewOffsetBehavior() {
        Log.d(TAG,"Constructor()");
    }

    public ViewOffsetBehavior(Context context, AttributeSet attrs) {


        super(context, attrs);
        Log.d(TAG,"Constructor(context,Attrs )");
    }

    /**
     * 초기화 + 지속적으로 사용하는 거같다.
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        Log.d(TAG,"onLayoutChild");
        parent.onLayoutChild(child,layoutDirection);

        if(mViewOffsetHelper == null) {
            mViewOffsetHelper = new ViewOffsetHelper(child);
        }

        if(mTempTopBottomOffset != 0) {
            mViewOffsetHelper.setTopAndBottomOffset(mTempTopBottomOffset);
            mTempTopBottomOffset = 0;
        }

        return true;

    }

    public boolean setTopAndBottomOffset(int offset) {
        Log.d(TAG,"setTopAndBottomOffset");
        if(mViewOffsetHelper != null) {
            return mViewOffsetHelper.setTopAndBottomOffset(offset);
        } else {
            mTempTopBottomOffset = offset;
            return false;
        }
    }

    public int getTopAndBottomOffset() {
        return mViewOffsetHelper != null?mViewOffsetHelper.getTopAndBottomOffset():0;
    }

}
