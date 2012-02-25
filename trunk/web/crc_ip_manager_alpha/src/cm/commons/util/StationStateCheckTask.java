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

/**
 * 
 * 每隔frequency秒执行一次获取报警的站点的名称
 * 判断一个站是否报警的原则是检查的当前时间与获得请求的时间差大于timeToWarn就要报警
 * 每次接收到新的请求时需要调用addOrRefreshTime()来更新或者添加上次接受到请求的时间
 *
 */
public class StationStateCheckTask extends TimerTask{
	private final static long SECOND_TO_MILLISECOND=1000;
	private static Log log = LogFactory.getLog(StationStateCheckTask.class);
	private  Map<String,Date> nameLastTime=new HashMap<String,Date>();
	private  Set<String> warnStation=new HashSet<String>();
	/**
	 * second 执行任务的周期，间隔frequency秒执行一次
	 */
	private int frequency;
	/**
	 * second  多久没收到请求才报警，检查的当前时间与获得请求的时间差大于timeToWarn就要报警
	 */
	private int timeToWarn;
	
	private final static int DEFAULT_SECOND_TO_WARN=300;
	
	private static StationStateCheckTask task;
	
	public static StationStateCheckTask getStateCheckTask(int frequency){
		if(task==null){
			task=new StationStateCheckTask(frequency);
			task.startCheckTask();
		}
		task.setFrequency(frequency);
		return task;
	}
	public static StationStateCheckTask getStateCheckTask(int frequency,int timeToWarn){
		if(task==null){
			task=new StationStateCheckTask(frequency,timeToWarn);
			task.startCheckTask();
		}
		return task;
	}
	
	
	public int getFrequency() {
		return frequency;
	}

	private StationStateCheckTask(int frequency){
		this.frequency=frequency;
		this.timeToWarn=DEFAULT_SECOND_TO_WARN;
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
