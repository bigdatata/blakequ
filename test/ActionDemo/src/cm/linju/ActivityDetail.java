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
 *  ���ϸҳ��
 * @author Administrator
 *
 */
public class ActivityDetail extends Activity implements OnClickListener{
	private View title;//����
	private Button back, home;//�����ϵ�������ť
	private TextView titletext;//�����ı�
	private TextView tab_discuss, tab_attention, tab_join, tab_score, tab_change; //���tab
	private View activity_profile,content_layout,button_three, activity_bottom_bar;
	private ImageView activity_detail_icon;//�����ͼƬ
	private TextView activity_title, activity_type, activity_time, activity_location;//����������
	private Button comment_num, look_num, detail_sponser;//�������������
	private Button creater, attention_creater;
	private Gallery gallery;
	
	private ActivityInfo ai;
	private Spannable sp;
	private UserInfo user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//��ȡ������Ϣ
		ai = (ActivityInfo) this.getIntent().getSerializableExtra("ai");
		setContentView(R.layout.activity_detail);
		this.getAllView();
		
		
	}
	
	private void getAllView()
	{
		title = findViewById(R.id.detailActivity_title);
		//���ñ��������
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
//		activity_detail_icon.setImageDrawable(drawable);//����ͼƬ����������������
		activity_detail_icon.setImageResource(R.drawable.linju);//��ʱ����
		creater = (Button)activity_profile.findViewById(R.id.activity_detail_creater);
		creater.setOnClickListener(this);
		creater.setText(ai.getCreateUser());//���ô�����
		attention_creater = (Button)activity_profile.findViewById(R.id.activity_detail_attention);
		attention_creater.setOnClickListener(this);
		
		//����Ҫ���û��������
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
		
		//��������ͳ��
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
		
		//����Ҫ����������������Ҫ�ж��Ƿ���Ҫ��ʾ��������������������������
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
		//����Ҫ����������
		
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
			Toast.makeText(this, "����", 3000).show();
			//����û���������ۻ����������Ҫ��¼���������������������÷���setResult()
			finish();
			break;
		case R.id.title_bt_right:
			Toast.makeText(this, "��ҳ", 3000).show();
			//�ص���ҳ
			break;
		case R.id.tab_discuss:
			Toast.makeText(this, "����", 3000).show();
			//��������
			break;
		case R.id.tab_attention:
			Toast.makeText(this, "��ע", 3000).show();
			//��ע�����ԣ������id����user�Ĺ�ע�����
			break;
		case R.id.tab_join:
			Toast.makeText(this, "���Ѿ��ɹ�����", 3000).show();
			//����������dialog�����Ƿ��������ۣ����߲鿴����������б��
			break;
		case R.id.tab_score:
			Toast.makeText(this, "����", 3000).show();
			//��������Dialog
			break;
		case R.id.tab_change:
			Toast.makeText(this, "�޸�", 3000).show();
			//Ҫ����Ȩ�ޣ�ֻ�д����߲����޸�(Tab��̬��ʾ������gone)
			break;
		case R.id.activity_detail_comment_num:
			Toast.makeText(this, "������", 3000).show();
			//�����������ҳ��
			break;
		case R.id.activity_detail_look_num:
			Toast.makeText(this, "�鿴��", 3000).show();
			int viewNum = Integer.parseInt(ai.getViewNum());
			viewNum ++;
			ai.setViewNum(viewNum + "");
			//�������鿴ҳ��
			break;
		case R.id.activity_detail_sponser:
			if(ai.isSponsor())
			{
				Toast.makeText(this, "�����̣�", 3000).show();
				//����������ҳ��
			}
			else
			{
				Toast.makeText(this, "��ʱ��û�������̣�", 3000).show();
			}
			break;
		case R.id.activity_detail_creater:
			Toast.makeText(this, "������", 3000).show();
			//���봴����ҳ��
			break;
		case R.id.activity_detail_attention:
			Toast.makeText(this, "��ע�ɹ�", 3000).show();
			//�õ�������id���������ע��1����ӵ���ǰ�û���ע�б�
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
