package cm.linju;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cm.linju.beans.ActivityInfo;
import cm.linju.beans.ActivityListHolder;
import cm.util.AsyncImageLoader;
import cm.util.AsyncImageLoader.ImageCallback;

public class MyAdapter extends BaseAdapter{
	
	public int count=6;//开始显示的条数
	private List<ActivityInfo> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	private AsyncImageLoader asyncImageLoader;
	private HashMap<Integer, View> mapView = new HashMap<Integer, View>();
	public MyAdapter(Context mContext, List<ActivityInfo> mData)
	{
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = mContext;
		this.mData = mData;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		if(count > mData.size())
		{
			return mData.size();
		}
		return count;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(position < 0 || mData.size() <= 0)
		{
			return null;
		}
		ActivityListHolder holder = null;
		View row = mapView.get(position);
		if(row == null)
		{
			row = mInflater.inflate(R.layout.listitem, null);
			holder = new ActivityListHolder(row);
			mapView.put(position, row);
		}
		else 
		{
			return row;
		}
		setListContent(position, holder);
		return row;
	}
	
	/**
	 * 设置list的内容
	 * @param position
	 */
	private void setListContent(int position, ActivityListHolder holder)
	{
		asyncImageLoader = new AsyncImageLoader();
		ActivityInfo infos = mData.get(position);
		if(infos != null)
		{
			//有活动照片否，有则显示，否则不显示
			if(infos.isOtherPicture())
			{
				holder.getActivityPicture().setVisibility(View.VISIBLE);
			}
			else
				holder.getActivityPicture().setVisibility(View.GONE);
			//活动时间,直接放开始时间
			holder.getActivityTime().setText(infos.getBeginTime());
			//题目
			holder.getActivityTitle().setText(infos.getTitle());
			//活动部分内容
			holder.getActivityContent().setText(infos.getDetail());
			//活动评论
			holder.getActivityComment().setText(infos.getReplyNum());
			//参加人数
			holder.getActivityNumber().setText(infos.getParticipationNum());
			//活动评分
			holder.getActivityScore().setText(infos.getScore());
			
			//主题图片,现在先暂时以固定代替?????????????
			holder.getActivityIcon().setImageResource(R.drawable.linju);
			/*
			//主题图片,现在先暂时以固定代替
			Drawable cachedIcon = asyncImageLoader.loadDrawable(infos.getMainPicture(), holder.getActivityPicture(), new ImageCallback() {
				
				public void imageLoaded(Drawable imageDrawable, ImageView imageView,
						String imageUrl) {
					// TODO Auto-generated method stub
					imageView.setImageDrawable(imageDrawable);
				}
			});
			if(cachedIcon == null)
			{
				holder.getActivityPicture().setImageResource(R.drawable.portrait);
			}
			else
			{
				holder.getActivityPicture().setImageDrawable(cachedIcon);
			}
			*/
		}
	}
	
	/*
	 * 还需要修改
	 */
	private boolean isRightTime(String startTime,String endTime)
	{
		Time t = new Time();
		t.setToNow();//设置为当前时间
		int year = t.year;
		int mouth = t.month;
		int data = t.monthDay;
		int hour = t.hour;
		int minute = t.minute;
		//下面是对时间的判断
		return true;
	}

}
