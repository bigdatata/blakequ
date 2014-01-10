package cm.exchange.parser;

import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

/**
 * login back imformation
 * @author qh
 *
 */
public class LoginParser  extends BasePullParserFactory<Integer> {

	private static final String INFO = "info";
	private static final String ERROR = "error";
	@Override
	public void parse(InputStream is, ParserListener<Integer> listener)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer parser(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		int type = -1;
		XmlPullParser parser = createXmlPullParser(is);
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String nodeName = parser.getName();
			switch (eventType) {
				case XmlPullParser.START_TAG:
					if(nodeName.equals(INFO)){
						type = Integer.valueOf(parser.nextText());
					}
					if(nodeName.equals(ERROR)){
						type = Integer.valueOf(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
			eventType = parser.next();
		}
		return type;
	}

	@Override
	public List<Integer> parserToList(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
