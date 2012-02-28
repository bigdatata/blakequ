package cm.commons.util;

public class NullUtil {

	/**
	 * 
	 * @param object 判断object是不是空
	 * @return
	 */
	public static boolean isNull(Object object){
		boolean nullObject=false;
		nullObject=(object==null||object.equals(""));
		return nullObject;
	}
	public static boolean notNull(Object object){
		return !isNull(object);
	}
}
