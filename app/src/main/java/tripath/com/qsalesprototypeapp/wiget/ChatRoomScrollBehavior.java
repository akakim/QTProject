package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by SSLAB on 2017-07-07.
 */

public class ChatRoomScrollBehavior extends ViewOffsetBehavior<RelativeLayout> {
    private static final String TAG = ChatRoomScrollBehavior.class.getSimpleName();

    private int touchSlop;
    private int maxFlingVelocity;
    private int minFlingVelocity;



    private boolean mIsScrolling;

    private View mTabLayout;

    private int mMinOffset;
    private int mMaxOffset;

    private View mTargetView;

    private int mSkippedOffset;

//    private ViewFlinger mViewFlinger;

    public ChatRoomScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        final ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        touchSlop = viewConfiguration.getScaledTouchSlop();
        maxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        minFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RelativeLayout child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, RelativeLayout child, int parentWidthMeasureSpec,
                                  int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec,
                heightUsed);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RelativeLayout child, View target, int dx, int dy, int[] consumed) {

        Log.d(TAG,"onNestedPreScroll");
        if (!mIsScrolling) {
            Log.d(TAG,"onNestedPreScroll not Scrolled");
            mSkippedOffset += dy;

            if (Math.abs(mSkippedOffset) >= touchSlop) {
                mIsScrolling = true;
                target.getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        /**
         * 스크롤링일째
         */
        if (mIsScrolling && dy != 0) {
            Log.d(TAG,"onNestedPreScroll isScrolled Vertical ");
            int min = -child.getHeight();  // HeaderLayout에 정의된 스크롤을 할 만큼의 범위 지정

            int max = 0;

            int currentOffset = getTopAndBottomOffset();
            int newOffset = Math.min(Math.max(min, currentOffset - dy), max);

            consumed[1] = newOffset - currentOffset;                //??

            setTopAndBottomOffset(newOffset);                       //??
        }

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RelativeLayout child, View directTargetChild, View target, int nestedScrollAxes) {

        // 수직으로 스크롤 하는경우
        if ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0) {
            mTargetView = target;

            mIsScrolling = false;
            mSkippedOffset = 0;

            mMinOffset = child.getHeight();
            mMaxOffset = 0;

            // 트루를 반환하면 뭔가 변화가 있다는 것인가?
            return true;
        }

        return false;
    }


}
