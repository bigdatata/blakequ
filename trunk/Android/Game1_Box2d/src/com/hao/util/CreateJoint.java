package com.hao.util;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

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
}
