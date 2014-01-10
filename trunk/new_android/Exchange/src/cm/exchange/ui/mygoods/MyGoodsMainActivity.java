package cm.exchange.ui.mygoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import cm.exchange.R;
import cm.exchange.util.BaseTabActivity;
import cm.exchange.util.PrimaryActivity;

public class MyGoodsMainActivity extends BaseTabActivity {

	View view;
	View buttonView;
	int tabButtonImages[] = new int[] { R.drawable.mygoods_shopping,
			R.drawable.mygoods_attention, R.drawable.home };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygoods_maintab);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		view = findViewById(R.id.mygoods_maintab_title);
		left = (ImageButton) view.findViewById(R.id.main_title_left);
		right = (ImageButton) view.findViewById(R.id.main_title_right);
		titleText = (TextView) view.findViewById(R.id.main_title_text);
		buttonView = findViewById(R.id.mygoods_maintab_bottom);
	}

	@Override
	public void initTitle() {
		// TODO Auto-generated method stub
		left.setImageResource(R.drawable.main_title_back_icon);
		left.setOnClickListener(this);
		right.setImageResource(R.drawable.refresh_icon);
		right.setOnClickListener(this);
		titleText.setText(getResources().getString(R.string.mygoods_goods));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_title_left:
			finish();
			break;
		case R.id.main_title_right:
			((PrimaryActivity) getCurrentActivity()).onRightButtonClicked();
			break;
		}
	}

	


	@Override
	public void initTabButton() {
		// TODO Auto-generated method stub
		createBottom(buttonView,
				getResources().getStringArray(R.array.mygoods_button_text),
				tabButtonImages);
		final TabHost th = getTabHost();

		th.addTab(th.newTabSpec("SHOPPING").setIndicator("SHOPPING")
				.setContent(new Intent(this, MyGoodsShopping.class)));
		th.addTab(th.newTabSpec("MY_ATTENTION").setIndicator("MY_ATTENTION")
				.setContent(new Intent(this, MyGoodsAttention.class)));
		th.addTab(th.newTabSpec("MY_GOODS").setIndicator("MY_GOODS")
				.setContent(new Intent(this, MyGoodsActivity.class)));
		setDefaultTab("SHOPPING");

		RadioGroup group = (RadioGroup) findViewById(R.id.mygoods_maintab_radio);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.mygoods_radio_button0:
					th.setCurrentTabByTag("SHOPPING");
					break;
				case R.id.mygoods_radio_button1:
					th.setCurrentTabByTag("MY_ATTENTION");
					break;
				case R.id.mygoods_radio_button2:
					th.setCurrentTabByTag("MY_GOODS");
					break;
				}
			}
		});

	}

}
