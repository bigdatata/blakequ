package com.hao;

/**
 * ����ʵ�ֵ�Ҳ��һ���򵥵Ĺ۲���ģʽ
 * ����Boss�Ǳ��۲�Ķ���Work�����ǹ۲���
 * ��Boss->come()��ʱ�򣬹����ǾͿ�ʼ����
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
