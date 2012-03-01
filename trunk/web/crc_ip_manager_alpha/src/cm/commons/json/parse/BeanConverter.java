package cm.commons.json.parse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.json.simple.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BeanConverter {
	/*************************将javaBean转换为Json对象*******************************/
	/**
	 * 将javaBean转换成Map
	 * @param javaBean javaBean
	 * @return Map对象
	 */
	public static Map<String, String> toMap(Object javaBean) {
		Map<String, String> result = new HashMap<String, String>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();
		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? "" : value.toString());
				}
			} catch (Exception e) {
			}
		}
		return result;
	}
	
	
	
	/**
	 * 将javaBean转换成JSONObject
	 * @param bean javaBean
	 * @return json对象
	 */
	public static JSONObject toJSON(Object bean) {
		return new JSONObject(toMap(bean));
	}
	
	/***********************将Json对象转换为javaBean对象*********************************/

	/**
	 * 将json对象转换成Map
	 * @param jsonObject json对象
	 * @return Map对象
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(JSONObject jsonObject) throws JSONException {
		Map<String, String> result = new HashMap<String, String>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 将map转换成Javabean
	 * @param javabean javaBean
	 * @param data map数据
	 */
	public static Object toJavaBean(Object javabean, Map<String, String> data) {
		Method[] methods = javabean.getClass().getDeclaredMethods();
		for (Method method : methods) {
			try {
				if (method.getName().startsWith("set")) {
					String field = method.getName();
					field = field.substring(field.indexOf("set") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					method.invoke(javabean, new Object[] { data.get(field) });
				}
			} catch (Exception e) {
			}
		}
		return javabean;
	}

	/**
	 * 将json string转换成javaBean***
	 * @param bean javaBean
	 * @param data Json对象字符串数据
	 * @return Object 返回JavaBean对象
	 * @throws ParseException json解析异常
	 * @throws JSONException 
	 */
	public static Object toJavaBean(Object javabean, String data)
			throws ParseException, JSONException {
		JSONObject jsonObject = new JSONObject(data);
		Map<String, String> datas = toMap(jsonObject);
		return toJavaBean(javabean, datas);
	}	

	
	/**
	 * 将json string转换为javaBean
	 * @param jsonString json数组
	 * @param clazz 实体类
	 * @param arrayName 数组名字，如果为空，默认jsonString就是一个数组
	 * @return
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static List arrayToJavaBean(String jsonString, Class s, String arrayName) throws JSONException, IllegalAccessException, InstantiationException{ 
		JSONArray ja = null;
		if(arrayName != null && !arrayName.equals("")){
			JSONObject jo = new JSONObject(jsonString);
			Object ay = jo.get(arrayName);
			ja = new JSONArray(ay.toString());
		}else{
			ja = new JSONArray(jsonString);
		}
		List list = new ArrayList();
		for(int i = 0; i < ja.length(); i++){ 
			JSONObject jsonObject = ja.getJSONObject(i); 
			Map<String, String> datas = toMap(jsonObject);
			list.add(toJavaBean(s.newInstance(), datas));
		} 
		return list;
	} 
}
