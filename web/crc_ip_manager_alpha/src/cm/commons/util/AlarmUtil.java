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
	private static int frequency = 1;
	public static void setFrequency(int fq){
		frequency=fq;
	}

	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//定时清除告警
			alarms.clear();
		}
	};
	static{
		Timer timer=new Timer();
		timer.schedule(task, 0, frequency*SECOND_TO_MILLISECOND);
	}
	
	public static final String SEGMENTKEY = "@";
	/**
	 * 用于全局存储所有告警信息
	 */
	private static Map<String, AlarmForm> alarms = new HashMap<String, AlarmForm>();
	
	public static void addToMap(String station, AlarmForm af){
		alarms.put(station, af);
	}
	
	/**
	 * 清除所有数据
	 */
	public static void Clear(){
		alarms.clear();
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
		Set<String> stations = alarms.keySet();
		for(String s:stations){
			AlarmForm af = alarms.get(s);
			if(af.getState()!=0){
				afList.add(af);
			}else{
				alarms.remove(s);
			}
		}
		return afList;
	}
}
