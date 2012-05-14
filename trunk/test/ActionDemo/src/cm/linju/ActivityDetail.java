package cm.linju;



import cm.linju.beans.ActivityInfo;
import cm.linju.beans.UserInfo;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 *  活动详细页面
 * @author Administrator
 *
 */
public class ActivityDetail extends Activity implements OnClickListener{
	private View title;//标题
	private Button back, home;//标题上的两个按钮
	private TextView titletext;//标题文本
	private TextView tab_discuss, tab_attention, tab_join, tab_score, tab_change; //五个tab
	private View activity_profile,content_layout,button_three, activity_bottom_bar;
	private ImageView activity_detail_icon;//活动主题图片
	private TextView activity_title, activity_type, activity_time, activity_location;//活动具体的内容
	private Button comment_num, look_num, detail_sponser;//下面的三个按键
	private Button creater, attention_creater;
	private Gallery gallery;
	
	private ActivityInfo ai;
	private Spannable sp;
	private UserInfo user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//获取传递信息
		ai = (ActivityInfo) this.getIntent().getSerializableExtra("ai");
		setContentView(R.layout.activity_detail);
		this.getAllView();
		
		
	}
	
	private void getAllView()
	{
		title = findViewById(R.id.detailActivity_title);
		//设置标题的内容
		titletext = (TextView)title.findViewById(R.id.title_bt_textView);
		titletext.setText(R.string.activity_content);
		back = (Button)title.findViewById(R.id.title_bt_left);
		back.setOnClickListener(this);
		back.setText(R.string.back);
		home = (Button)title.findViewById(R.id.title_bt_right);
		home.setOnClickListener(this);
		home.setText(R.string.activity_home);
		
		activity_profile = findViewById(R.id.activity_profile);
		activity_detail_icon = (ImageView)activity_profile.findViewById(R.id.activity_detail_icon);
//		activity_detail_icon.setImageDrawable(drawable);//设置图片？？？？？？？？
		activity_detail_icon.setImageResource(R.drawable.linju);//暂时代替
		creater = (Button)activity_profile.findViewById(R.id.activity_detail_creater);
		creater.setOnClickListener(this);
		creater.setText(ai.getCreateUser());//设置创建者
		attention_creater = (Button)activity_profile.findViewById(R.id.activity_detail_attention);
		attention_creater.setOnClickListener(this);
		
		//还需要设置活动具体内容
		content_layout = findViewById(R.id.content_layout);
		activity_title = (TextView)content_layout.findViewById(R.id.activity_detail_content);
		activity_title.setText(R.string.title_content);
		activity_title.append(ai.getDetail());
		sp = (Spannable) activity_title.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray)), 0, 5,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		activity_type = (TextView)content_layout.findViewById(R.id.activity_detail_type);
		activity_type.setText(R.string.title_type);
		activity_type.append(ai.getType());
		sp = (Spannable) activity_type.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray)), 0, 5,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		activity_time = (TextView)content_layout.findViewById(R.id.activity_detail_time);
		activity_time.setText(R.string.title_time);
		activity_time.append(ai.getBeginTime() + "--" + ai.getEndTime());
		sp = (Spannable) activity_time.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray)), 0, 5,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		activity_location = (TextView)content_layout.findViewById(R.id.activity_detail_location);
		activity_location.setText(R.string.title_place);
		activity_location.append(ai.getPlace());
		sp = (Spannable) activity_location.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray)), 0, 5,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		//三个反馈统计
		button_three = findViewById(R.id.button_three);
		comment_num = (Button)findViewById(R.id.activity_detail_comment_num);
		comment_num.setOnClickListener(this);
		comment_num.setText(R.string.comment);
		comment_num.append(ai.getReplyNum());
		look_num = (Button)findViewById(R.id.activity_detail_look_num);
		look_num.setOnClickListener(this);
		look_num.setText(R.string.scan);
		look_num.append(ai.getViewNum());
		detail_sponser = (Button)findViewById(R.id.activity_detail_sponser);
		detail_sponser.setOnClickListener(this);
		
		//还需要设置适配器，先需要判断是否需要显示？？？？？？？？？？？？？
		if(ai.isOtherPicture())
		{
			gallery = (Gallery)findViewById(R.id.gallery);
			gallery.setVisibility(View.VISIBLE);
			gallery.setAdapter(new ImageAdapter(this));
			gallery.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
//					Drawable image = (Drawable)gallery.getItemAtPosition(position);
//					ActivityDetail.this.openPopupwin(image);
					System.out.println("..."+gallery.getItemAtPosition(position));
					ActivityDetail.this.openPopupwin(getResources().getDrawable(R.drawable.sample_5));
					// TODO Auto-generated method stub
					Toast.makeText(ActivityDetail.this, "" + position, Toast.LENGTH_SHORT).show();
				}
			});
		}
		//还需要设置适配器
		
		activity_bottom_bar = findViewById(R.id.activity_bottom_bar);
		tab_discuss = (TextView)activity_bottom_bar.findViewById(R.id.tab_discuss);
		tab_discuss.setOnClickListener(this);
		tab_attention = (TextView)activity_bottom_bar.findViewById(R.id.tab_attention);
		tab_attention.setOnClickListener(this);
		tab_join = (TextView)activity_bottom_bar.findViewById(R.id.tab_join);
		tab_join.setOnClickListener(this);
		tab_score = (TextView)activity_bottom_bar.findViewById(R.id.tab_score);
		tab_score.setOnClickListener(this);
		tab_change = (TextView)activity_bottom_bar.findViewById(R.id.tab_change);
		tab_change.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
