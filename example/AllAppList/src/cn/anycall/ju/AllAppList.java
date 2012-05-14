package cn.anycall.ju;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import cn.anycall.ju.ScrollLayout.OnScreenChangeListenerDataLoad;

/**
 * GridView分页显示安装的应用程序
 */
public class AllAppList extends Activity {
	private ScrollLayout mScrollLayout;
	private static final float APP_PAGE_SIZE = 4.0f;
	private Context mContext;
	private PageControlView pageControl;
	public MyHandler myHandler;
	public int n=0;
	private DataLoading dataLoad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		setContentView(R.layout.main);
		dataLoad = new DataLoading();
		mScrollLayout = (ScrollLayout)findViewById(R.id.ScrollLayoutTest);
		myHandler = new MyHandler(this,1);
		
		//起一个线程更新数据
		MyThread m = new MyThread();
		new Thread(m).start();
	} 
	
	/**
	 * gridView 的onItemLick响应事件
	 */
	public OnItemClickListener listener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			System.out.println("position="+position);
		}
		
	};
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	// 更新后台数据
	class MyThread implements Runnable {
		public void run() {
			try {
				Thread.sleep(1000*3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String msglist = "1";
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putString("rmsg", msglist);
			msg.setData(b);
			AllAppList.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI

		}
	}

	class MyHandler extends Handler {
		private AllAppList mContext;
		public MyHandler(Context conn,int a) {
			mContext = (AllAppList)conn;
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			String rmsg = b.getString("rmsg");
			if ("1".equals(rmsg)) {
				// do nothing
				 List<Map> list = new ArrayList<Map>();
				 for(int i =0;i<16;i++){
					 n++;
					 Map map = new HashMap();
				        map.put("name", n);
				        list.add(map);
				 }
			        
				int pageNo = (int)Math.ceil( list.size()/APP_PAGE_SIZE);
				for (int i = 0; i < pageNo; i++) {
					GridView appPage = new GridView(mContext);
					// get the "i" page data
					appPage.setAdapter(new AppAdapter(mContext, list, i));
					appPage.setNumColumns(2);
					appPage.setOnItemClickListener(listener);
					mScrollLayout.addView(appPage);
				}
				//加载分页
				pageControl = (PageControlView) findViewById(R.id.pageControl);
				pageControl.bindScrollViewGroup(mScrollLayout);
				//加载分页数据
				dataLoad.bindScrollViewGroup(mScrollLayout);
					
				}
			}

		}
	
	
	//分页数据
	class DataLoading {
		private int count;
		public void bindScrollViewGroup(ScrollLayout scrollViewGroup) {
			this.count=scrollViewGroup.getChildCount();
			scrollViewGroup.setOnScreenChangeListenerDataLoad(new OnScreenChangeListenerDataLoad() {
				public void onScreenChange(int currentIndex) {
					// TODO Auto-generated method stub
					generatePageControl(currentIndex);
				}
			});
		}
		
		private void generatePageControl(int currentIndex){
			//如果到最后一页，就加载16条记录
			if(count==currentIndex+1){
				MyThread m = new MyThread();
				new Thread(m).start();
			}
		}
	}
}
