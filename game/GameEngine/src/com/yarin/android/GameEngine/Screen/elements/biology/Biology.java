package com.yarin.android.GameEngine.Screen.elements.biology;
//����ӿڣ������˾����������Ķ�/ֲ����Ϊ
public interface Biology
{
	//���������
	public static final int MAXLIFE=100;
	//��С������
	public static final int MINLIFE=1;
	/**
	 * �����ܵ��˺����������ؼ������ǵ����������������ֵС��MINLIFE�������������������
	 * @param detaLife ������������
	 * @return �������ǵ�������
	 */
	public int decreaseLife(int detaLife);
	/**
	 * ����ʹ����Ʒ������ԭ�����ǵ�����������
	 * @param detaLife ������������
	 * @return ���ǵ�������
	 */
	public int increaseLife(int detaLife);
}

