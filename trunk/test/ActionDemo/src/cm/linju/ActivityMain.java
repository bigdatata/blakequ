package cm.linju;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ActivityMain extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);
        
        final TabHost th = this.getTabHost();
        th.addTab(th.newTabSpec("MY_JOIN")
        		.setIndicator("MY_JOIN")
        		.setContent(new Intent(this, MyJoinActivity.class)));
        th.addTab(th.newTabSpec("MY_CREATE")
        		.setIndicator("MY_CREATE")
        		.setContent(new Intent(this, MyCreateActivity.class)));
        th.addTab(th.newTabSpec("COMMAND")
        		.setIndicator("COMMAND")
        		.setContent(new Intent(this, RecommandActivity.class)));
        th.addTab(th.newTabSpec("FRIEND_JOIN")
        		.setIndicator("FRIEND_JOIN")
        		.setContent(new Intent(this, FriendJoinActivity.class)));
        setDefaultTab("MY_JOIN");
        
        RadioGroup mainGroup = (RadioGroup)findViewById(R.id.main_radio);
        mainGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				Log.d("radiou group", "you selected="+checkedId);
				switch(checkedId)
				{
				case R.id.radio_button0:
					th.setCurrentTabByTag("MY_JOIN");
					break;
				case R.id.radio_button1:
					th.setCurrentTabByTag("MY_CREATE");
					break;
				case R.id.radio_button2:
					th.setCurrentTabByTag("COMMAND");
					break;
				case R.id.radio_button3:
					th.setCurrentTabByTag("FRIEND_JOIN");
					break;
				}
			}
		});
        
    }
}