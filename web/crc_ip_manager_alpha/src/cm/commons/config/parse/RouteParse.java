package cm.commons.config.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cm.commons.config.form.RouteParseForm;
import cm.commons.json.parse.BeanConverter;

public class RouteParse implements BaseParse<RouteParseForm>{

	private static final String COUNT = "count";
	private static final String ROUTES = "routes";
	private int count = 0;
	private List<RouteParseForm> routeList;
	public int getCount() {
		return count;
	}
	public List<RouteParseForm> getRouteList() {
		return routeList;
	}

	public List<RouteParseForm> parseJson(String jsonStr) throws JSONException, IllegalAccessException, InstantiationException {
		// TODO Auto-generated method stub
		List<RouteParseForm> routeList = new ArrayList<RouteParseForm>();
		Map<String, String> map = BeanConverter.toMap(new JSONObject(jsonStr));
		this.count = Integer.parseInt(map.get(COUNT));
		routeList = BeanConverter.arrayToJavaBean(map.get(ROUTES), RouteParseForm.class, null);
		this.routeList = routeList;
		return routeList;
	}

	
}
