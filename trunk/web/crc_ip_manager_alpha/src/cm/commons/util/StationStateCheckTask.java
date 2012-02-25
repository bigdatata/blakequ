package cm.commons.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StationStateCheckTask extends TimerTask{
	private static long SECOND_TO_MILLISECOND=1000;
	private static Log log = LogFactory.getLog(StationStateCheckTask.class);
	private  Map<String,Date> nameLastTime=new HashMap<String,Date>();
	private  Set<String> warnStation=new HashSet<String>();
	
	private static StationStateCheckTask task;
	
	public static StationStateCheckTask getStateCheckTask(int frequency){
		if(task==null){
			task=new StationStateCheckTask(frequency);
			task.startCheckTask();
		}
		return task;
	}
	
	private int frequency;
	/**
	 * second
	 */
	private int timeToWarn;
	
	private StationStateCheckTask(int frequency){
		this.frequency=frequency;
		this.timeToWarn=0;
	}
	
	/**
	 * 
	 * @param frequency second
	 * @param timeToWarn second
	 */
	private StationStateCheckTask(int frequency,int timeToWarn){
		this.frequency=frequency;
		this.timeToWarn=timeToWarn;
	}
	
	public void addOrRefreshTime(String stationName){
		nameLastTime.put(stationName, new Date());
	}
	/**
	 * start task
	 */
	private void startCheckTask(){
		
		Timer timer=new Timer();
		timer.schedule(this, timeToWarn, frequency*SECOND_TO_MILLISECOND);
	}
	public void setFrequency(int frequency){
		this.frequency=frequency;
	}
	
	public Set<String> getWarnStation() {
		return warnStation;
	}
	public void setTimeToWarn(int timeToWarn) {
		this.timeToWarn = timeToWarn;
	}
	@Override
	public void run() {	
		
		warnStation.clear();
		Date now =new Date();
		for(String name:nameLastTime.keySet()){
			Date receivedTime=nameLastTime.get(name);
			boolean needWarn=(now.getTime()-receivedTime.getTime())>timeToWarn*SECOND_TO_MILLISECOND;
			if(needWarn){
				warnStation.add(name);
				log.debug("warning station"+name);
			}
		}
	}

}
