package cm.browser.view.process;

import cm.browser.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class ProcessTabActivity extends TabActivity {
	private static final String TAB1 = "tab1";
	private static final String TAB2 = "tab2";
	private static final String TAB3 = "tab3";
	private static final String TAB4 = "tab4";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintab);
		
		final TabHost tabHost = this.getTabHost();  
	    final TabWidget tabWidget = tabHost.getTabWidget();
	    Resources res = getResources();
	    tabHost.addTab(tabHost.newTabSpec(TAB1).setIndicator(composeLayout(res.getString(R.string.run_process))).setContent(new Intent(ProcessTabActivity.this
	    		, RunProcessActivity.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB2).setIndicator(composeLayout(res.getString(R.string.sort_process))).setContent(new Intent(ProcessTabActivity.this
	    		, SortProcessActivity.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB3).setIndicator(composeLayout(res.getString(R.string.run_service))).setContent(new Intent(ProcessTabActivity.this
	    		, BackServiceActivity.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB4).setIndicator(composeLayout(res.getString(R.string.system_state))).setContent(new Intent(ProcessTabActivity.this
	    		, SystemStateActivity.class)));
	    setDefaultTab(TAB1);
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
        tv.setTextSize(15);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        layout.addView(tv,   
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 20));
        return layout;  
    }
}
