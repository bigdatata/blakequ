package cm.commons.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;



public class DateAndTimestampUtil {
	
	public static String defaultDateFormatStr="yyyy-MM-dd HH:mm:ss";
	
	public static String defaultBeginDate="2011-09-01 00:00:00";
	
	public static long secondToMillisecond=1000;
	
	public static long minuteToMillisecond=60000; //60*secondToMillisecond
	
	public static long hourToMillisecond=3600000; //60*minuteToMillisecond
	
	public static long dayToMillisecond=86400000; //24*hourToMillisecond
	
	
	

	public static Timestamp getNowTimestamp(){
		return new Timestamp((new Date()).getTime());
	}
	
	public static Timestamp getTimestampBeginDatePulsParams(Date beginDate,int days,int hours,int minutes,int seconds,int milliseconds){
		long times=beginDate.getTime();
		times+=milliseconds+days*dayToMillisecond+
		hours*hourToMillisecond+minutes*minuteToMillisecond+seconds*secondToMillisecond;
		return new Timestamp(times);
	}
	
	public static Timestamp getTimestampNowPulsDays(int days){
		return getTimestampBeginDatePulsParams(new Date(),days,0,0,0,0);
	}
	
	public static String getNowStr(String formatStr){
		SimpleDateFormat dateFormat=new SimpleDateFormat(formatStr);
		return dateFormat.format(new Date());
	}
	
	/**
	 * @return 如 ：1990-12-12 12:12:12
	 */
	public static String getNowStr(){
	SimpleDateFormat dateFormat=new SimpleDateFormat(defaultDateFormatStr);
	return dateFormat.format(new Date());
	}
	/**
	 * 从请求中获取日期包括小时,如果为null，则如果有Start返回默认开始，其他的放回当前日期
	 * @param dateName 形如:1900-11-11
	 * @param timeName 形如:08:00
	 * @param request
	 * @return 1900-11-11 08:00:00
	 */
	
	public static String getDateAndTime(String dateName,String timeName,HttpServletRequest request){
		String date=request.getParameter(dateName);
		String time=request.getParameter(timeName);
		System.out.println(date);
		System.out.println(time);
		if(NullUtil.notNull(date)&&NullUtil.notNull(time)){
			return date+" "+time+":00";
		}
		if(dateName.indexOf("Start")!=-1){
			return defaultBeginDate;
		}
			
		return DateAndTimestampUtil.getNowStr();
	}
	/**
	 * 如columnName为:delete,则request中必须要有deleteDate(Y-m-d),deleteTime(H:i)
	 *
	 * @param columnName
	 * @param request
	 * @return  1900-11-11 08:00:00
	 */
	public static String getDateAndTime(String columnName,HttpServletRequest request){
		
		return getDateAndTime(columnName+"Date",columnName+"Time",request);
	}
	public static Timestamp DateToTimeStamp(Date date){
		return new Timestamp(date.getTime());
	}
}
