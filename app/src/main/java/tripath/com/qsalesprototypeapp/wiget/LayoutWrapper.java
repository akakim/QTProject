package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by SSLAB on 2017-07-06.
 */

public class LayoutWrapper extends LinearLayout implements NestedScrollingChild {

    public LayoutWrapper(Context context) {
        super(context);
    }

    public LayoutWrapper(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutWrapper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LayoutWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {

    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return false;
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return false;
    }

    @Override
    public void stopNestedScroll() {

    }

    @Override
    public boolean hasNestedScrollingParent() {
        return false;
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return false;
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return false;
    }
}
