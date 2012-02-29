package cm.commons.util;

import java.util.List;

import cm.commons.controller.form.AlarmForm;
import fb.info.fbSegment;
import fb.info.fbStation;

public class AlarmStyleUtil {
	
	//stationColors={"station_normal","station_abnormal","station_unknow"};
	private static String[] stationColors={"#76EE00","EE0000","#000000"};
	//segmentColors={"segment_normal","segment_abnormal","segment_unknow"};
	private static String[] segmentColors={"#7A67EE","#FF7F00","#636363"};
	
	private static List<AlarmForm> list;
	
	/**
	 * 
	 * @param object  call this function you should first call setList() or you will get normalColor;
	 * @return
	 */
	public static String getColor(Object object){
		return getColor(object,list);
	}
	
	public static void setList(List<AlarmForm> list) {
		AlarmStyleUtil.list = list;
	}

	public static String getColor(Object object,List<AlarmForm> list){
		if(object instanceof fbStation){
			return getStationColor((fbStation)object,list);
		}
		if(object instanceof fbSegment){
			return getSegmentColor((fbSegment)object,list);
		}
		return  "";
	}
	public static String getStationColor(fbStation station,List<AlarmForm> list){
		if(NullUtil.isNull(list)||list.size()==0){
			return stationColors[0];
		}
		int stationId=station.getId();	
		for(AlarmForm alarm:list){
			if(alarm.getStation_id()==stationId){
				int state=alarm.getState();
				return stationColors[state];
			}
		}
		return stationColors[0];
	}
	/**
	 * 
	 * @param stationColors  stationColors={"station_normal","station_abnormal","station_unknow"};
	 */
	public static void setStationColors(String[] stationColors) {
		AlarmStyleUtil.stationColors = stationColors;
	}
	/**
	 * 
	 * @param segmentColors  segmentColors={"segment_normal","segment_abnormal","segment_unknow"};
	 */
	public static void setSegmentColors(String[] segmentColors) {
		AlarmStyleUtil.segmentColors = segmentColors;
	}

	public static String getSegmentColor(fbSegment segment,List<AlarmForm> list){
		if(NullUtil.isNull(list)||list.size()==0){
			return segmentColors[0];
		}
		int segmentId=segment.getId();	
		for(AlarmForm alarm:list){
			if(alarm.getStation_id()==segmentId){
				int state=alarm.getState();
				return segmentColors[state];
			}
		}
		return segmentColors[0];
	}
}
