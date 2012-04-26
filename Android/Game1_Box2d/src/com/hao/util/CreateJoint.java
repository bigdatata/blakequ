package com.hao.util;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

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
	
	/**
	 * 旋转关节(旋转车轮)
	 * @param body1
	 * @param body2
	 * @param maxMotorTorque(1) 马达的预期最大扭矩(扭矩与速度成反比)
	 * @param motorSpeed(20)     马达转速（弧度/秒）
	 * @return
	 */
	public RevoluteJoint createRevoluteJoint1(Body body1, Body body2, float maxMotorTorque, float motorSpeed) {
		//创建一个旋转关节的数据实例 
		RevoluteJointDef rjd = new RevoluteJointDef();
		//初始化旋转关节数据
		rjd.initialize(body1, body2, body1.getWorldCenter());
		//启动马达,是否可以转动
		rjd.enableMotor = true;
		rjd.maxMotorTorque = maxMotorTorque; 
		rjd.motorSpeed = motorSpeed;
		//利用world创建一个旋转关节
		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);
		return rj ;
	}
	
	/**
	 * 旋转关节(跷跷板)
	 * @param body1
	 * @param body2
	 * @param lowerAngle 旋转最小角度
	 * @param upperAngle 旋转最大角度
	 * @return
	 */
	public RevoluteJoint createRevoluteJoint2(Body body1, Body body2, float lowerAngle, float upperAngle) {
		//创建一个旋转关节的数据实例 
		RevoluteJointDef rjd = new RevoluteJointDef();
		//初始化旋转关节数据
		rjd.initialize(body1, body2, body1.getWorldCenter());
		//是否固定
		rjd.enableLimit = true;	
		rjd.lowerAngle = (float) (lowerAngle * Math.PI / 180);
		rjd.upperAngle = (float) (upperAngle * Math.PI / 180);
		//利用world创建一个旋转关节
		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);
		return rj ;
	}
}
