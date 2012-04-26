package com.hao.util;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

/**
 * 创建常见关节
 * @author Administrator
 *
 */
public class CreateJoint {

	private World world;
	
	public CreateJoint(World world){
		this.world = world;
	}
	
	/**
	 * 创建距离关节
	 * @return
	 */
	public DistanceJoint createDistanceJoint(Body body1, Body body2) {
		//创建一个距离关节数据实例
		DistanceJointDef dje = new DistanceJointDef();
		// 初始化关节数据
		dje.initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter());
		// dje.collideConnected=true;
		//利用世界通过传入的距离关节数据创建一个关节
		DistanceJoint dj = (DistanceJoint) world.createJoint(dje);
		return dj;
	}
}
