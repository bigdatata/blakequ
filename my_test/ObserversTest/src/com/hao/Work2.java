package com.hao;

public class Work2 implements MyListener {

	@Override
	public void hasChanged(Object arg) {
		// TODO Auto-generated method stub
		System.out.println("老板来了工人2开始工作了！："+arg);
	}

}
