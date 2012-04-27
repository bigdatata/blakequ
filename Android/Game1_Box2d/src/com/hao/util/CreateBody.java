package com.hao.util;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 * ��������Body
 * @author Administrator
 *
 */
public class CreateBody {
	public final float RATE;
	private World world;
	public float restitution;  //�ָ���
	public float friction;		//Ħ����
	
	/**
	 * 
	 * @param world �����������
	 */
	public CreateBody(World world){
		this.world = world;
		this.RATE = 30;
		this.restitution = 0.3f;
		this.friction = 0.8f;
	}
	/**
	 * 
	 * @param world �����������
	 * @param RATE  �������������ر���
	 */
	public CreateBody(World world, float RATE){
		this.world = world;
		this.RATE = RATE;
		this.restitution = 0.3f;
		this.friction = 0.8f;
	}

	/**
	 * ����һ������
	 * @param x			x����
	 * @param y			y����
	 * @param width		���
	 * @param height	�߶�
	 * @param isStatic	�Ƿ�̬
	 * @param userData  �Զ����û�����
	 * @return
	 */
	public Body createRectangle(float x, float y, float width, float height, boolean isStatic, Object userData) {
		//1.����Ƥ��
		PolygonDef pd = new PolygonDef();
		if(isStatic){
			pd.density = 0;
		}else{
			pd.density = 1;		//���ö���ε�����
		}
		pd.friction = this.friction;		//���ö���ε�Ħ����
		pd.restitution = this.restitution;	//���ö���εĻָ���
		pd.setAsBox(width / 2 /RATE, height / 2 / RATE);
		
		//2.�������壬����
		BodyDef bd = new BodyDef();
		bd.position.set((x + width/2)/RATE, (y + height/2)/RATE);
		
		//3.�������壬��Ƥ���͹�����װ����
		Body body = world.createBody(bd);//������������ģ��
		if(userData != null) body.m_userData = userData;
		body.createShape(pd);
		body.setMassFromShapes();	//���������������
		return body;
	}
	
	/**
	 * ����һ��Բ��
	 * @param x	x����
	 * @param y y����
	 * @param r	�뾶
	 * @param isStatic
	 * @param userData  �Զ����û�����
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
	 * ����һ������Σ�����һ�������ζ���float[] Poly_Vertices = { -1, -1, 1, -1, 0, 1 };����������body���õ���������(����������Ϊ(0,0))��
	 * @param vertices	���������
	 * @param x	���������x����
	 * @param y ���������y����
	 * @param width ���
	 * @param height �߶�
	 * @param isStatic
	 * @param userData  �Զ����û�����
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
		//Ϊ�������Ӷ���
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
	 * ��ȡ�ָ���
	 * @return
	 */
	public float getRestitution() {
		return restitution;
	}

	/**
	 * ��ȡĦ����
	 * @return
	 */
	public float getFriction() {
		return friction;
	}

	/**
	 * ���ûָ���
	 * @param restitution
	 */
	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	/**
	 * ����Ħ����
	 * @param friction
	 */
	public void setFriction(float friction) {
		this.friction = friction;
	}
}
