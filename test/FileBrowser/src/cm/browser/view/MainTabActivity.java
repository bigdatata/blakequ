package cm.browser.view;

import cm.browser.R;
import cm.browser.R.drawable;
import cm.browser.R.layout;
import cm.browser.view.process.RunProcessActivity;
import cm.util.BaseActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class MainTabActivity extends TabActivity {
	private static final String TAB1 = "tab1";
	private static final String TAB2 = "tab2";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintab);
		checkTheStateOfSdcard();
		final TabHost tabHost = this.getTabHost();  
	    final TabWidget tabWidget = tabHost.getTabWidget();
	    tabHost.addTab(tabHost.newTabSpec(TAB1).setIndicator(composeLayout(getResources().getString(R.string.sort_browser))).setContent(new Intent(MainTabActivity.this
	    		, SortActivity.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB2).setIndicator(composeLayout(getResources().getString(R.string.directory_browser))).setContent(new Intent(MainTabActivity.this
	    		, FileBrowserActivity.class)));
	    
	    /**  
         * 下面是设置Tab的背景，可以是颜色，背景图片等  
         */ 
	    for(int i = 0; i < tabWidget.getChildCount(); i++)  
        {  
             View v = tabWidget.getChildAt(i);  
             tabWidget.getChildTabViewAt(i);
            if (tabHost.getCurrentTab() == i) {  
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_tab_left_pressed));  
            } else {  
            	v.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_tab_left_normal)); 
            }  
        }  
	    tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				for(int i=0; i<tabWidget.getChildCount(); i++){
					View v = tabWidget.getChildAt(i);
					 if (tabHost.getCurrentTab() == i) {  
			                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_tab_left_pressed));  
			         } else {  
			            	v.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_tab_left_normal)); 
			         }  
				}
			}
		});
	}
	

	/**  
     * 这个设置Tab标签本身的布局
     */  
    public View composeLayout(String s){  
        LinearLayout layout = new LinearLayout(this);  
        layout.setOrientation(LinearLayout.VERTICAL); 
        layout.setBackgroundResource(R.drawable.tab_background);
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setTextSize(17);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        layout.addView(tv,   
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return layout;  
    }  
    
    private void checkTheStateOfSdcard(){
    	boolean state =  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    	if(!state){
    		new AlertDialog.Builder(MainTabActivity.this)
			.setTitle(getResources().getString(R.string.sdcard_notice))
			.setMessage(getResources().getString(R.string.sdcard_error))  
			.setPositiveButton(getResources().getString(R.string.confirm), new OnClickListener() {  
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}  
			}).create().show() ;
    	}
    }
	
}
