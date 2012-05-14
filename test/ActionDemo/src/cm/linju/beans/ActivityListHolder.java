package cm.linju.beans;

import cm.linju.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ActivityListHolder {
	private ImageView activityIcon;
	private ImageView activityPicture;
	private TextView activityTime;
	private TextView activityTitle;
	private TextView activityContent;
	private TextView activityComment;
	private TextView activityNumber;
	private TextView activityScore;
	
	private View baseView;
	
	public ActivityListHolder(View baseView)
	{
		this.baseView = baseView;
	}
	
	public TextView getActivityComment() {
		if(activityComment == null)
		{
			activityComment = (TextView)baseView.findViewById(R.id.activityComment);
		}
		return activityComment;
	}
	
	public TextView getActivityContent() {
		if(activityContent == null)
		{
			activityContent = (TextView)baseView.findViewById(R.id.activityContent);
		}
		return activityContent;
	}

	public ImageView getActivityIcon() {
		if(activityIcon == null)
		{
			activityIcon = (ImageView)baseView.findViewById(R.id.activityIcon);
		}
		return activityIcon;
	}
	
	public ImageView getActivityPicture() {
		if(activityPicture == null)
		{
			activityPicture = (ImageView)baseView.findViewById(R.id.activityPic);
		}
		return activityPicture;
	}
	
	public TextView getActivityTime() {
		if(activityTime == null)
		{
			activityTime = (TextView)baseView.findViewById(R.id.ActivityTime);
		}
		return activityTime;
	}
	

	public TextView getActivityNumber() {
		if(activityNumber == null)
		{
			activityNumber = (TextView)baseView.findViewById(R.id.activityNumber);
		}
		return activityNumber;
	}


	public TextView getActivityTitle() {
		if(activityTitle == null)
		{
			activityTitle = (TextView)baseView.findViewById(R.id.activityTitle);
		}
		return activityTitle;
	}	
	
	public TextView getActivityScore() {
		if(activityScore == null)
		{
			activityScore = (TextView)baseView.findViewById(R.id.activityScore);
		}
		return activityScore;
	}
}
