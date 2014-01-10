package cm.exchange.parser;

import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import cm.exchange.entity.Goods;

/**
 * 
 * @author qh
 *
 */
public class GoodsListParser extends BasePullParserFactory<Goods> {

	Goods goods; 
	private String pageNum, totalNum, attributeName;
	private boolean isHavePic;
	
	public static final String ID = "id";
	public static final String CREATE_USER = "createUser";
	public static final String NAME = "name";
	public static final String CREATE_TIME = "createTime";
	public static final String SALE_TIME = "saleTime";
	public static final String IS_HAVE_PIC = "isHavePic";
	public static final String PIC_ADDRESS = "pictureAddress";
	public static final String GOODS_STATE = "goodsState";
	public static final String CATAGORY = "catagory";
	public static final String PRICE = "price";
	public static final String DETAIL = "detail";
	public static final String IS_DISCUSS = "isDiscussing";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String COMMENT_NUM = "commentNum";
	
	public static final String TOTALPAGES = "totalPages";
	public static final String TOTALNUM = "totalNum";
	public static final String GOODSLIST = "goodsList";
	public static final String GOODS = "goods";
	
	@Override
	public void parse(InputStream is, ParserListener<Goods> listener)
			throws Exception {
		// TODO Auto-generated method stub
		XmlPullParser parser = createXmlPullParser(is);
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while(eventType != XmlPullParser.END_DOCUMENT){
			String nodeName = parser.getName();
			switch(eventType){
				case XmlPullParser.START_TAG:
					if(nodeName.equals(GOODSLIST)){
						attributeName = parser.getAttributeName(0);
						if(attributeName.equals(TOTALPAGES)){
							setPageNum(parser.getAttributeValue(null, TOTALPAGES));
						}else{
							setTotalNum(parser.getAttributeValue(null, TOTALNUM));
						}
					}
					if(nodeName.equals(GOODS)){
						goods = new Goods();
					}
					if(goods != null){
						if(nodeName.equals(ID)){
							goods.setId(Integer.valueOf(parser.nextText()));
						}
						if(nodeName.equals(CREATE_USER)){
							goods.setCreateUser(parser.nextText());
						}
						if(nodeName.equals(NAME)){
							goods.setName(parser.nextText());
						}
						if(nodeName.equals(CREATE_TIME)){
							goods.setCreateData(parser.nextText());
						}
						if(nodeName.equals(SALE_TIME)){
							goods.setSaleData(parser.nextText());
						}
						if(nodeName.equals(IS_HAVE_PIC)){
							isHavePic = parser.nextText().equals("false") ? false:true;
							goods.setHavePicture(isHavePic);
						}
						if(isHavePic){
							if(nodeName.equals(PIC_ADDRESS)){
								goods.setPictureAddress(parser.nextText());
							}
						}
						if(nodeName.equals(GOODS_STATE)){
							goods.setGoodsState(Integer.valueOf(parser.nextText()));
						}
						if(nodeName.equals(CATAGORY)){
							goods.setCatagory(Integer.valueOf(parser.nextText()));
						}
						if(nodeName.equals(PRICE)){
							goods.setPrice(Integer.valueOf(parser.nextText()));
						}
						if(nodeName.equals(DETAIL)){
							goods.setDescript(parser.nextText());
						}
						if(nodeName.equals(IS_DISCUSS)){
							goods.setDiscussing(parser.nextText().equals("false")?false:true);
						}
						if(nodeName.equals(LONGITUDE)){
							goods.setLongitude(parser.nextText());
						}
						if(nodeName.equals(LATITUDE)){
							goods.setLatitude(parser.nextText());
						}
						if(nodeName.equals(COMMENT_NUM)){
							goods.setCommentNum(Integer.valueOf(parser.nextText()));
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if(nodeName.equals(GOODS)){
						listener.onParserOverListener(goods);
					}
					break;
				default:
					break;
			}
			eventType = parser.next();
		}
		//判断是不是有数据
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
	public Goods parser(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goods> parserToList(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
