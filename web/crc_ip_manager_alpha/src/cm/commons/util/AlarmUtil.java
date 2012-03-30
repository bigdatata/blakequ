package cm.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cm.commons.controller.form.AlarmForm;
/**
 * 告警
 * @author Administrator
 *
 */
public class AlarmUtil {
	//定时任务
	private final static long SECOND_TO_MILLISECOND=1000;
	private static int frequency = 60;
	private static Timer timer;
	/**
	 * 设置清除告警频率
	 * @param time
	 */
	public static void setTime(int time){
		frequency = time;
	}
	public static void setFrequency(int fq){
		System.out.println("--------time:"+fq*SECOND_TO_MILLISECOND);
		frequency=fq;
		if(timer != null){
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("--------clear segment alarm--------time:"+frequency);
				//定时清除线段告警
				clearAlarmSegment();
			}
		}, 0, frequency*SECOND_TO_MILLISECOND);
	}
	static{
		setFrequency(frequency);
	}
	
	public static final String SEGMENTKEY = "@";
	public static final String ERRORKEY = "#";
	/**
	 * 用于全局存储所有告警信息
	 */
	private static Map<String, AlarmForm> alarms = new HashMap<String, AlarmForm>();
	
	public static void addToMap(String station, AlarmForm af){
		alarms.put(station, af);
	}
	
	public static boolean containsStation(String station){
		return alarms.containsKey(station);
	}
	
	public static AlarmForm getByKey(String station){
		return alarms.get(station);
	}
	
	/**
	 * 移除指定站点
	 * @param station
	 */
	public static void removeStation(String station){
		alarms.remove(station);
	}
	
	
	/**
	 * 获取所有线段告警
	 */
	public static List<AlarmForm> getAlarmSegment(){
		List<AlarmForm> list = new ArrayList<AlarmForm>();
		Iterator i = alarms.keySet().iterator();
		while(i.hasNext()){
			String key = (String) i.next();
			if(key.startsWith(SEGMENTKEY)){
				list.add(alarms.get(key));
			}
		}
		return list;
	}
	
	/**
	 * 清除所有未知线段告警
	 */
	private static void clearAlarmSegment(){
		List<AlarmForm> list = new ArrayList<AlarmForm>();
		Iterator i = alarms.keySet().iterator();
		while(i.hasNext()){
			String key = (String) i.next();
			if(key.startsWith(SEGMENTKEY)){
				i.remove();
			}
		}
	}
	
	/**
	 * 获取所有状态有up到down的告警线段
	 * @return
	 */
	@SuppressWarnings("unused")
	public static List<AlarmForm> getUpToDownSegment(){
		List<AlarmForm> list = new ArrayList<AlarmForm>();
		Iterator i = alarms.keySet().iterator();
		while(i.hasNext()){
			String key = (String) i.next();
			if(key.startsWith(ERRORKEY)){
				list.add(alarms.get(key));
			}
		}
		return list;
	}
	
	
	/**
	 * 获取所有站点告警
	 * @return
	 */
	public static List<AlarmForm> getAlarmStation(){
		List<AlarmForm> list = new ArrayList<AlarmForm>();
		Iterator i = alarms.keySet().iterator();
		while(i.hasNext()){
			String key = (String) i.next();
			if(!key.startsWith(SEGMENTKEY)){
				list.add(alarms.get(key));
			}
		}
		return list;
	}
	
	/**
	 * 获取所有告警信息
	 * @return
	 */
	public static List<AlarmForm> getAllAlarm(){
		List<AlarmForm> afList = new ArrayList<AlarmForm>();
		Iterator i =  alarms.keySet().iterator();
		while(i.hasNext()){
			String key = (String) i.next();
			afList.add(alarms.get(key));
		}
		return afList;
	}
}
