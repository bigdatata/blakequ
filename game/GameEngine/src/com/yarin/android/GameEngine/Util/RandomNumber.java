package com.yarin.android.GameEngine.Util;

import java.util.Random;

//�������
public class RandomNumber
{
	/**
	 * �ж�n�Ƿ���except������
	 * @param n
	 * @param except
	 * @return
	 */
	private static boolean isExcept(int n,int[] except){
		for(int i=0;i<except.length;i++){
			if (n==except[i]){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ���������(0<=��Χ<bound)
	 * @param bound ������ķ�Χ
	 * @param except �����ȡֵ���ų���Χ
	 * @return �����
	 */
	public static int genRandomlyNumber(int bound,int[] except){
		Random ran=new Random();
		int result=ran.nextInt(bound);
		if (except!=null){
			while(isExcept(result,except)){
				result=ran.nextInt(bound);
			}
		}
		return result;
	}
}

