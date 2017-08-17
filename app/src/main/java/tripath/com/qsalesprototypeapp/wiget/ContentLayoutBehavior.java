package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by SSLAB on 2017-07-07.
 */

public class ContentLayoutBehavior extends ViewOffsetBehavior {

    private static final String TAG = ContentLayoutBehavior.class.getSimpleName();

    public ContentLayoutBehavior() {
    }

    public ContentLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        Log.d(TAG,"onMeasureChild");

        if(child.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT) {
            List dependencies = parent.getDependencies(child);
            if(dependencies.isEmpty()){
                return false;
            }

            HeaderLayout headerLayout = findHeaderLayout(dependencies);
            if(headerLayout != null && ViewCompat.isLaidOut(headerLayout)) {
                int scrollRange = headerLayout.getScrollRange();
                int height = parent.getHeight() - headerLayout.getMeasuredHeight() + scrollRange;
                int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
                parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
                return true;
            }

        }

        return false;


    }
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG,"onDependentViewChanged");
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)dependency.getLayoutParams()).getBehavior();

        int headerOffset = ((ChatRoomScrollBehavior)behavior).getTopAndBottomOffset();
        int contentsOffset = dependency.getHeight() + headerOffset;
        setTopAndBottomOffset(contentsOffset);
        return false;
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG,"layoutDependsOn");
        return dependency instanceof HeaderLayout;
    }

    private static HeaderLayout findHeaderLayout(List<View> views){
        int i = 0;

        // 신기하네.. 이런 스타일이 있는건지 아니면 이렇게 해야만하는건지 ..?
        for(int z = views.size(); i< z; i++){
            View view = (View)views.get(i);
            if(view instanceof HeaderLayout){
                return (HeaderLayout)view;
            }

        }

        return null;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

}
