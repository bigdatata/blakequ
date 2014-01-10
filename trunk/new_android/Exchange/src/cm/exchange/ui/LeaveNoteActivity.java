package cm.exchange.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cm.exchange.R;
import cm.exchange.adapter.LeaveNoteAdapter;
import cm.exchange.db.CommentService;
import cm.exchange.entity.Comment;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.CommentListParser;
import cm.exchange.parser.ParserListener;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.UserTask;

public class LeaveNoteActivity extends BaseActivity {
	View title;
	ImageButton left, right;
	TextView titleText;
	ListView listView;
	PopupWindow popupWindow;
	CommentListParser parser = null;
	LeaveNoteAdapter adapter = null;
	List<Comment> commentList = null, oldList = null;
	CommentService commentDB = null;
	private final static int DIALOG_SHOW = 1;
	private final static String REQUEST_NOTE = "1";
	private final static String REQUEST_REPLY = "2";
	private boolean check = true;
	private int totalNum = 0, goodsID;
	String reply;
	@Override
	public void initTitle() {
		// TODO Auto-generated method stub
		super.initTitle();
		left.setImageResource(R.drawable.main_title_back_icon);
		right.setImageResource(R.drawable.main_title_comment_icon);
		titleText.setText(getResources().getString(R.string.messagegoods_leave_note));
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
			case R.id.main_title_left:
				finish();
				break;
			case R.id.main_title_right:
				openPopupwin("");
				break;
		
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagegoods);
		initView();
		goodsID = getIntent().getIntExtra("goodsID", -1);
		parser = new CommentListParser();
		commentList = new ArrayList<Comment>();
		commentDB = new CommentService(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Comment c = (Comment) parent.getItemAtPosition(position);
				reply = (String) c.getUsername();
				showDialog(DIALOG_SHOW);
			}
		});
	}

	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		commentDB.open();
		if(adapter == null){
			oldList = commentDB.getAllData();
			new NoteTask().execute(REQUEST_NOTE);
		}
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(commentDB.getDatabase().isOpen()){
			commentDB.close();
		}
	}


	private void initView() {
		// TODO Auto-generated method stub
		title = findViewById(R.id.messagegoods_title);
		left = (ImageButton)title.findViewById(R.id.main_title_left);
		left.setOnClickListener(this);
		right = (ImageButton)title.findViewById(R.id.main_title_right);
		right.setOnClickListener(this);
		titleText = (TextView) title.findViewById(R.id.main_title_text);
		listView = (ListView)findViewById(R.id.messagegoods_listview);
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_SHOW:
				buildDialog(this);
				break;
		}
		return super.onCreateDialog(id);
	}


	private Dialog buildDialog(LeaveNoteActivity leaveNoteActivity) {
		// TODO Auto-generated method stub
		final CharSequence[] items = new CharSequence[]{this.getResources().getString(R.string.messagegoods_reply_leave_note)};
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle(getResources().getString(R.string.messagegoods_leave_note));
		ab.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				openPopupwin("@"+reply+":");
			}
		});
		ab.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		ab.show();
		return ab.create();
		
	}
	
	
	private void openPopupwin(String str) {
		LayoutInflater mLayoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.messagegoods_popup_layout, null, true);

		final EditText text = (EditText) menuView.findViewById(R.id.messagegoods_input_edit);
		if(!"".equals(str)){
			text.setText(str);
		}
		Button confirm = (Button) menuView.findViewById(R.id.messagegoods_input_confirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = text.getText().toString();
				//upload to service
				new NoteTask().execute(REQUEST_REPLY, str);
			}
		});
		Button cancel = (Button) menuView.findViewById(R.id.messagegoods_input_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(findViewById(R.id.messagegoods_layout),
				Gravity.CENTER | Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
	
	
	private class NoteTask extends UserTask<String, Object, Boolean> implements ParserListener<Comment>{
		int type = 0;
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(params[0].equals(REQUEST_NOTE)){
				getData(goodsID, this);
			}else if(params[0].equals(REQUEST_REPLY)){
				type = 1;
				publishProgress(uploadNote(goodsID, params[1]));
			}
			return true;
		}
		

		@Override
		protected void onProgressUpdate(Object... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if(values[0] instanceof Comment){
				if(values[0]!=null){
					commentList.add((Comment) values[0]);
					freshListView();
				}else{
					Toast.makeText(LeaveNoteActivity.this, getResources().getString(R.string.main_no_new_data), 3000).show();
				}
			}else{
				int result = (Integer) values[0];
				if(result == -1){
					Toast.makeText(LeaveNoteActivity.this, getResources().getString(R.string.detailgoods_upload_note_error), 3000).show();
				}else{
					Toast.makeText(LeaveNoteActivity.this, getResources().getString(R.string.detailgoods_upload_note_success), 3000).show();
					popupWindow.dismiss();
				}
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(type == 0 && oldList!= null){
				commentList.addAll(oldList);
				freshListView();
			}
		}

		@Override
		public void onParserOverListener(Comment t) {
			// TODO Auto-generated method stub
			if (check) {
				totalNum = Integer.valueOf(parser.getTotalNum());
				if (totalNum == 0) {
					cancel(true);
				}
				check = false;
			}
			if(totalNum != 0){
				publishProgress(t);
				commentDB.update(t);
			}
		}
		
	}
	
	
	private void getData(int goodsID, ParserListener<Comment> listener){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		try {
			is = httpClient.doHttpPost2(URLConstant.COMMENTINFO, new BasicNameValuePair("goodsID", String.valueOf(goodsID)));
//			is = getAssets().open("comment.xml");
			parser.parse(is, listener);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("comment parser", "leaveNoteActivity  parser error");
		}finally
		{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private int uploadNote(int goodsID, String note){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		int result = 0;
		try {
			note = URLEncoder.encode(note, "UTF-8");
			is = httpClient.doHttpPost2(URLConstant.GOODS_NOTE_UPLOAD, new BasicNameValuePair("goodsID",  String.valueOf(goodsID)),
								new BasicNameValuePair("content", note));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		}finally
		{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	private void freshListView() {
		if (adapter == null) {
			adapter = new LeaveNoteAdapter(this, commentList);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}
}
