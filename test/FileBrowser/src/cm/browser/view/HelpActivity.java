package cm.browser.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cm.browser.R;
import cm.util.BaseActivity;

public class HelpActivity extends BaseActivity {

	TextView text, user_link, email_link, user_email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		text = (TextView) findViewById(R.id.user_help);
		text.setText(getResources().getString(R.string.my_help));
		user_link = (TextView) findViewById(R.id.user_link);
		user_email = (TextView) findViewById(R.id.user_email);
		email_link = (TextView) findViewById(R.id.user_email_link);
		email_link.setText(getResources().getString(R.string.my_email));
		
		SpannableString sp = new SpannableString(getResources().getString(R.string.my_blog));   
		sp.setSpan(new URLSpan("http://hao3100590.iteye.com/"), 3, 7,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue_light)), 3, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		user_link.setText(sp);
		user_link.setMovementMethod(LinkMovementMethod.getInstance());
		
		user_email.setBackgroundResource(R.drawable.text_icon);
		user_email.setMovementMethod(LinkMovementMethod.getInstance());
		user_email.setText(getResources().getString(R.string.email));
		user_email.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		user_email.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text"); //type
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Blakequ@gmail.com"});//email to
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_title));//title
				emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.email_content));//content
//				startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.email_choose)));
				try{
					startActivity(emailIntent);
				}catch(ActivityNotFoundException e){
					e.printStackTrace();
					Toast.makeText(HelpActivity.this, getResources().getString(R.string.not_email), 5000).show();
				}
			}
		});
		
		/*
		text.setText(Html.fromHtml("���ͣ� <a href='http://hao3100590.iteye.com'> ���˲���</a><h1><i><font color='#000FFF'>h1 ���� б�� ��ɫ</font></i>"));
		����һ�� SpannableString����  
        SpannableString sp = new SpannableString("��仰���аٶȳ�����,�и�����ʾ����������������������б��.");   
        //���ó�����  
        sp.setSpan(new URLSpan("http://www.baidu.com"), 5, 7,   
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   
        //���ø�����ʽһ  
        sp.setSpan(new BackgroundColorSpan(Color.RED), 17 ,19,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
        //���ø�����ʽ��  
        sp.setSpan(new ForegroundColorSpan(Color.YELLOW),20,24,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     
        //����б��  
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 27, 29, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);   
        //SpannableString�������ø�TextView  
        text.setText(sp);   
        	����TextView�ɵ��  
        text.setMovementMethod(LinkMovementMethod.getInstance());   
        */
	}

}
