package cm.commons.config.parse;

import java.util.List;

import org.json.JSONException;

public interface BaseParse<E> {

	/**
	 * 解析json字符
	 * @param jsonStr
	 * @return
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws JSONException 
	 */
	List<E> parseJson(String jsonStr) throws JSONException, IllegalAccessException, InstantiationException;
}
