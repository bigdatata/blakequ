package com.hao;

/**
 * ����1����Ҫ�ȵ��ϰ����˲�׼��������û���ϰ��֪ͨ�Ͳ�����
 * �������Ǽ����ߣ������ϰ��״̬
 * @author Administrator
 *
 */
public class Work1 implements MyListener {

	@Override
	public void hasChanged(Object arg) {
		// TODO Auto-generated method stub
		System.out.println("�ϰ����˹���1��ʼ�����ˣ���"+arg);
	}

}
