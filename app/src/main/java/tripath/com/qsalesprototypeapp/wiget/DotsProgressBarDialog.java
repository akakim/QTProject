package tripath.com.qsalesprototypeapp.wiget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;

import tripath.com.qsalesprototypeapp.R;

/**
 * Created by SSLAB on 2017-07-17.
 */

public class DotsProgressBarDialog extends Dialog {
    private int DEFAULT_LAYOUT = R.layout.progress_bar;
    DilatingDotsProgressBar dilatingProgressBar = null;

    public DotsProgressBarDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature( Window.FEATURE_NO_TITLE);

        setContentView( DEFAULT_LAYOUT );

        dilatingProgressBar = (DilatingDotsProgressBar )findViewById( R.id.dilatingProgressBar);
        getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ));
        setCancelable(false);
    }



    @Override
    public void show() {
        super.show();
        if(dilatingProgressBar != null){
            Log.d(getClass().getSimpleName(),"progressbar is showing...... ");
            dilatingProgressBar.showNow();
        }else {
            Log.e(getClass().getSimpleName(),"progressbar is not!! shown");
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(dilatingProgressBar != null){
            dilatingProgressBar.hide();
        }
    }


    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    public void setBackGroundColor(int color){
        findViewById( R.id.dialog_background).setBackgroundColor( color );
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }
}
