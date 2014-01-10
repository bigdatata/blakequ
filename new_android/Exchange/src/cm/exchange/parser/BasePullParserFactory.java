package cm.exchange.parser;

import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * the factory of XmlPullParser
 * @author qh
 *
 * @param <T> entity class
 */
public abstract class BasePullParserFactory<T> {
	
	private static XmlPullParserFactory factory;
	static{
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			throw new IllegalStateException("Could not create a factory");
		}
	}
	
	/**
	 * parser a xml file until complete
	 * @param in inputstream of xml file
	 * @return list of T
	 * @throws Exception
	 */
	public abstract List<T> parserToList(InputStream in) throws Exception;
	
	/**
	 * parser a xml file and return single object 
	 * @param in inputstream of xml file
	 * @return a object of T type
	 * @throws Exception
	 */
	public abstract T parser(InputStream in) throws Exception;
	
	/**
	 * parser a xml file and return asynchronous object by listener
	 * @param is inputStream of xml file
	 * @param listener monitor the parser of xml file
	 * @throws Exception
	 */
	public abstract void parse(InputStream is, ParserListener<T> listener) throws Exception ;
	
	/**
	 * generate the {@link XmlPullParser}
	 * @param is inputStream 
	 * @return XmlPullParser
	 */
	public static XmlPullParser createXmlPullParser(InputStream is){
		XmlPullParser parser = null;
		try {
			parser = factory.newPullParser();
			parser.setInput(is, null);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parser;
	}
}
