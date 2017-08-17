package tripath.com.qsalesprototypeapp.wiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import tripath.com.qsalesprototypeapp.R;

public class ButtonTextAndImg extends RelativeLayout {
	private final int GRAY = 0;
	private final int PURPLE = 1;

	private ImageView img;
	private TextView text;

	private AQuery aq;

	public ButtonTextAndImg(Context context, AttributeSet attrs ) {
		super( context, attrs );
		LayoutInflater.from( context ).inflate( R.layout.button_text_image, this, true );
		aq = new AQuery( context );
		img = (ImageView) findViewById( R.id.img );
		text = (TextView) findViewById( R.id.text );
		TypedArray array = context.getTheme().obtainStyledAttributes( attrs, R.styleable.ButtonTextAndImg, 0, 0 );
		try {
			aq.id( img ).image( array.getDrawable( R.styleable.ButtonTextAndImg_src ) );
		} catch ( Exception e ) {
		}
		try {
			float imgSize = array.getDimension( R.styleable.ButtonTextAndImg_img_size, 10 );
			aq.id( img ).height( ( int ) imgSize, false ).width( ( int ) imgSize, false );
		} catch ( Exception e ) {
		}
		try {
			String str = array.getString( R.styleable.ButtonTextAndImg_text );
			if ( str == null || str.length() == 0 ) {
				text.setVisibility( GONE );
			} else {
				text.setVisibility( VISIBLE );
				text.setText( str );
			}
		} catch ( Exception e ) {
		}
		try {
			float textSize = array.getDimension( R.styleable.ButtonTextAndImg_text_size, 10 );
			text.setTextSize( TypedValue.COMPLEX_UNIT_PX, textSize );
		} catch ( Exception e ) {

		}
		try {
			float textMargin = array.getDimension( R.styleable.ButtonTextAndImg_text_margin, 10 );
			text.setPadding( ( int ) textMargin, 0, 0, 0 );
		} catch ( Exception e ) {

		}
		try {
			int textColor = array.getColor( R.styleable.ButtonTextAndImg_text_color, Color.WHITE );
			text.setTextColor( textColor );
		} catch ( Exception e ) {

		}
	}

	private void setButtonBg() {

	}

	public void setText(String str) {
		text.setVisibility( VISIBLE );
		text.setText( str );
	}

	public TextView getTextView() {
		return text;
	}
}
