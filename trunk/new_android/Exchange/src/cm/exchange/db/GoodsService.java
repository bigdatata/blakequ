package cm.exchange.db;

import java.util.ArrayList;
import cm.exchange.util.ConstantValue;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.SyncStateContract.Columns;
import cm.exchange.entity.Goods;

public class GoodsService extends DBService<Goods>{

	Context context;
	int pageSize = ConstantValue.PAGESIZE;
	public final static String TABLE_NAME = "goods";
	public final static String CREATE_TABLE_SQL = "create table "+TABLE_NAME+" ("+
			GoodsColumns._ID + " integer primary key autoincrement,"+
			GoodsColumns.ID + " integer not null,"+
			GoodsColumns.CREATE_USER + "  text not null,"+
			GoodsColumns.NAME + "  text,"+
			GoodsColumns.CREATE_TIME + "  datetime not null,"+
			GoodsColumns.SALE_TIME + "  datetime not null,"+
			GoodsColumns.IS_HAVE_PIC + " boolean,"+
			GoodsColumns.PIC_ADDRESS + "  text,"+
			GoodsColumns.GOODS_STATE + " integer,"+
			GoodsColumns.CATAGORY + " integer,"+
			GoodsColumns.PRICE + " integer,"+
			GoodsColumns.DETAIL + " text,"+
			GoodsColumns.IS_DISCUSS + " boolean,"+
			GoodsColumns.LONGITUDE + " text,"+
			GoodsColumns.LATITUDE + " text,"+
			GoodsColumns.COMMENT_NUM + " integer,"+
			GoodsColumns.DISTANCE + " integer)";
	
	public GoodsService(Context context) {
		super(context, TABLE_NAME);
		this.context = context;
	}
	
	private final class GoodsColumns implements BaseColumns{
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
		public static final String DISTANCE = "distance";
	}

