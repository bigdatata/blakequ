package cm.exchange.util;

import android.os.AsyncTask;
import android.util.Log;

/**
 * This class is used to handle the concurrent thread, if you want do something time-consuming,
 * please extends this class, it can help you manager the threads. 
 * @author qh
 *
 * @param <Params> 泛型参数
 * @param <Progress> 进度值（执行过程中返回的值）
 * @param <Result> 最终值（执行完返回的值）
 */
public abstract class UserTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	
	public final static String TAG = "usertask";

	@Override
	protected void onPostExecute(Result result) {
		// TODO Auto-generated method stub
		BaseService.taskList.remove(this);
		Log.i(TAG, "remove task");
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		BaseService.taskList.add(this);
		Log.i(TAG, "add task");
	}

	
	

}
