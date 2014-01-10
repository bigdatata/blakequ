package cm.exchange.net;

import java.net.URLEncoder;

/**
 * this class using store the constant of post url
 * 
 * @author qh
 * 
 */
public class URLConstant {

	public static final String BASEURL = "http://222.197.176.8:8080/exchange/";

	public static final String GOODSINFO = BASEURL + "allgoods.action";
	public static final String SIGNLGOODS = BASEURL + "goodsinfo.action";
	public static final String COMMENTINFO = BASEURL + "commentinfo.action";
	public static final String USER_BUY = BASEURL + "buyinfo.action";
	public static final String USER_ATTENTION = BASEURL + "concerninfo.action";
	public static final String LOGIN = BASEURL + "login.action";
	public static final String REGISTER = BASEURL + "register.action";
	public static final String USER = BASEURL + "userinfo.action";
	public static final String SEARCH = BASEURL + "goodsname.action";
	// 关注商品
	public static final String ATTENTION = BASEURL + "goodsAttention.action";
	public static final String GOODS_STATE_CHANGE = BASEURL+ "goodsState.action";
	public static final String GOODS_DISTANCE = BASEURL+ "goodsDistance.action";
	// http://localhost:8080/exchange/goodsDistance.action?longitude=32.3222&latitude=443.4343&distance=100
	public static final String GOODS_NOTE_UPLOAD = BASEURL + "goodsNote.action";// 0(已售出),1(正在出售)""
	// note = URLEncoder.encode(note, "UTF-8");将文字转码
	// http://222.197.176.49:8080/exchange/goodsUpload.action?name=fa&catagory=1&state=0&price=32&isDiscuss=0&longitude=32.33&latitude=32.33&phone=1344444444&qq=21321312&location=4324324&discription=nidefe
	public static final String GOODS_UPLOAD = BASEURL + "goodsUpload.action";
	public static final String PIC_UPLOAD = BASEURL + "picUpload.action";


}
