package com.hao.util;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 * 创建各种Body
 * @author Administrator
 *
 */
public class CreateBody {
	public final float RATE;
	private World world;
	public float restitution;  //恢复力
	public float friction;		//摩擦力
	
	/**
	 * 
	 * @param world 物理世界对象
	 */
	public CreateBody(World world){
		this.world = world;
		this.RATE = 30;
		this.restitution = 0.3f;
		this.friction = 0.8f;
	}
	/**
	 * 
	 * @param world 物理世界对象
	 * @param RATE  物理世界与像素比例
	 */
	public CreateBody(World world, float RATE){
		this.world = world;
		this.RATE = RATE;
		this.restitution = 0.3f;
		this.friction = 0.8f;
	}

	/**
	 * 创建一个矩形
	 * @param x			x坐标
	 * @param y			y坐标
	 * @param width		宽度
	 * @param height	高度
	 * @param isStatic	是否静态
	 * @param userData  自定义用户数据
	 * @return
	 */
	public Body createRectangle(float x, float y, float width, float height, boolean isStatic, Object userData) {
		//1.创建皮肤
		PolygonDef pd = new PolygonDef();
		if(isStatic){
			pd.density = 0;
		}else{
			pd.density = 1;		//设置多边形的质量
		}
		pd.friction = this.friction;		//设置多边形的摩擦力
		pd.restitution = this.restitution;	//设置多边形的恢复力
		pd.setAsBox(width / 2 /RATE, height / 2 / RATE);
		
		//2.创建刚体，骨骼
		BodyDef bd = new BodyDef();
		bd.position.set((x + width/2)/RATE, (y + height/2)/RATE);
		
		//3.创建物体，将皮肤和骨骼组装起来
		Body body = world.createBody(bd);//建立物理世界模型
		if(userData != null) body.m_userData = userData;
		body.createShape(pd);
		body.setMassFromShapes();	//将整个物体计算打包
		return body;
	}
	
	/**
	 * 创建一个圆形
	 * @param x	x坐标
	 * @param y y坐标
	 * @param r	半径
	 * @param isStatic
	 * @param userData  自定义用户数据
	 * @return
	 */
	public Body createCircle(float x, float y, float r, boolean isStatic, Object userData) {
		CircleDef cd = new CircleDef();
		if(isStatic){
			cd.density = 0;
		}else{
			cd.density = 1;
		}
		cd.friction = this.friction;
		cd.restitution = this.restitution;
		cd.radius = r/RATE;
		
		BodyDef bd = new BodyDef();
		bd.position.set((x + r)/RATE, (y + r)/RATE);
		
		Body body = world.createBody(bd);
		if(userData != null) body.m_userData = userData;
		body.createShape(cd);
		body.setMassFromShapes();
		return body;
	}
	
	
	/**
	 * 创建一个多边形（例如一个三角形定义float[] Poly_Vertices = { -1, -1, 1, -1, 0, 1 };创建三角形body所用的三个顶点(三角形中心为(0,0))）
	 * @param vertices	多边形坐标
	 * @param x	多边形中心x坐标
	 * @param y 多边形中心y坐标
	 * @param width 宽度
	 * @param height 高度
	 * @param isStatic
	 * @param userData  自定义用户数据
	 * @return
	 */
	public Body createPolygon(float[] vertices, float x, float y, float width,
			float height, boolean isStatic, Object userData) {
		PolygonDef cd = new PolygonDef();
		if (isStatic) {
			cd.density = 0;
		} else {
			cd.density = 1;
		}
		cd.friction = this.friction;
		cd.restitution = this.restitution;
		//为多边形添加顶点
		if(vertices.length % 2 != 0) return null;
		for(int i=0; i<vertices.length; i+=2){
			cd.addVertex(new Vec2(vertices[i], vertices[i+1]));
		}
		
		BodyDef bd = new BodyDef();
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);
		
		Body body = world.createBody(bd);
		if(userData != null) body.m_userData = userData;
		body.createShape(cd);
		body.setMassFromShapes();
		return body;
	}


	/**
	 * 获取恢复力
	 * @return
	 */
	public float getRestitution() {
		return restitution;
	}

	/**
	 * 获取摩擦力
	 * @return
	 */
	public float getFriction() {
		return friction;
	}

	/**
	 * 设置恢复力
	 * @param restitution
	 */
	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	/**
	 * 设置摩擦力
	 * @param friction
	 */
	public void setFriction(float friction) {
		this.friction = friction;
	}
}
