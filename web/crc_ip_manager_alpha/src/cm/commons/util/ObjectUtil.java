package cm.commons.util;

import java.lang.reflect.Method;

public class ObjectUtil {

	public static Object getFieldValueByName(String fieldName, Object o) {  
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(o, new Object[] {});  
            return value;  
        } catch (Exception e) {  
            System.out.println("属性不存在");  
            return null;  
        }  
    } 
}
