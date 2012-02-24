package cm.commons.json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import cm.commons.json.constant.ComputerJson;
import cm.commons.json.constant.StationJson;
import cm.commons.json.parse.BeanConverter;
import cm.commons.pojos.Station;

import junit.framework.TestCase;

public class BeanConverterTest extends TestCase {

	public void testToJavaBean(){
		Map<String, String> j = new HashMap<String, String>();
		j.put("station_name", "成都站");
		j.put("flag_value", "1");
//		"pc_ip":"192.168.1.29",
//		"pc_state":1,
//		"pc_info":"windows33,win-7,pentium--4400",
//		"pc_cpu_usage":12,
//		"pc_mem_usage":45.4877,
		j.put("pc_ip", "192.168.1.29");
		j.put("pc_state", "1");
		j.put("pc_info", "windows33,win-7,pentium--4400");
		j.put("pc_cpu_usage", "12");
		j.put("pc_mem_usage", "45.4877");
		try {
			StationJson station = new StationJson();
			ComputerJson c = new ComputerJson();
			station = (StationJson) BeanConverter.toJavaBean(station, j);
			c= (ComputerJson) BeanConverter.toJavaBean(c, j);
			System.out.println(station);
			System.out.println(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
