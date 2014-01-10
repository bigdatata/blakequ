package cm.exchange.util.test;

import java.util.Date;

import cm.exchange.util.DateUtil;
import android.test.AndroidTestCase;

public class DateUtilTest  extends AndroidTestCase{
	
	public void testFormatData(){
		String s1 = DateUtil.formatData(new Date());
		String s2 = DateUtil.formatData("2012-05-31 11:12:16");
		System.out.println(s1+" "+s2);
	}
}
