package cm.exchange.parser;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import cm.exchange.entity.Comment;

/**
 * 
 * @author qh
 *
 */
public class CommentListParser extends BasePullParserFactory<Comment>{

	Comment comment = null;
	private String pageNum, totalNum, attributeName;
	
	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String TIME = "time";
	public static final String CONTENT = "content";
	
	public static final String TOTALPAGES = "totalPages";
	public static final String TOTALNUM = "totalNum";
	public static final String COMMENT = "comment";
	public static final String COMMENT_LIST = "commentList";
	@Override
	public void parse(InputStream is, ParserListener<Comment> listener)
			throws Exception {
		// TODO Auto-generated method stub
		XmlPullParser parser = createXmlPullParser(is);
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while(eventType != XmlPullParser.END_DOCUMENT){
			String nodeName = parser.getName();
			switch(eventType){
				case XmlPullParser.START_TAG:
					if(nodeName.equals(COMMENT_LIST)){
						attributeName = parser.getAttributeName(0);
						if(attributeName.equals(TOTALPAGES)){
							setPageNum(parser.getAttributeValue(null, TOTALPAGES));
						}else{
							setTotalNum(parser.getAttributeValue(null, TOTALNUM));
						}
					}
					if(nodeName.equals(COMMENT)){
						comment = new Comment();
					}
					if(comment != null){
						if(nodeName.equals(ID)){
							comment.setUid(Integer.valueOf(parser.nextText()));
						}
						if(nodeName.equals(USERNAME)){
							comment.setUsername(parser.nextText());
						}
						if(nodeName.equals(TIME)){
							comment.setTime(parser.nextText());
						}
						if(nodeName.equals(CONTENT)){
							comment.setContent(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if(nodeName.equals(COMMENT)){
						listener.onParserOverListener(comment);
					}
					break;
				default:
					break;
			}
			eventType = parser.next();
		}
		
		if(attributeName.equals(TOTALPAGES))
		{
			if(getPageNum().equals("0"))
			{
				listener.onParserOverListener(null);
			}
		}else
		{
			if(getTotalNum().equals("0"))
			{
				listener.onParserOverListener(null);
			}
		}
		
	}
	
	

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	@Override
	public Comment parser(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> parserToList(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
