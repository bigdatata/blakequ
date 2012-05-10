package com.yarin.android.GameEngine.Material;
//�߽���������������Ƿ���߽�
public class Measure
{
	//�������ͣ��ھ����м��
	public static final int RECTANGLE_MEASURE=1;
	
	/**
	 * �ж��Ƿ���߽�
	 * @param x �����x����
	 * @param y �����y����
	 * @param border �߽����
	 * @param type ��������
	 * @param superposition �жϱ߽�ʱ�Ƿ������߽��غϵ����
	 * @return �Ƿ���߽�
	 */
	public static boolean isOutOfBorder(int x,int y,Border border,int type,boolean superposition){
		boolean result=false;
		
		switch(type){
		case RECTANGLE_MEASURE:
			//���������߽��غϵ����
			if (superposition){
				if ((x>border.getMaxX())||
						(x<border.getMinX())||
						(y>border.getMaxY())||
						(y<border.getMinY())){
					result=true;
				}
				else{
					result=false;
				}
			}
			else{
				if ((x>=border.getMaxX())||
					(x<=border.getMinX())||
					(y>=border.getMaxY())||
					(y<=border.getMinY())){
					result=true;
				}
				else{
					result=false;
				}
			}
			break;
		}
		return result;
	}
}

