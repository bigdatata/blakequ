package cm.exchange.parser.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cm.exchange.parser.ShopAndAttenParser;
import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

public class ShopAndAttenParserTest  extends AndroidTestCase{
	ShopAndAttenParser parser = null;
	Context context = null;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		parser = new ShopAndAttenParser();
		context = getContext();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testParser(){
		InputStream is = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			is = context.getAssets().open("attention.xml");
			list = parser.parserToList(is);
			for(int i=0; i<list.size(); i++){
				System.out.print(list.get(i)+" ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("adapter", "open inputstream error");
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("adapter", "parser error");
		}
		finally
		{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