//		int tag = (Integer) v.getTag();
//		System.out.println("tag: " + tag);
		switch(v.getId())
		{
		case R.id.title_bt_left:
			Toast.makeText(this, "返回", 3000).show();
			//如果用户点击了评论或者浏览就需要记录下来，传到服务器，设置返回setResult()
			finish();
			break;
		case R.id.title_bt_right:
			Toast.makeText(this, "主页", 3000).show();
			//回到主页
			break;
		case R.id.tab_discuss:
			Toast.makeText(this, "讨论", 3000).show();
			//进入讨论
			break;
		case R.id.tab_attention:
			Toast.makeText(this, "关注", 3000).show();
			//关注数加以，将活动的id加入user的关注活动里面
			break;
		case R.id.tab_join:
			Toast.makeText(this, "你已经成功加入", 3000).show();
			//加入活动，弹出dialog提醒是否立即讨论，或者查看活动参与人数列表等
			break;
		case R.id.tab_score:
			Toast.makeText(this, "评分", 3000).show();
			//进入评分Dialog
			break;
		case R.id.tab_change:
			Toast.makeText(this, "修改", 3000).show();
			//要设置权限，只有创建者才能修改(Tab动态显示，设置gone)
			break;
		case R.id.activity_detail_comment_num:
			Toast.makeText(this, "评论数", 3000).show();
			//点击进入评论页面
			break;
		case R.id.activity_detail_look_num:
			Toast.makeText(this, "查看数", 3000).show();
			int viewNum = Integer.parseInt(ai.getViewNum());
			viewNum ++;
			ai.setViewNum(viewNum + "");
			//点击进入查看页面
			break;
		case R.id.activity_detail_sponser:
			if(ai.isSponsor())
			{
				Toast.makeText(this, "赞助商！", 3000).show();
				//进入赞助商页面
			}
			else
			{
				Toast.makeText(this, "暂时还没有赞助商！", 3000).show();
			}
			break;
		case R.id.activity_detail_creater:
			Toast.makeText(this, "创建者", 3000).show();
			//进入创建者页面
			break;
		case R.id.activity_detail_attention:
			Toast.makeText(this, "关注成功", 3000).show();
			//得到创建者id，并将其关注加1，添加到当前用户关注列表
			break;
		default:
				break;
		}
	}

	private final void openPopupwin(Drawable image) {
		LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.view_picture, null, true);
		ImageView img = (ImageView)menuView.findViewById(R.id.pic);
		img.setImageDrawable(image);
		PopupWindow popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(findViewById(R.id.activity_detail), Gravity.CENTER
				| Gravity.CENTER, 0, 0);
//		popupWindow.showAsDropDown(menuView);
		popupWindow.update();
	}
	
}
