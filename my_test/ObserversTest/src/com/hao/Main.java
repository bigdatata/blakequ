package com.hao;

/**
 * 这里实现的也是一个简单的观察者模式
 * 这里Boss是被观察的对象，Work工人是观察者
 * 当Boss->come()的时候，工人们就开始工作
 * @author Administrator
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Boss b = new Boss();
		
		Work1 w1 = new Work1();
		Work2 w2 = new Work2();
		
		b.addListener(w2);
		b.addListener(w1);
		b.come();
	}

}
