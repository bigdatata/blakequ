package com.hao;

/**
 * 工人1，他要等到老板来了才准备工作，没有老板的通知就不工作
 * 所以它是监听者，监听老板的状态
 * @author Administrator
 *
 */
public class Work1 implements MyListener {

	@Override
	public void hasChanged(Object arg) {
		// TODO Auto-generated method stub
		System.out.println("老板来了工人1开始工作了！："+arg);
	}

}
