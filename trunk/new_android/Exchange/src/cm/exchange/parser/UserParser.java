package cm.exchange.parser;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import cm.exchange.entity.User;

/**
 * 
 * @author qh
 *
 */
public class UserParser extends BasePullParserFactory<User>{

	User user = null;
	
	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String LOCATION = "location";
	public static final String TELEPHONE = "telephone";
	public static final String QQ = "qq";
	
	public static final String USER = "user";
	@Override
	public void parse(InputStream is, ParserListener<User> listener)
			throws Exception {
		XmlPullParser parser = createXmlPullParser(is);
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while(eventType != XmlPullParser.END_DOCUMENT){
			String nodeName = parser.getName();
			switch(eventType){
				case XmlPullParser.START_TAG:
					if(nodeName.equals(USER)){
						user = new User();
					}
					if(user != null){
						if(nodeName.equals(ID)){
							user.setId(Integer.valueOf(parser.nextText()));
						}
						if(nodeName.equals(USERNAME)){
							user.setUsername(parser.nextText());
						}
						if(nodeName.equals(LOCATION)){
							user.setLocation(parser.nextText());
						}
						if(nodeName.equals(TELEPHONE)){
							user.setTelephone(parser.nextText());
						}
						if(nodeName.equals(QQ)){
							user.setQq(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if(nodeName.equals(USER)){
						listener.onParserOverListener(user);
					}
					break;
				default:
					break;
			}
			eventType = parser.next();
		}
		
	}

	@Override
	public User parser(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		XmlPullParser parser = createXmlPullParser(is);
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while(eventType != XmlPullParser.END_DOCUMENT){
			String nodeName = parser.getName();
			switch(eventType){
				case XmlPullParser.START_TAG:
					if(nodeName.equals(USER)){
						user = new User();
					}
					if(user != null){
						if(nodeName.equals(ID)){
							user.setId(Integer.valueOf(parser.nextText()));
						}
						if(nodeName.equals(USERNAME)){
							user.setUsername(parser.nextText());
						}
						if(nodeName.equals(LOCATION)){
							user.setLocation(parser.nextText());
						}
						if(nodeName.equals(TELEPHONE)){
							user.setTelephone(parser.nextText());
						}
						if(nodeName.equals(QQ)){
							user.setQq(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
			}
			eventType = parser.next();
		}
		return user;
	}

	@Override
	public List<User> parserToList(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
