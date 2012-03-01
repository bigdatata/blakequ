package cm.commons.config.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cm.commons.config.form.StationParseForm;
import cm.commons.json.parse.BeanConverter;

public class StationParse implements BaseParse<StationParseForm>{

	private static final String COUNT = "count";
	private static final String ROUTE = "route";
	private static final String STATIONS = "stations";
	private int count;
	private String route;
	private List<StationParseForm> stationList;
	
	public int getCount() {
		return count;
	}

	public String getRoute() {
		return route;
	}

	public List<StationParseForm> getStationList() {
		return stationList;
	}

	public List<StationParseForm> parseJson(String jsonStr) throws JSONException, IllegalAccessException, InstantiationException {
		// TODO Auto-generated method stub
		List<StationParseForm> list = new ArrayList<StationParseForm>();
		Map<String, String> map = BeanConverter.toMap(new JSONObject(jsonStr));
		this.count = Integer.parseInt(map.get(COUNT));
		this.route = map.get(ROUTE);
		list = BeanConverter.arrayToJavaBean(map.get(STATIONS), StationParseForm.class, null);
		this.stationList = list;
		return list;
	}

	

}
