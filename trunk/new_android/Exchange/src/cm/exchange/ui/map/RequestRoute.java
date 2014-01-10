package cm.exchange.ui.map;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.widget.Toast;

import cm.exchange.R;

import com.google.android.maps.GeoPoint;

public class RequestRoute {
	double origin_lat, origin_lng, destination_lat, destination_lng;
	Context context;
	public RequestRoute(Context context, double origin_lat, double origin_lng, double destination_lat, double destination_lng){
		this.context = context;
		this.origin_lat = origin_lat;
		this.destination_lat = destination_lat;
		this.origin_lng = origin_lng;
		this.destination_lng = destination_lng;
	}
	
	/**
	 * if can not get route, will return null
	 * @param drive_method (driving,walking)
	 * @param language (en, cn ...)
	 * @return
	 */
	public List<GeoPoint> getPointList(String drive_method, String language){
		String result = requestRouteFromGoogle(drive_method, language);
		if(result == null){
    		return null;
    	}
		return decodePoly(result);
	}
	
	/**
	 * 通过解析google map返回的xml，在map中画路线图 
	 * @param origin_lat
	 * @param origin_lng
	 * @param destination_lat
	 * @param destination_lng
	 * @param drive_method the method include driving, walking and bicycling. but bicycling only in American
	 * @param language you request language (en, cn. ..)
	 * @return
	 */
	private String requestRouteFromGoogle(String drive_method, String language){
		StringBuilder builder = new StringBuilder("http://maps.google.com/maps/api/directions/xml?");
		builder.append("origin="+origin_lat);
		builder.append(","+origin_lng);
		builder.append("&destination="+destination_lat);
		builder.append(","+destination_lng);
		builder.append("&sensor=true&mode="+drive_method);
		builder.append("&language="+language);
		String url = builder.toString();
//		System.out.println(url);
//		String url = "http://maps.google.com/maps/api/directions/xml?origin=23.055291,113.391802&destination=23.046604,113.397510&sensor=false&mode=walking&language=cn"; 
		HttpGet get = new HttpGet(url); 
		String strResult = null; 
		try { 
			HttpParams httpParameters = new BasicHttpParams(); 
			HttpConnectionParams.setConnectionTimeout(httpParameters, 3000); 
			HttpClient httpClient = new DefaultHttpClient(httpParameters); 
		
			HttpResponse httpResponse = null; 
			httpResponse = httpClient.execute(get); 
		
			if (httpResponse.getStatusLine().getStatusCode() == 200){ 
				strResult = EntityUtils.toString(httpResponse.getEntity()); 
			} 
		} catch (Exception e) { 
			return null; 
		} 
		
		if (-1 == strResult.indexOf("<status>OK</status>")){ 
			Toast.makeText(context, "获取导航路线失败!", Toast.LENGTH_SHORT).show(); 
			return null; 
		} 
		int pos = strResult.indexOf("<overview_polyline>"); 
		pos = strResult.indexOf("<points>", pos + 1); 
		int pos2 = strResult.indexOf("</points>", pos); 
		strResult = strResult.substring(pos + 8, pos2); 
		return strResult;
	} 
	
	
	
	/** 
	* 解析返回xml中overview_polyline的路线编码 
	* 
	* @param encoded 
	* @return 
	*/ 
	private List<GeoPoint> decodePoly(String encoded) {
		List<GeoPoint> poly = new ArrayList<GeoPoint>(); 
		int index = 0, len = encoded.length(); 
		int lat = 0, lng = 0; 
		while (index < len) { 
			 int b, shift = 0, result = 0; 
			 do { 
				b = encoded.charAt(index++) - 63; 
				result |= (b & 0x1f) << shift; 
				shift += 5; 
			 } while (b >= 0x20); 
			 
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1)); 
			lat += dlat; 
			shift = 0; 
			result = 0; 
			
			do { 
				b = encoded.charAt(index++) - 63; 
				result |= (b & 0x1f) << shift; 
				shift += 5; 
			} while (b >= 0x20); 
			
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1)); 
			lng += dlng; 
			GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),(int) (((double) lng / 1E5) * 1E6)); 
			poly.add(p); 
		} 
		return poly; 
	} 
}
