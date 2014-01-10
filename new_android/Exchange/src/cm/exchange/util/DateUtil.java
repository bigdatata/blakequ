package cm.exchange.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.Spannable;
import android.text.SpannableString;

/**
 * 
 * @author qh
 *
 */
public class DateUtil {
	/**
	 * format date
	 * @param date
	 * @return
	 */
	public static String getCreateAt(Date date){
		Calendar c = Calendar.getInstance();
		if(c.get(Calendar.YEAR)-(date.getYear()+1900)>0){
			int i = c.get(Calendar.YEAR)-date.getYear();
			return i+"年前";
		}else if(c.get(Calendar.MONTH)-date.getMonth()>0){
			int i = c.get(Calendar.MONTH)-date.getMonth();
			return i+"月前";
		}else if(c.get(Calendar.DAY_OF_MONTH)-date.getDate()>0){
			int i = c.get(Calendar.DAY_OF_MONTH)-date.getDate();
			return i+"天前"; 
		}else if(c.get(Calendar.HOUR_OF_DAY)-date.getHours()>0){
			int i = c.get(Calendar.HOUR_OF_DAY)-date.getHours();
			return i+"小时前"; 
		}else if(c.get(Calendar.MINUTE)-date.getMinutes()>0){
			int i = c.get(Calendar.MINUTE)-date.getMinutes();
			return i+"分钟前";
		}else {
			return "刚刚";
		}
	}
	
	/**
	 * format the date to mm-dd
	 * @param date
	 * @return
	 */
	public static String formatData(String date){
		Spannable sp = new SpannableString(date);
		return sp.subSequence(5, 10).toString();
	}
	
	/**
	 * format the date to yyyy-mm-dd
	 * @param date
	 * @return
	 */
	public static String formatData(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String s = format.format(date);
		return s;
	}
	
	public static String converTime(long timestamp){
		long currentSeconds = System.currentTimeMillis()/1000;
		long timeGap = currentSeconds-timestamp;//与现在时间相差秒数
		String timeStr = null;
		if(timeGap>24*60*60){//1天以上
			timeStr = timeGap/(24*60*60)+"天前";
		}else if(timeGap>60*60){//1小时-24小时
			timeStr = timeGap/(60*60)+"小时前";
		}else if(timeGap>60){//1分钟-59分钟
			timeStr = timeGap/60+"分钟前";
		}else{//1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}
	
	public static String getStandardTime(long timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		Date date = new Date(timestamp*1000);
		sdf.format(date);
		return sdf.format(date);
	}
}
