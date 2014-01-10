package cm.exchange.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

public class ShopAndAttenParser extends BasePullParserFactory<Integer> {

	private String totalNum;
	List<Integer> list = new ArrayList<Integer>();
	public static final String ID = "id";

	public static final String TOTALNUM = "totalNum";
	public static final String GOODSLIST = "goodsList";

	@Override
	public void parse(InputStream is, ParserListener<Integer> listener)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer parser(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> parserToList(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		XmlPullParser parser = createXmlPullParser(is);
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String nodeName = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (nodeName.equals(GOODSLIST)) {
					setTotalNum(parser.getAttributeValue(null, TOTALNUM));
				}
				if (nodeName.equals(ID)) {
					list.add(Integer.valueOf(parser.nextText()));
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			default:
				break;
			}
			eventType = parser.next();
		}
		return list;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

}
