package cm.commons.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cm.commons.config.form.RouteParseForm;
import cm.commons.config.form.SegmentParseForm;
import cm.commons.config.form.StationParseForm;
import cm.commons.config.parse.ParseAllData;
import cm.commons.config.parse.RouteParse;
import cm.commons.config.parse.SegmentParse;
import cm.commons.config.parse.StationParse;
import cm.commons.json.constant.ComputerJson;
import cm.commons.json.constant.PortJson;
import cm.commons.json.parse.BeanConverter;

import junit.framework.TestCase;

public class FileUtilTest extends TestCase {

	public void testReadFileFromDirectory(){
		Map<String, String> map = FileUtil.readFileFromDirectory("c:/", new String[]{"1.txt","2.txt","3.txt"});
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			String content = map.get(key);
			System.out.println("key:"+key);
			System.out.println("content:"+content);
		}
		
	}
	
	public void testParser(){
		try {
			String str = FileUtil.readFromFile("c:/1.txt");
			String s = str.substring(1, str.length());
			System.out.println(s);
			Map<String, String> map = BeanConverter.toMap(new JSONObject(s));
			Iterator i = map.keySet().iterator();
			while(i.hasNext()){
				String key = (String) i.next();
				System.out.println("key:"+key+", value:"+map.get(key));
			}
			List<PortJson> portJsonList = BeanConverter.arrayToJavaBean(map.get("portDatas"), PortJson.class, null);
			for(PortJson pj:portJsonList){
				System.out.println(pj);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testParser2() throws JSONException, IllegalAccessException, InstantiationException{
		String str;
		try {
			str = FileUtil.readFromFile("c:/config/route.txt");
			System.out.println(str.charAt(0));
			String s = str.substring(1, str.length());
			System.out.println(s);
			RouteParse rp = new RouteParse();
			List<RouteParseForm> list = rp.parseJson(s);
			for(RouteParseForm r: list){
				System.out.println(r);
			}
			System.out.println("count:"+rp.getCount());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testParser3() throws JSONException, IllegalAccessException, InstantiationException{
		String str;
		try {
			str = FileUtil.readFromFile("c:/config/1/station.txt");
			String s = str.substring(1, str.length());
			System.out.println(s);
			StationParse rp = new StationParse();
			List<StationParseForm> list = rp.parseJson(s);
			for(StationParseForm r: list){
				System.out.println(r);
			}
			System.out.println("count:"+rp.getCount());
			System.out.println("route:"+rp.getRoute());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testParser4() throws JSONException, IllegalAccessException, InstantiationException{
		String str;
		try {
			str = FileUtil.readFromFile("c:/config/1/segment.txt");
			String s = str.substring(1, str.length());
			System.out.println(s);
			SegmentParse rp = new SegmentParse();
			List<SegmentParseForm> list = rp.parseJson(s);
			for(SegmentParseForm r: list){
				System.out.println(r);
			}
			System.out.println("count:"+rp.getCount());
			System.out.println("route:"+rp.getRoute());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test2(){
		ParseAllData pa = new ParseAllData("c:/config");
		boolean b = pa.checkConfigFile("c:/config");
		System.out.println(b);
	}
	
	public void test1() throws JSONException, IllegalAccessException, InstantiationException{
		ParseAllData pa = new ParseAllData("c:/config");
		RouteParse  routeParse = pa.parserRouteConfig();
		List<RouteParseForm> lr = routeParse.getRouteList();
		for(RouteParseForm rpf:lr){
			System.out.println(lr);
		}
		Map<String, StationParse>  map1 = pa.parserStationConfig();
		Iterator i = map1.keySet().iterator();
		while(i.hasNext()){
			StationParse sp = map1.get(i.next());
			System.out.println(sp.getCount()+", "+sp.getRoute()+", ");
			List<StationParseForm> list = sp.getStationList();
			for(StationParseForm sf:list){
				System.out.println(sf);;
			}
		}
		Map<String, SegmentParse>  map2 = pa.parserSegmentConfig();
		i = map2.keySet().iterator();
		while(i.hasNext()){
			SegmentParse sp = map2.get(i.next());
			System.out.println(sp.getCount()+", "+sp.getRoute());
			List<SegmentParseForm> list = sp.getSegmentList();
			for(SegmentParseForm sf:list){
				System.out.println(sf);
			}
			
		}
//		Map<String, String> map = pa.getFileFromConfig("c:/config");
//		Iterator i = map.keySet().iterator();
//		while(i.hasNext()){
//			String key = (String) i.next();
//			System.out.println("key:"+key);
//			System.out.println("value:"+map.get(key));
//		}
//		System.out.println("************************");
	}
}
