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
	
	public int count=6;//��ʼ��ʾ������
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
	 * ����list������
	 * @param position
	 */
	private void setListContent(int position, ActivityListHolder holder)
	{
		asyncImageLoader = new AsyncImageLoader();
		ActivityInfo infos = mData.get(position);
		if(infos != null)
		{
			//�л��Ƭ��������ʾ��������ʾ
			if(infos.isOtherPicture())
			{
				holder.getActivityPicture().setVisibility(View.VISIBLE);
			}
			else
				holder.getActivityPicture().setVisibility(View.GONE);
			//�ʱ��,ֱ�ӷſ�ʼʱ��
			holder.getActivityTime().setText(infos.getBeginTime());
			//��Ŀ
			holder.getActivityTitle().setText(infos.getTitle());
			//���������
			holder.getActivityContent().setText(infos.getDetail());
			//�����
			holder.getActivityComment().setText(infos.getReplyNum());
			//�μ�����
			holder.getActivityNumber().setText(infos.getParticipationNum());
			//�����
			holder.getActivityScore().setText(infos.getScore());
			
			//����ͼƬ,��������ʱ�Թ̶�����?????????????
			holder.getActivityIcon().setImageResource(R.drawable.linju);
			/*
			//����ͼƬ,��������ʱ�Թ̶�����
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
	 * ����Ҫ�޸�
	 */
	private boolean isRightTime(String startTime,String endTime)
	{
		Time t = new Time();
		t.setToNow();//����Ϊ��ǰʱ��
		int year = t.year;
		int mouth = t.month;
		int data = t.monthDay;
		int hour = t.hour;
		int minute = t.minute;
		//�����Ƕ�ʱ����ж�
		return true;
	}

}
