package cm.exchange.util;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import cm.exchange.R;

/**
 * 
 * @author qh
 *
 */
public abstract class BaseTabActivity extends TabActivity implements PrimaryActivity ,OnClickListener{
	
	public ImageButton left;
	public ImageButton right;
	public TextView titleText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		initTabButton();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initTabButton();
		initTitle();
	}
	
	/**
	 * create the button in the bottom 
	 * @param baseView the parent view include the button
	 * @param titles the title array of button
	 * @param drawables the drawable array of botton
	 */
	public final void createBottom(View baseView, String[] titles, int[] drawables){
		int length = titles.length;
		if (length>3) {
			return;
		}
		int i;
		RadioButton[] buttons = new RadioButton[] {
				(RadioButton) baseView.findViewById(R.id.mygoods_radio_button0),
				(RadioButton) baseView.findViewById(R.id.mygoods_radio_button1),
				(RadioButton) baseView.findViewById(R.id.mygoods_radio_button2)};
		for (i = 0; i < length; i++) {
			buttons[i].setText(titles[i]);
			buttons[i].setCompoundDrawablesWithIntrinsicBounds(0,drawables[i], 0, 0 );
		}
	}

	/**
	 * the method when clicked right button of title
	 */
	@Override
	public void onRightButtonClicked() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeTitle(int leftId, int midId, int rightId) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Initialize the tab widget button
	 */
	public abstract void initTabButton();

}
