package cm.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONException;

import cm.commons.config.form.RouteParseForm;
import cm.commons.config.parse.RouteParse;
import cm.commons.util.unicode.UnicodeReader;

import junit.framework.TestCase;

public class UnicodeTest extends TestCase{

	public void testCode() throws JSONException, IllegalAccessException, InstantiationException{
		 File f  = new File("c:/config/route.txt");  
	        FileInputStream in;
			try {
				in = new FileInputStream(f);
				// 指定读取文件时以UTF-8的格式读取  
//	            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));  
				//改造后的方式
				BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));  
				String line = new String();
				StringBuffer sb = new StringBuffer();
				while((line=br.readLine()) != null)  
				{  
					sb.append(line);
				}  
				System.out.println(sb.toString());  
				RouteParse rp = new RouteParse();
				List<RouteParseForm> list = rp.parseJson(sb.toString());
				for(RouteParseForm r: list){
					System.out.println(r);
				}
				System.out.println("count:"+rp.getCount());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	}
}
