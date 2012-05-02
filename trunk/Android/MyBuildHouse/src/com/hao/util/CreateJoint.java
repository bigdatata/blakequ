package com.hao.util;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.GearJoint;
import org.jbox2d.dynamics.joints.GearJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.PulleyJoint;
import org.jbox2d.dynamics.joints.PulleyJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 * ���������ؽ�
 * @author Administrator
 *
 */
public class CreateJoint {

	private World world;
	public final float RATE;
	
	public CreateJoint(World world){
		this.world = world;
		RATE = 30;
	}
	
	public CreateJoint(World world, float RATE){
		this.world = world;
		this.RATE = RATE;
	}
	
	/**
	 * ��������ؽ�
	 * @return
	 */
	public DistanceJoint createDistanceJoint(Body body1, Body body2) {
		//����һ������ؽ�����ʵ��
		DistanceJointDef dje = new DistanceJointDef();
		// ��ʼ���ؽ�����
		dje.initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter());
		// dje.collideConnected=true;
		//��������ͨ������ľ���ؽ����ݴ���һ���ؽ�
		DistanceJoint dj = (DistanceJoint) world.createJoint(dje);
		return dj;
	}
	
	/**
	 * ��ת�ؽ�(��ת����)
	 * ������������ת����������world.getGroundBody()�����Լ����Լ���ת
	 * initialize(world.getGroundBody(), body1, body1.getWorldCenter());
	 * @param body1
	 * @param body2
	 * @param maxMotorTorque(1) ����Ԥ�����Ť��(Ť�����ٶȳɷ���)
	 * @param motorSpeed(20)     ���ת�٣�����/�룩
	 * @return
	 */
	public RevoluteJoint createRevoluteJoint1(Body body1, Body body2, float maxMotorTorque, float motorSpeed) {
		//����һ����ת�ؽڵ�����ʵ�� 
		RevoluteJointDef rjd = new RevoluteJointDef();
		//��ʼ����ת�ؽ�����
		rjd.initialize(body1, body2, body1.getWorldCenter());
		//�������,�Ƿ����ת��
		rjd.enableMotor = true;
		rjd.maxMotorTorque = maxMotorTorque; 
		rjd.motorSpeed = motorSpeed;
		//����world����һ����ת�ؽ�
		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);
		return rj ;
	}
	
	/**
	 * ��ת�ؽ�(���ΰ�)
	 * @param body1
	 * @param body2
	 * @param enableLimit �Ƿ�̶�
	 * @param lowerAngle ��ת��С�Ƕ�
	 * @param upperAngle ��ת���Ƕ�
	 * @return
	 */
	public RevoluteJoint createRevoluteJoint2(Body body1, Body body2,
			boolean enableLimit, float lowerAngle, float upperAngle) {
		//����һ����ת�ؽڵ�����ʵ�� 
		RevoluteJointDef rjd = new RevoluteJointDef();
		//��ʼ����ת�ؽ�����
		rjd.initialize(body1, body2, body1.getWorldCenter());
		//�Ƿ�̶�
		rjd.enableLimit = enableLimit;	
		rjd.lowerAngle = (float) (lowerAngle * Math.PI / 180);
		rjd.upperAngle = (float) (upperAngle * Math.PI / 180);
		//����world����һ����ת�ؽ�
		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);
		return rj ;
	}
	
	/**
	 * ���ֹؽ�
	 * @param body1	���ó��ֹؽڵ�����Body
	 * @param body2 ���ó��ֹؽڵ�����Body
	 * @param joint1 ���ֹؽڰ󶨵�������ת�ؽ�
	 * @param joint2 ���ֹؽڰ󶨵�������ת�ؽ�
	 * @param ratio ��ת�Ƕȱ�(1:2��ʾһ����תһȦ�������ת2Ȧ)
	 * @return
	 */
	public GearJoint createGearJoint(Body body1, Body body2, Joint joint1, Joint joint2, float ratio) {
		//�������ֹؽ�����ʵ��
		GearJointDef gjd = new GearJointDef();
		//���ó��ֹؽڵ�����Body
		gjd.body1 = body1;
		gjd.body2 = body2;
		//���ó��ֹؽڰ󶨵�������ת�ؽ�
		gjd.joint1 = joint1;
		gjd.joint2 = joint2;
		//������ת�Ƕȱ�
		gjd.ratio = ratio; //Ϊ10��ʾһ����ת10��һ����һ��
		//ͨ��world����һ�����ֹؽ�
		GearJoint gj = (GearJoint) world.createJoint(gjd);
		return gj;
	}
	
	
	/**
	 * ���ֹؽ�(���������� һ������ê����л���)
	 * @param body1 
	 * @param body2
	 * @param anchor1x ���1 x����(����body1�ĵ�����)
	 * @param anchor1y ���1 y����
	 * @param anchor2x ���2 x����(����body2�ĵ�����)
	 * @param anchor2y ���2 y����
	 * @param ratio(1f) ����ϵ��
	 * @return
	 */
	public PulleyJoint createPulleyJointDef(Body body1, Body body2, float anchor1x, float anchor1y, 
			float anchor2x, float anchor2y, float ratio) {
		//�������ֹؽ�����ʵ��
		PulleyJointDef pjd = new PulleyJointDef();
		Vec2 ga1 = new Vec2(anchor1x/ RATE,anchor1y/ RATE);
		Vec2 ga2 = new Vec2(anchor2x/ RATE,anchor2y/ RATE); 
		//��ʼ�����ֹؽ�����
		//body���������ֵ�ê�㣬����body��ê�㣬����ϵ��
		pjd.initialize(body1, body2, ga1, ga2, body1.getWorldCenter(), body2
				.getWorldCenter(), ratio);
		PulleyJoint pj = (PulleyJoint) world.createJoint(pjd);
		return pj;
	}
	
	
	/**
	 * �����ƶ��ؽ�
	 * @param body1	�ƶ��ؽڵ�Body1��world.getGroundBody()����ΪĬ��
	 * @param body2  �ƶ��ؽڵ�Body2
	 * @param anchor�ƶ��ؽڵ����(���Ǹ�������ƶ�)
	 * @param axis  �ƶ��ؽڵ��ƶ�����
	 * @param enableMotor �Ƿ��������
	 * @param maxMotorTorque(1) ����Ԥ�����Ť��(Ť�����ٶȳɷ���)
	 * @param motorSpeed(20)     ���ת�٣�����/�룩
	 * @param enableLimit �Ƿ������ƶ���Χ
	 * @param lowerTranslation λ����Сƫ��ֵ
	 * @param upperTranslation λ�����ƫ��ֵ
	 * @return
	 */
	public PrismaticJoint createPrismaticJointMove(Body body1, Body body2, Vec2 anchor, Vec2 axis,
			boolean enableMotor, float maxMotorForce, float motorSpeed,
			boolean enableLimit, float lowerTranslation, float upperTranslation) {
		//�����ƶ��ؽ�����ʵ��
		PrismaticJointDef pjd = new PrismaticJointDef();
		//Ԥ�����������
		pjd.maxMotorForce = maxMotorForce;
		//����������
		pjd.motorSpeed = motorSpeed;
		//�������
		pjd.enableMotor = enableMotor;
		//����λ����Сƫ��ֵ
		pjd.lowerTranslation = lowerTranslation / RATE;
		//����λ�����ƫ��ֵ
		pjd.upperTranslation = upperTranslation / RATE;
		//��������
		pjd.enableLimit = enableLimit;
		//��ʼ���ƶ��ؽ�����
		pjd.initialize(body1, body2, anchor, axis);
		//ͨ��world����һ���ƶ��ؽ�
		PrismaticJoint pj = (PrismaticJoint) world.createJoint(pjd);
		return pj;
	}
}
