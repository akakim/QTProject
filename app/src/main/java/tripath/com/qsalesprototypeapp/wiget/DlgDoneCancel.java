package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tripath.com.qsalesprototypeapp.R;


public class DlgDoneCancel extends AlertDialog {
    public interface OnListener {
        void onDone(DlgDoneCancel dlg);

        void onCancel(DlgDoneCancel dlg);

        void onDismiss(DlgDoneCancel dlg);
    }

    private OnListener listener;

    public DlgDoneCancel setOnListener(OnListener l ) {
        listener = l;
        return this;
    }

    private TextView textTitle;
    private TextView textContent;
    private Button btnDone;
    private Button btnCancel;

    public DlgDoneCancel( Context context ) {
        super( context );

        View v = View.inflate( context, R.layout.dlg_done_cancel, null );

        textTitle = (TextView) v.findViewById( R.id.textTitle );
        textContent = (TextView) v.findViewById( R.id.textContent );
        btnDone = (Button) v.findViewById( R.id.btnDone );
        btnCancel = (Button) v.findViewById( R.id.btnCancel );
        btnDone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( listener != null ) {
                    listener.onDone( DlgDoneCancel.this );
                }
            }
        } );
        btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( listener != null ) {
                    listener.onCancel( DlgDoneCancel.this );
                }
            }
        } );
        setOnDismissListener( new OnDismissListener() {
            @Override
            public void onDismiss( DialogInterface dialog ) {
                if ( listener != null ) {
                    listener.onDismiss( DlgDoneCancel.this );
                }
            }
        } );
        setView( v );
    }

    public DlgDoneCancel setTitle(String title ) {
        textTitle.setText( title );
        return this;
    }

    public DlgDoneCancel setContent(String content ) {
        textContent.setText( content );
        return this;
    }

    public DlgDoneCancel setButtonDoneText(String content ) {
        btnDone.setText( content );
        return this;
    }

    public DlgDoneCancel setButtonDoneText(int stringId ) {
        btnDone.setText( getContext().getResources().getString( stringId ) );
        return this;
    }

    public DlgDoneCancel setButtonCancelText(String content ) {
        btnCancel.setText( content );
        return this;
    }

    public DlgDoneCancel setButtonCancelText(int stringId ) {
        btnCancel.setText( getContext().getResources().getString( stringId ) );
        return this;
    }

    public DlgDoneCancel setOnclickListener(View.OnClickListener l ) {
        if ( l == null ) {
            dismiss();
        } else {
            btnDone.setOnClickListener( l );
        }
        return this;
    }
    public DlgDoneCancel setCanCancel(boolean cancelable) {
        setCancelable( cancelable );
        return this;
    }
}
