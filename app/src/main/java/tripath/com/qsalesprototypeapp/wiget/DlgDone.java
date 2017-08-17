package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tripath.com.qsalesprototypeapp.R;

public class DlgDone extends AlertDialog {
    public interface OnListener {
        void onDone(DlgDone dlg);

        void onDismiss(DlgDone dlg);
    }

    private OnListener listener;

    public DlgDone setOnListener(OnListener l ) {
        listener = l;
        return this;
    }

    private TextView textTitle;
    private TextView textContent;
    private Button btnDone;

    public DlgDone( Context context ) {
        super( context );

        View v = View.inflate( context, R.layout.dlg_done, null );

        textTitle = (TextView) v.findViewById( R.id.textTitle );
        textContent = (TextView) v.findViewById( R.id.textContent );
        btnDone = (Button) v.findViewById( R.id.btnDone );
        btnDone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( listener != null ) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDone( DlgDone.this );
                        }
                    }, 200 );

                }
            }
        } );
        setOnDismissListener( new OnDismissListener() {
            @Override
            public void onDismiss( DialogInterface dialog ) {
                if ( listener != null ) {
                    listener.onDismiss( DlgDone.this );
                }
            }
        } );
        setView( v );
    }

    public DlgDone setTitle(String title ) {
        textTitle.setText( title );
        return this;
    }

    public DlgDone setContent(String content ) {
        textContent.setText( content );
        return this;
    }

    public DlgDone setButtonText(String content ) {
        btnDone.setText( content );
        return this;
    }

    public DlgDone setButtonText(int stringId ) {
        btnDone.setText( getContext().getResources().getString( stringId ) );
        return this;
    }

    public DlgDone setOnclickListener(View.OnClickListener l ) {
        if ( l == null ) {
            dismiss();
        } else {
            btnDone.setOnClickListener( l );
        }
        return this;
    }

    public DlgDone setCanCancel(boolean cancelable ) {
        setCancelable( cancelable );
        return this;
    }
}
