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
 * 创建常见关节
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
	 * 可以设置自旋转，可以设置world.getGroundBody()，让自己绕自己旋转
	 * initialize(world.getGroundBody(), body1, body1.getWorldCenter());
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
	 * @param enableLimit 是否固定
	 * @param lowerAngle 旋转最小角度
	 * @param upperAngle 旋转最大角度
	 * @return
	 */
	public RevoluteJoint createRevoluteJoint2(Body body1, Body body2,
			boolean enableLimit, float lowerAngle, float upperAngle) {
		//创建一个旋转关节的数据实例 
		RevoluteJointDef rjd = new RevoluteJointDef();
		//初始化旋转关节数据
		rjd.initialize(body1, body2, body1.getWorldCenter());
		//是否固定
		rjd.enableLimit = enableLimit;	
		rjd.lowerAngle = (float) (lowerAngle * Math.PI / 180);
		rjd.upperAngle = (float) (upperAngle * Math.PI / 180);
		//利用world创建一个旋转关节
		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);
		return rj ;
	}
	
	/**
	 * 齿轮关节
	 * @param body1	设置齿轮关节的两个Body
	 * @param body2 设置齿轮关节的两个Body
	 * @param joint1 齿轮关节绑定的两个旋转关节
	 * @param joint2 齿轮关节绑定的两个旋转关节
	 * @param ratio 旋转角度比(1:2表示一个旋转一圈另外的旋转2圈)
	 * @return
	 */
	public GearJoint createGearJoint(Body body1, Body body2, Joint joint1, Joint joint2, float ratio) {
		//创建齿轮关节数据实例
		GearJointDef gjd = new GearJointDef();
		//设置齿轮关节的两个Body
		gjd.body1 = body1;
		gjd.body2 = body2;
		//设置齿轮关节绑定的两个旋转关节
		gjd.joint1 = joint1;
		gjd.joint2 = joint2;
		//设置旋转角度比
		gjd.ratio = ratio; //为10表示一个旋转10次一个才一次
		//通过world创建一个齿轮关节
		GearJoint gj = (GearJoint) world.createJoint(gjd);
		return gj;
	}
	
	
	/**
	 * 滑轮关节(让物体沿着 一个世界锚点进行滑轮)
	 * @param body1 
	 * @param body2
	 * @param anchor1x 描点1 x坐标(连接body1的点坐标)
	 * @param anchor1y 描点1 y坐标
	 * @param anchor2x 描点2 x坐标(连接body2的点坐标)
	 * @param anchor2y 描点2 y坐标
	 * @param ratio(1f) 比例系数
	 * @return
	 */
	public PulleyJoint createPulleyJointDef(Body body1, Body body2, float anchor1x, float anchor1y, 
			float anchor2x, float anchor2y, float ratio) {
		//创建滑轮关节数据实例
		PulleyJointDef pjd = new PulleyJointDef();
		Vec2 ga1 = new Vec2(anchor1x/ RATE,anchor1y/ RATE);
		Vec2 ga2 = new Vec2(anchor2x/ RATE,anchor2y/ RATE); 
		//初始化滑轮关节数据
		//body，两个滑轮的锚点，两个body的锚点，比例系数
		pjd.initialize(body1, body2, ga1, ga2, body1.getWorldCenter(), body2
				.getWorldCenter(), ratio);
		PulleyJoint pj = (PulleyJoint) world.createJoint(pjd);
		return pj;
	}
	
	
	/**
	 * 创建移动关节
	 * @param body1	移动关节的Body1：world.getGroundBody()可作为默认
	 * @param body2  移动关节的Body2
	 * @param anchor移动关节的描点(以那个点进行移动)
	 * @param axis  移动关节的移动方向
	 * @param enableMotor 是否启动马达
	 * @param maxMotorTorque(1) 马达的预期最大扭矩(扭矩与速度成反比)
	 * @param motorSpeed(20)     马达转速（弧度/秒）
	 * @param enableLimit 是否限制移动范围
	 * @param lowerTranslation 位移最小偏移值
	 * @param upperTranslation 位移最大偏移值
	 * @return
	 */
	public PrismaticJoint createPrismaticJointMove(Body body1, Body body2, Vec2 anchor, Vec2 axis,
			boolean enableMotor, float maxMotorForce, float motorSpeed,
			boolean enableLimit, float lowerTranslation, float upperTranslation) {
		//创建移动关节数据实例
		PrismaticJointDef pjd = new PrismaticJointDef();
		//预设马达的最大力
		pjd.maxMotorForce = maxMotorForce;
		//马达的最终力
		pjd.motorSpeed = motorSpeed;
		//启动马达
		pjd.enableMotor = enableMotor;
		//设置位移最小偏移值
		pjd.lowerTranslation = lowerTranslation / RATE;
		//设置位移最大偏移值
		pjd.upperTranslation = upperTranslation / RATE;
		//启动限制
		pjd.enableLimit = enableLimit;
		//初始化移动关节数据
		pjd.initialize(body1, body2, anchor, axis);
		//通过world创建一个移动关节
		PrismaticJoint pj = (PrismaticJoint) world.createJoint(pjd);
		return pj;
	}
}
