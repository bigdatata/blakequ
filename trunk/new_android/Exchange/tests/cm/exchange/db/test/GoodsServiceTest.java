package cm.exchange.db.test;

import java.util.ArrayList;
import java.util.List;
import cm.exchange.db.GoodsService;
import cm.exchange.entity.Goods;
import android.test.AndroidTestCase;

public class GoodsServiceTest  extends AndroidTestCase {
	GoodsService gs = null;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		gs = new GoodsService(getContext());
		gs.open();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		gs.close();
	}
	
	public void testInsert(){
		boolean b = true;
		for(int i=0; i<10; i++){
			Goods goods = new Goods();
			goods.setCatagory(i*2);
			goods.setCommentNum(i);
			goods.setCreateData("2011-1-2");
			goods.setCreateUser("GGGGGGG");
			goods.setDescript("meiyou");
			goods.setDiscussing(true);
			goods.setDistance(i);
			goods.setGoodsState(1);
			goods.setHavePicture(false);
			goods.setId(i*i);
			goods.setName("shouji");
			goods.setPrice(23+i);
			goods.setSaleData("2001-21-3");
			b = gs.insert(goods);
			if(b == false) break;
		}
		assertEquals(true, b);
		
	}
	
	public void testInsert2(){
			Goods goods = new Goods();
			goods.setCatagory(1);
			goods.setCommentNum(9);
			goods.setCreateData("2011-1-2");
			goods.setCreateUser("李成");
			goods.setDescript("meiyou");
			goods.setDiscussing(true);
			goods.setDistance(30);
			goods.setGoodsState(1);
			goods.setHavePicture(false);
			goods.setId(9);
			goods.setName("shouji");
			goods.setPrice(50);
			goods.setSaleData("2001-21-3");
		assertEquals(true, gs.insert(goods));
		
	}
	public void testGetAllData(){
		List<Goods> list = gs.getAllData();
		for(Goods g:list){
			System.out.println("CommentNum:"+g.getCommentNum()+" Distance:"+g.getDistance()+" Price:"+g.getPrice()+" date:"+g.getCreateData());
		}
		assertNotNull(list);
	}
	
	public void testGetDataById2(){
		List<Integer> list = new ArrayList<Integer>();
		list.add(3);
		list.add(5);
		list.add(4);
		List<Goods> list2 = gs.getDataById(list);
		if(list2 != null)
			for(Goods g:list2){
				System.out.println("id:"+g.getId()+" Distance:"+g.getDistance()+" Price:"+g.getPrice()+" date:"+g.getCreateData());
			}
		else 
			System.out.println("list null");
		assertNotNull(list);
		
	}
	
	public void testGetDataSortByDistance(){
		List<Goods> list = gs.getDataSortByDistance();
		for(Goods g:list){
			System.out.println("CommentNum:"+g.getCommentNum()+" Distance:"+g.getDistance()+" Price:"+g.getPrice());
		}
		assertNotNull(list);
	}
	
	public void testGetDataSortByPrice(){
		List<Goods> list = gs.getDataSortByPrice();
		//通过下面可以实现反序
		for(int i=list.size()-1; i>=0; i--){
			Goods g = list.get(i);
			System.out.println("CommentNum:"+g.getCommentNum()+" Distance:"+g.getDistance()+" Price:"+g.getPrice());
		}
		assertNotNull(list);
	}
	
	public void testGetDataSortByHot(){
		List<Goods> list = gs.getDataSortByHot();
		for(Goods g:list){
			System.out.println("CommentNum:"+g.getCommentNum()+" Distance:"+g.getDistance()+" Price:"+g.getPrice());
		}
		assertNotNull(list);
	}
	
	
	public void testUpdate(){
		Goods g = gs.getDataById(3);
		System.out.println("["+g.getId()+", "+g.getCreateData()+", "+g.getCreateUser()+", "+g.getDescript()+", "+g.getName()+"]");
		Goods goods = new Goods();
		goods.setCatagory(111);
		goods.setCommentNum(111);
		goods.setCreateData("2011-1-2");
		goods.setCreateUser("李云");
		goods.setDescript("meiyou");
		goods.setDiscussing(true);
		goods.setDistance(30);
		goods.setGoodsState(1);
		goods.setHavePicture(false);
		goods.setId(3);
		goods.setName("iphone5出售");
		goods.setPrice(50);
		goods.setSaleData("2001-21-3");
	assertEquals(true, gs.update(goods));
	Goods gg = gs.getDataById(3);
	System.out.println("["+gg.getId()+", "+gg.getCreateData()+", "+gg.getCreateUser()+", "+gg.getDescript()+", "+gg.getName()+"]");
	
	}
	
	public void testGetDataById(){
		Goods g = gs.getDataById(3);
		if( g!= null)
			System.out.println("id:"+g.getId()+" Distance:"+g.getDistance()+" Price:"+g.getPrice());
		assertNotNull(g);
	}
	
	public void testDeleteAll(){
		assertEquals(14, gs.deleteAll());
	}
	
	public void testGetAllDateByPageNum(){
		for(int i=0; i<gs.getPageNumber(3); i++){
			List<Goods> list = gs.getAllData(i, 3);
			for(Goods g:list){
				System.out.println("createtime:"+g.getCreateData()+" Distance:"+g.getDistance()+" Price:"+g.getPrice());
			}
			System.out.println("--------------------------------");
		}
//		assertNotNull(list);
	}
	
	public void testGetPageNum(){
		assertEquals(3, gs.getPageNumber());
	}
	
	public void testCheckHaveDataById(){
		assertEquals(true, gs.checkHaveDataById(3));
	}
	
	public void testAll(){
		gs.getItemCount();
	}
}