	@Override
	public List<Goods> getAllData() {
		// TODO Auto-generated method stub
		List<Goods> list = null;
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{GoodsColumns.ID,
				GoodsColumns.CREATE_USER, GoodsColumns.NAME, GoodsColumns.CREATE_TIME, GoodsColumns.SALE_TIME,
				GoodsColumns.IS_HAVE_PIC, GoodsColumns.PIC_ADDRESS, GoodsColumns.GOODS_STATE, GoodsColumns.CATAGORY,
				GoodsColumns.PRICE, GoodsColumns.DETAIL, GoodsColumns.COMMENT_NUM, GoodsColumns.LONGITUDE,
				GoodsColumns.LATITUDE, GoodsColumns.DISTANCE, GoodsColumns.IS_DISCUSS},
				null, null, null, null, GoodsColumns.CREATE_TIME+" desc");
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(0));
				good.setCreateUser(cursor.getString(1));
				good.setName(cursor.getString(2));
				good.setCreateData(cursor.getString(3));
				good.setSaleData(cursor.getString(4));
				good.setHavePicture(cursor.getString(5).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(6));
				good.setGoodsState(cursor.getInt(7));
				good.setCatagory(cursor.getInt(8));
				good.setPrice(cursor.getInt(9));
				good.setDescript(cursor.getString(10));
				good.setCommentNum(cursor.getInt(11));
				good.setLongitude(cursor.getString(12));
				good.setLatitude(cursor.getString(13));
				good.setDistance(cursor.getInt(14));
				good.setDiscussing(cursor.getString(15).equals("false")?false:true);
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	/**
	 * get the number of rows
	 * @param pageSize you want the number of one page ,we should use {@link #pageSize}
	 * @return the total pages
	 */
	public int getPageNumber(int pageSize){
		int totalNum = getItemCount();
		return (totalNum%pageSize == 0)?(totalNum/pageSize):(int)(totalNum/pageSize + 1);
	}
	
	/**
	 * default 
	 * @return
	 */
	public int getPageNumber(){
		return getPageNumber(pageSize);
	}
	
	/**
	 * @return the row number of table
	 */
	public int getItemCount(){
		Cursor cursor = getDatabase().rawQuery("select count(*) from "+TABLE_NAME, null);
		boolean b = cursor.moveToLast();    
        int totalNum = (int)cursor.getLong(0);
        cursor.close();
        return totalNum;
	}
	
	/**
	 * get the row number of data
	 * @param length the row number of data
	 * @return
	 */
	public List<Goods> getLimitData(int length){
		List<Goods> list = null;
		String sql= "select * from " + TABLE_NAME + " order by createTime" +    
        " Limit "+String.valueOf(length);    
        Cursor cursor = getDatabase().rawQuery(sql, null);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	/**
	 * the zero-based position to move to.
	 * @param pageNum you must notice : the first page number begin with 0.
	 * @return if over the page size will return null.
	 * @return pageSize 
	 */
	public List<Goods> getAllData(int pageNum, int pageSize) {
		List<Goods> list = null;
		String sql= "select * from " + TABLE_NAME + " order by createTime desc " +    
        " Limit "+String.valueOf(pageSize)+ " Offset " +String.valueOf(pageNum*pageSize);    
        Cursor cursor = getDatabase().rawQuery(sql, null);
		Goods good = null;
		int pages = getPageNumber(pageSize);
		if(pageNum > pages-1){
			return null;
		}else{
			cursor.moveToFirst();
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	/**
	 * order by create time 
	 * @return the data list
	 */
	public List<Goods> getDataByTime(){
		List<Goods> list = null;
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{GoodsColumns.ID,
				GoodsColumns.CREATE_USER, GoodsColumns.NAME, GoodsColumns.CREATE_TIME, GoodsColumns.SALE_TIME,
				GoodsColumns.IS_HAVE_PIC, GoodsColumns.PIC_ADDRESS, GoodsColumns.GOODS_STATE, GoodsColumns.CATAGORY,
				GoodsColumns.PRICE, GoodsColumns.DETAIL, GoodsColumns.COMMENT_NUM, GoodsColumns.LONGITUDE,
				GoodsColumns.LATITUDE, GoodsColumns.DISTANCE, GoodsColumns.IS_DISCUSS},
				null, null, null, null, GoodsColumns.CREATE_TIME+" desc");
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(0));
				good.setCreateUser(cursor.getString(1));
				good.setName(cursor.getString(2));
				good.setCreateData(cursor.getString(3));
				good.setSaleData(cursor.getString(4));
				good.setHavePicture(cursor.getString(5).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(6));
				good.setGoodsState(cursor.getInt(7));
				good.setCatagory(cursor.getInt(8));
				good.setPrice(cursor.getInt(9));
				good.setDescript(cursor.getString(10));
				good.setCommentNum(cursor.getInt(11));
				good.setLongitude(cursor.getString(12));
				good.setLatitude(cursor.getString(13));
				good.setDistance(cursor.getInt(14));
				good.setDiscussing(cursor.getString(15).equals("false")?false:true);
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
		
	}
	
	/**
	 * order by price ,default asc
	 * @return the list of goods
	 */
	public List<Goods> getDataSortByPrice(){
		List<Goods> list = null;
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{GoodsColumns.ID,
				GoodsColumns.CREATE_USER, GoodsColumns.NAME, GoodsColumns.CREATE_TIME, GoodsColumns.SALE_TIME,
				GoodsColumns.IS_HAVE_PIC, GoodsColumns.PIC_ADDRESS, GoodsColumns.GOODS_STATE, GoodsColumns.CATAGORY,
				GoodsColumns.PRICE, GoodsColumns.DETAIL, GoodsColumns.COMMENT_NUM, GoodsColumns.LONGITUDE,
				GoodsColumns.LATITUDE, GoodsColumns.DISTANCE, GoodsColumns.IS_DISCUSS},
				null, null, null, null, GoodsColumns.PRICE);
//		String sql= "select * from " + TABLE_NAME +  " order by "+GoodsColumns.PRICE+   
//        " Limit "+String.valueOf(pageSize)+ " Offset " +String.valueOf(pageNum*pageSize);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(0));
				good.setCreateUser(cursor.getString(1));
				good.setName(cursor.getString(2));
				good.setCreateData(cursor.getString(3));
				good.setSaleData(cursor.getString(4));
				good.setHavePicture(cursor.getString(5).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(6));
				good.setGoodsState(cursor.getInt(7));
				good.setCatagory(cursor.getInt(8));
				good.setPrice(cursor.getInt(9));
				good.setDescript(cursor.getString(10));
				good.setCommentNum(cursor.getInt(11));
				good.setLongitude(cursor.getString(12));
				good.setLatitude(cursor.getString(13));
				good.setDistance(cursor.getInt(14));
				good.setDiscussing(cursor.getString(15).equals("false")?false:true);
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	public List<Goods> getLimitDataByPrice(int catagory){
		return getLimitDataByPrice(catagory, 0);
	}
	
	/**
	 * get data by catagory
	 * @param catagory the catagory of goods
	 * @param sort the sort of price, 0 ascending order， 1 descending order，default 0
	 * @return
	 */
	public List<Goods> getLimitDataByPrice(int catagory, int sort){
		String sql;
		List<Goods> list = null;
		if(sort == 1){
			sql = "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory+" order by "+GoodsColumns.PRICE+" desc ";
		}else{
			sql = "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory+" order by "+GoodsColumns.PRICE;
		}
		Cursor cursor = getDatabase().rawQuery(sql, null);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
		
	}
	
	/**
	 * return the row of distance between 0 to distance
	 * @param distance 
	 * @return
	 */
	public List<Goods> getLimitDataByDistance(int distance){
		return getLimitDataByDistance(-1, distance);
	}
	
	/**
	 * 
	 * @param catagory the catagory of goods
	 * @param distance the distance 
	 * @return
	 */
	public List<Goods> getLimitDataByDistance(int catagory ,int distance){
		List<Goods> list = null;
		String sql;
		if(catagory == -1){
			sql = "select * from " + TABLE_NAME + " where "+GoodsColumns.DISTANCE+"<"+distance+" order by createTime desc ";
		}else{
			sql= "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory
				+" and "+GoodsColumns.DISTANCE+"<"+distance+" order by createTime desc ";
		}
		Cursor cursor = getDatabase().rawQuery(sql, null);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	/**
	 * order by distance , default asc(升序)
	 * @return list of goods
	 */
	public List<Goods> getDataSortByDistance(){
		List<Goods> list = null;
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{GoodsColumns.ID,
				GoodsColumns.CREATE_USER, GoodsColumns.NAME, GoodsColumns.CREATE_TIME, GoodsColumns.SALE_TIME,
				GoodsColumns.IS_HAVE_PIC, GoodsColumns.PIC_ADDRESS, GoodsColumns.GOODS_STATE, GoodsColumns.CATAGORY,
				GoodsColumns.PRICE, GoodsColumns.DETAIL, GoodsColumns.COMMENT_NUM, GoodsColumns.LONGITUDE,
				GoodsColumns.LATITUDE, GoodsColumns.DISTANCE, GoodsColumns.IS_DISCUSS},
				null, null, null, null, GoodsColumns.DISTANCE);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(0));
				good.setCreateUser(cursor.getString(1));
				good.setName(cursor.getString(2));
				good.setCreateData(cursor.getString(3));
				good.setSaleData(cursor.getString(4));
				good.setHavePicture(cursor.getString(5).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(6));
				good.setGoodsState(cursor.getInt(7));
				good.setCatagory(cursor.getInt(8));
				good.setPrice(cursor.getInt(9));
				good.setDescript(cursor.getString(10));
				good.setCommentNum(cursor.getInt(11));
				good.setLongitude(cursor.getString(12));
				good.setLatitude(cursor.getString(13));
				good.setDistance(cursor.getInt(14));
				good.setDiscussing(cursor.getString(15).equals("false")?false:true);
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	/**
	 * get data order by price and distance
	 * @param catagory the catagory of goods
	 * @param distance the distance ,if you want get all set distance -1
	 * @param sortPrice the sort of price (asc，desc),0 ascending order， 1 descending order，default 0
	 * @return
	 */
	public List<Goods> getLimitDataByPriceAndDistance(int catagory, int distance, int sortPrice){
		String sql;
		List<Goods> list = null;
		if(distance == -1 && sortPrice == 1){
			sql= "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory
			+" order by "+GoodsColumns.PRICE+" desc ";
		}else if(distance == -1 && sortPrice == 0){
			sql= "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory
			+" order by "+GoodsColumns.PRICE;
		}else if(sortPrice == 0){
			sql= "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory
			+" and "+GoodsColumns.DISTANCE+"<"+distance+" order by "+GoodsColumns.PRICE;
		}else{
			sql= "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory
			+" and "+GoodsColumns.DISTANCE+"<"+distance+" order by "+GoodsColumns.PRICE+" desc ";
		}
		Cursor cursor = getDatabase().rawQuery(sql, null);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
		
	}
	
	/**
	 * get data order by price and distance
	 * @param distance the distance ,if you want get all set distance -1
	 * @param sortPrice the sort of price (asc，desc),0 ascending order， 1 descending order，default 0
	 * @param ids can not set null, if ids is null will return null
	 * @return
	 */
	public List<Goods> getLimitDataByPriceAndDistance(int distance, int sortPrice, String[] ids){
		String sql;
		List<Goods> list = null;
		if(ids == null || ids.length == 0) return null;
		if(distance == -1){
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< ids.length; i++){
				sb.append(" id="+ids[i]+" or");
			}
			sb.delete(sb.length()-3, sb.length());
			if(sortPrice == 1){
				sql= "select * from " + TABLE_NAME + " where "+ sb.toString()
				+" order by "+GoodsColumns.PRICE+" desc ";
			}else{
				sql= "select * from " + TABLE_NAME + " where "+ sb.toString()
				+" order by "+GoodsColumns.PRICE;
			}
		}else{
			StringBuilder sb1 = new StringBuilder();
			for(int i=0; i< ids.length; i++){
				sb1.append(" id="+ids[i]+" and "+GoodsColumns.DISTANCE+"<"+distance+" or");
			}
			sb1.delete(sb1.length()-3, sb1.length());
			if(sortPrice == 0){
				sql= "select * from " + TABLE_NAME + " where "+ sb1.toString()
				+" order by "+GoodsColumns.PRICE;
			}else{
				sql= "select * from " + TABLE_NAME + " where "+ sb1.toString()
				+" order by "+GoodsColumns.PRICE+" desc ";
			}
		}
		
		
		Cursor cursor = getDatabase().rawQuery(sql, null);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
		
	}
	
	/**
	 * sort by hot(comment number)
	 * @return
	 */
	public List<Goods> getDataSortByHot(){
		List<Goods> goods = getDataSortByHot(-1);
		return goods;
	}
	
	/**
	 * get the number of list item
	 * @param number the number of item
	 * @return
	 */
	public List<Goods> getDataSortByHot(int number){
		List<Goods> list = null;
		int i = number;
		if(i == -1) i = getItemCount();
		else if(i<-1) return null;
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{GoodsColumns.ID,
				GoodsColumns.CREATE_USER, GoodsColumns.NAME, GoodsColumns.CREATE_TIME, GoodsColumns.SALE_TIME,
				GoodsColumns.IS_HAVE_PIC, GoodsColumns.PIC_ADDRESS, GoodsColumns.GOODS_STATE, GoodsColumns.CATAGORY,
				GoodsColumns.PRICE, GoodsColumns.DETAIL, GoodsColumns.COMMENT_NUM, GoodsColumns.LONGITUDE,
				GoodsColumns.LATITUDE, GoodsColumns.DISTANCE, GoodsColumns.IS_DISCUSS},
				null, null, null, null, GoodsColumns.COMMENT_NUM+" desc");
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				i --;
				good = new Goods();
				good.setId(cursor.getInt(0));
				good.setCreateUser(cursor.getString(1));
				good.setName(cursor.getString(2));
				good.setCreateData(cursor.getString(3));
				good.setSaleData(cursor.getString(4));
				good.setHavePicture(cursor.getString(5).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(6));
				good.setGoodsState(cursor.getInt(7));
				good.setCatagory(cursor.getInt(8));
				good.setPrice(cursor.getInt(9));
				good.setDescript(cursor.getString(10));
				good.setCommentNum(cursor.getInt(11));
				good.setLongitude(cursor.getString(12));
				good.setLatitude(cursor.getString(13));
				good.setDistance(cursor.getInt(14));
				good.setDiscussing(cursor.getString(15).equals("false")?false:true);
				list.add(good);
			}while(cursor.moveToNext() && i>0);
		}
		cursor.close();
		return list;
	}
	
	/**
	 * get all data by catagory
	 * @param catagory the catagory of goods, if -1 will return all data
	 * @return
	 */
	public List<Goods> getDataByCatagory(int catagory){
		List<Goods> list = null;
		String sql;
		if(catagory == -1){
			sql = "select * from " + TABLE_NAME + " order by createTime desc ";
		}else{
			sql= "select * from " + TABLE_NAME + " where "+GoodsColumns.CATAGORY+"="+catagory
				+" order by price";
		}
		Cursor cursor = getDatabase().rawQuery(sql, null);
		Goods good = null;
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}

	/**
	 * get data by id
	 * @param id the id of goods
	 * @return if id is not exists will return null, otherwise return Goods
	 */
	public Goods getDataById(int id){
		Goods good = null;
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{GoodsColumns.ID,
				GoodsColumns.CREATE_USER, GoodsColumns.NAME, GoodsColumns.CREATE_TIME, GoodsColumns.SALE_TIME,
				GoodsColumns.IS_HAVE_PIC, GoodsColumns.PIC_ADDRESS, GoodsColumns.GOODS_STATE, GoodsColumns.CATAGORY,
				GoodsColumns.PRICE, GoodsColumns.DETAIL, GoodsColumns.COMMENT_NUM, GoodsColumns.LONGITUDE,
				GoodsColumns.LATITUDE, GoodsColumns.DISTANCE, GoodsColumns.IS_DISCUSS},
				"id = "+id, null, null, null, null);
		if(cursor.moveToFirst()){
				good = new Goods();
				good.setId(cursor.getInt(0));
				good.setCreateUser(cursor.getString(1));
				good.setName(cursor.getString(2));
				good.setCreateData(cursor.getString(3));
				good.setSaleData(cursor.getString(4));
				good.setHavePicture(cursor.getString(5).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(6));
				good.setGoodsState(cursor.getInt(7));
				good.setCatagory(cursor.getInt(8));
				good.setPrice(cursor.getInt(9));
				good.setDescript(cursor.getString(10));
				good.setCommentNum(cursor.getInt(11));
				good.setLongitude(cursor.getString(12));
				good.setLatitude(cursor.getString(13));
				good.setDistance(cursor.getInt(14));
				good.setDiscussing(cursor.getString(15).equals("false")?false:true);
		}
		cursor.close();	
		return good;
	}
	
	
	/**
	 * get data by id, if no data return null
	 * @param ids the list of goods id
	 * @return
	 */
	public List<Goods> getDataById(List<Integer> ids){
		String[] strs = new String[ids.size()];
		for(int i=0; i<ids.size(); i++){
			strs[i] = String.valueOf(ids.get(i));
		}
		return getDataById(strs);
	}
	
	/**
	 * get data by id, if array length is 0, will return null
	 * @param ids the list of goods id
	 * @return
	 */
	public List<Goods> getDataById(String[] ids){
		if(ids.length == 0){
			return null;
		}
		Goods good = null;
		List<Goods> list = null;
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< ids.length; i++){
			sb.append(" id="+ids[i]+" or");
		}
		sb.delete(sb.length()-3, sb.length());
		String sql = "select * from "+TABLE_NAME+" where "+sb.toString();
		Cursor cursor = getDatabase().rawQuery(sql, null);
		if(cursor.moveToFirst()){
			list = new ArrayList<Goods>();
			do{
				good = new Goods();
				good.setId(cursor.getInt(1));
				good.setCreateUser(cursor.getString(2));
				good.setName(cursor.getString(3));
				good.setCreateData(cursor.getString(4));
				good.setSaleData(cursor.getString(5));
				good.setHavePicture(cursor.getString(6).equals("false")?false:true);
				good.setPictureAddress(cursor.getString(7));
				good.setGoodsState(cursor.getInt(8));
				good.setCatagory(cursor.getInt(9));
				good.setPrice(cursor.getInt(10));
				good.setDescript(cursor.getString(11));
				good.setDiscussing(cursor.getString(12).equals("false")?false:true);
				good.setLongitude(cursor.getString(13));
				good.setLatitude(cursor.getString(14));
				good.setCommentNum(cursor.getInt(15));
				good.setDistance(cursor.getInt(16));
				list.add(good);
			}while(cursor.moveToNext());
		}
		cursor.close();	
		return list;
	}
	
	
	
	
	/**
	 * 
	 * @param t the new data will be update
	 * @return
	 */
	public boolean update(Goods t){
		if(checkHaveDataById(t.getId())){
			ContentValues values = new ContentValues();
			values.put(GoodsColumns.ID, t.getId());
			values.put(GoodsColumns.CREATE_USER, t.getCreateUser());
			values.put(GoodsColumns.NAME, t.getName());
			values.put(GoodsColumns.CREATE_TIME, t.getCreateData());
			values.put(GoodsColumns.SALE_TIME, t.getSaleData());
			values.put(GoodsColumns.IS_HAVE_PIC, t.isHavePicture());
			values.put(GoodsColumns.PIC_ADDRESS, t.getPictureAddress());
			values.put(GoodsColumns.GOODS_STATE, t.getGoodsState());
			values.put(GoodsColumns.CATAGORY, t.getCatagory());
			values.put(GoodsColumns.PRICE, t.getPrice());
			values.put(GoodsColumns.DETAIL, t.getDescript());
			values.put(GoodsColumns.IS_DISCUSS, t.isDiscussing());
			values.put(GoodsColumns.LONGITUDE, t.getLongitude());
			values.put(GoodsColumns.LATITUDE, t.getLatitude());
			values.put(GoodsColumns.COMMENT_NUM, t.getCommentNum());
			values.put(GoodsColumns.DISTANCE, t.getDistance());
	//		int i = getDatabase().update(TABLE_NAME, values, "id = ?", new String[]{t.getId()+""});
			int i = getDatabase().update(TABLE_NAME, values, "id = "+t.getId(), null);
			return i<0 ? false:true;
		}
		return insert(t);
	}
	
	
	@Override
	public boolean insert(Goods t) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(GoodsColumns.ID, t.getId());
		values.put(GoodsColumns.CREATE_USER, t.getCreateUser());
		values.put(GoodsColumns.NAME, t.getName());
		values.put(GoodsColumns.CREATE_TIME, t.getCreateData());
		values.put(GoodsColumns.SALE_TIME, t.getSaleData());
		values.put(GoodsColumns.IS_HAVE_PIC, t.isHavePicture());
		values.put(GoodsColumns.PIC_ADDRESS, t.getPictureAddress());
		values.put(GoodsColumns.GOODS_STATE, t.getGoodsState());
		values.put(GoodsColumns.CATAGORY, t.getCatagory());
		values.put(GoodsColumns.PRICE, t.getPrice());
		values.put(GoodsColumns.DETAIL, t.getDescript());
		values.put(GoodsColumns.IS_DISCUSS, t.isDiscussing());
		values.put(GoodsColumns.LONGITUDE, t.getLongitude());
		values.put(GoodsColumns.LATITUDE, t.getLatitude());
		values.put(GoodsColumns.COMMENT_NUM, t.getCommentNum());
		values.put(GoodsColumns.DISTANCE, t.getDistance());
		return getDatabase().insert(TABLE_NAME, null, values) > -1;
	}
	
	public int deleteAll(){
		return getDatabase().delete(TABLE_NAME, "1", null);
	}
	
	/**
	 * 查找是否有指定id的数据
	 * @param id
	 * @return
	 */
	public boolean checkHaveDataById(int id){
		//if table is null, return false
		if(getItemCount() == 0)
			return false;
		Cursor cursor = getDatabase().rawQuery("select "+GoodsColumns.ID+" from "+TABLE_NAME+" where id = "+id, null);
		//if have data return true, otherwise false
		boolean b = cursor.moveToLast();    
        cursor.close();
		return b;
	}
	
}
