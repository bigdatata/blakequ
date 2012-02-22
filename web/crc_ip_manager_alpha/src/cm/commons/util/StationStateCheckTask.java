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
	private Map<String,Date> nameLastTime=new HashMap<String,Date>();
	private Set<String> warnStation=new HashSet<String>();
	
	private int frequency;
	/**
	 * second
	 */
	private int timeToWarn;
	/**
	 * 
	 * @param frequency second
	 * @param timeToWarn second
	 */
	public StationStateCheckTask(int frequency,int timeToWarn){
		this.frequency=frequency;
	}
	public void addOrRefreshTime(String stationName){
		nameLastTime.put(stationName, new Date());
	}
	public void startCheckTask(){
		
		Timer timer=new Timer();
		timer.schedule(this, 0, frequency*SECOND_TO_MILLISECOND);
		
		
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
