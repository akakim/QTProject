package tripath.com.qsalesprototypeapp.wiget;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by SSLAB on 2017-07-07.
 */

public class ViewOffsetHelper {


    private final View mView;
    private int mLayoutTop;
    private int mOffsetTop;

    public ViewOffsetHelper(View view){
        mView = view;
    }

    public void onViewLayout() {
        mLayoutTop = mView.getTop();    // 상단 설정
        updateOffsets();                // 상단과의 간격 설정
    }

    private void updateOffsets() {
        if(Build.VERSION.SDK_INT == 22) {
            ViewCompat.setTranslationY(mView, (float) mOffsetTop);
        } else {
            ViewCompat.offsetTopAndBottom(mView, mOffsetTop - mView.getTop() - mLayoutTop);
        }
    }

    public boolean setTopAndBottomOffset(int offset) {
        if(mOffsetTop != offset) {
            mOffsetTop = offset;
            updateOffsets();
            return true;
        } else {
            return false;
        }
    }

    public int getTopAndBottomOffset() {
        return mOffsetTop;
    }
}
