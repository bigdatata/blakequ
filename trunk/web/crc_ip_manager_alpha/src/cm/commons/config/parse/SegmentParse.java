package cm.commons.config.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cm.commons.config.form.SegmentParseForm;
import cm.commons.json.parse.BeanConverter;

public class SegmentParse implements BaseParse<SegmentParseForm> {

	private static final String COUNT = "count";
	private static final String ROUTE = "route";
	private static final String SEGMENTS = "segments";
	private int count;
	private String route;
	private List<SegmentParseForm> segmentList;
	public int getCount() {
		return count;
	}
	

	public List<SegmentParseForm> getSegmentList() {
		return segmentList;
	}


	public String getRoute() {
		return route;
	}

	public List<SegmentParseForm> parseJson(String jsonStr) throws JSONException, IllegalAccessException, InstantiationException {
		// TODO Auto-generated method stub
		List<SegmentParseForm> list = new ArrayList<SegmentParseForm>();
		Map<String, String> map;
		map = BeanConverter.toMap(new JSONObject(jsonStr));
		this.count = Integer.parseInt(map.get(COUNT));
		this.route = map.get(ROUTE);
		list = BeanConverter.arrayToJavaBean(map.get(SEGMENTS), SegmentParseForm.class, null);
		this.segmentList = list;
		return list;
	}

}
