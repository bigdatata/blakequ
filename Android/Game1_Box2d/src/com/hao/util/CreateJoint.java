package com.hao.util;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.GearJoint;
import org.jbox2d.dynamics.joints.GearJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 * ���������ؽ�
 * @author Administrator
 *
 */
public class CreateJoint {

	private World world;
	
	public CreateJoint(World world){
		this.world = world;
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
	 * @param lowerAngle ��ת��С�Ƕ�
	 * @param upperAngle ��ת���Ƕ�
	 * @return
	 */
	public RevoluteJoint createRevoluteJoint2(Body body1, Body body2, float lowerAngle, float upperAngle) {
		//����һ����ת�ؽڵ�����ʵ�� 
		RevoluteJointDef rjd = new RevoluteJointDef();
		//��ʼ����ת�ؽ�����
		rjd.initialize(body1, body2, body1.getWorldCenter());
		//�Ƿ�̶�
		rjd.enableLimit = true;	
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
}
