package cm.commons.config.parse;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import cm.commons.util.FileUtil;

public class ParseAllData {

	private static final String ROUTE = "route.txt";
	private static final String SEGMENT = "segment.txt";
	private static final String STATION = "station.txt";
	private Map<String, String> configMap = null;
	public ParseAllData(String path){
		if(this.checkConfigFile(path)){
			this.configMap = this.getFileFromConfig(path);
		}
	}
	
	
	/**
	 * 解析线路配置文件
	 * @param configMap
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws JSONException 
	 */
	public RouteParse parserRouteConfig() throws JSONException, IllegalAccessException, InstantiationException{
		RouteParse rp = new RouteParse();
		if(configMap != null){
			if(configMap.containsKey(ROUTE)){
				String value = configMap.get(ROUTE);
				rp.parseJson(value);
			}
		}
		return rp;
	}
	
	/**
	 * 解析每条线路的车站配置
	 * @return map<key, value> key:线路，value:该线路的所有车站
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws JSONException 
	 */
	public Map<String, StationParse> parserStationConfig() throws JSONException, IllegalAccessException, InstantiationException{
		Map<String, StationParse> map = new HashMap<String, StationParse>();
		if(configMap != null){
			Iterator i = configMap.keySet().iterator();
			while(i.hasNext()){
				String key = (String) i.next();
				if(!key.equals(ROUTE)){
					String sub = key.substring(0, key.length()-11);
					String b = key.substring(key.length()-11, key.length());
					if(b.equals(STATION)){
						String value = configMap.get(key);
						StationParse sp = new StationParse();
						sp.parseJson(value);
						map.put(sub, sp);
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 解析每条线路的线段配置
	 * @return map<key, value> key:线路，value:该线路的线段配置
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws JSONException 
	 */
	public Map<String, SegmentParse> parserSegmentConfig() throws JSONException, IllegalAccessException, InstantiationException{
		Map<String, SegmentParse> map = new HashMap<String, SegmentParse>();
		if(configMap != null){
			Iterator i = configMap.keySet().iterator();
			while(i.hasNext()){
				String key = (String) i.next();
				if(!key.equals(ROUTE)){
					String sub = key.substring(0, key.length()-11);
					String b = key.substring(key.length()-11, key.length());
					if(b.equals(SEGMENT)){
						String value = configMap.get(key);
						SegmentParse sp = new SegmentParse();
						sp.parseJson(value);
						map.put(sub, sp);
					}
				}
			}
		}
		return map;
	}
	

	/**
	 * 读取所有的配置文件
	 * @param path
	 * @return
	 */
	private Map<String, String> getFileFromConfig(String path){
		if(path == null || path.equals("")){
			return null;
		}
		Map<String, String> allFile = new HashMap<String, String>();
		//route
		Map<String, String> routeMap = FileUtil.readFileFromDirectory(path, ROUTE);
		String route = routeMap.get(ROUTE);
		
		allFile.put(ROUTE, route);
		//all segment and station
		List<File> files = FileUtil.readDirectoryFromPath(path);
		for(File file: files){
			String s = file.getPath();
			String lastName = s.substring(s.lastIndexOf(File.separatorChar)+1, s.length());
			Map<String, String> ssMap = FileUtil.readFileFromDirectory(file.getPath(), new String[]{SEGMENT, STATION});
			Iterator i = ssMap.keySet().iterator();
			while(i.hasNext()){
				String key = (String) i.next();
				if(key.equals(SEGMENT)){
					String segment = ssMap.get(key);
					allFile.put(lastName+SEGMENT, segment);
				}else if(key.equals(STATION)){
					String station = ssMap.get(key);
					allFile.put(lastName+STATION, station);
				}
			}
		}
		return allFile;
	}
	
	/**
	 * 检查配置文件是否正确
	 * @param path
	 * @return
	 */
	public boolean checkConfigFile(String path){
		boolean flag = true;
		if(path == null || path.equals("")){
			return false;
		}
		try {
			Map<String, String> routeMap = FileUtil.readFileFromDirectory(path, ROUTE);
			if(routeMap == null){
				return false;
			}else if(routeMap.size() == 0){
				return false;
			}
			String route = routeMap.get(ROUTE);
			RouteParse rp = new RouteParse();
			rp.parseJson(route);
			int count = rp.getCount();
			List<File> files = FileUtil.readDirectoryFromPath(path);
			if(files == null) return false;
			if(files.size() != count){
				flag = false;
			}else{
				for(File file: files){
					String[] f = file.list();
					int size = f.length;
					if(size != 2){
						flag = false;
						break;
					}
					for(String s:f){
						if(!s.equals(SEGMENT) && !s.equals(STATION)){
							flag = false;
							break;
						}
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
