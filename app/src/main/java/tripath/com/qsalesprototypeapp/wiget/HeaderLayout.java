package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tripath.com.qsalesprototypeapp.R;

/**
 * Created by SSLAB on 2017-07-10.
 */

public class HeaderLayout extends RelativeLayout {
    private TextView mTextView;


    public HeaderLayout(Context context) {
        super(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    public int getScrollRange() {
        return getHeight() - mTextView.getHeight();
    }

}
