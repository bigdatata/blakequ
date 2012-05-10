package com.yarin.android.GameEngine.Util;

import java.util.Vector;

//�ַ�����չ�� 
public class StringExtension 
{
	//�س��ָ��
	public static final String ENTER_SEPARATOR="\r\n";
	//�ָ����������-�ָ�Ϊ�ַ�������
	public static final int STRING_ARRAY=0;
	//�ָ����������-�ָ�Ϊ��������
	public static final int INTEGER_ARRAY=1;
	
	/**
	 * ʹ�÷ָ��ַ����������ַ���
	 * @param strbf ��������ַ���Buffer
	 * @param separator �ָ��ַ���
	 * @param resultType ���ؽ�������ͣ��ַ������顢�������飩
	 * @param drop �Ƿ��������ʣ����ַ���
	 * @return �ָ����ַ�����������
	 */
	public static Object[] split(StringBuffer strbf,String separator,int resultType,boolean drop){
		
		int pos=0;
		Vector tmp=null;
		
		pos=strbf.toString().indexOf(separator);
		tmp=new Vector();
		
		while(pos>0){
			switch(resultType){
			case STRING_ARRAY:
				tmp.addElement(strbf.toString().substring(0,pos));
				break;
			case INTEGER_ARRAY:
				tmp.addElement(new Integer(Integer.parseInt(strbf.toString().substring(0,pos))));
				break;
			}
			
			strbf.delete(0,pos+separator.length());
			pos=strbf.toString().indexOf(separator);
		}
		
		//����������µ��ַ������������뵽���ؽ���С�
		if (!drop){
			if (strbf.length()>0){
				switch(resultType){
				case STRING_ARRAY:
					tmp.addElement(strbf.toString());
					break;
				case INTEGER_ARRAY:
					tmp.addElement(new Integer(Integer.parseInt(strbf.toString())));
					break;
				}
			}
		}
		Object[] result=new Object[tmp.size()];
		tmp.copyInto(result);
		return result;
	}
	
	/**
	 * �����������е�ԭ��ΪString�Ķ���ת��ΪString����
	 * @param objArray ��������
	 * @return ת������ַ�������
	 */
	public static String[] objectArrayBatchToStringArray(Object[] objArray){
		String[] result=new String[objArray.length];
		for(int i=0;i<objArray.length;i++){
			result[i]=(String)objArray[i];
		}
		return result;
	}
	
	/**
	 * �����������е�ԭ��ΪInteger�Ķ���ת��Ϊint����
	 * @param objArray ��������
	 * @return ת�����int����
	 */
	public static int[] objectArrayBatchToIntArray(Object[] objArray){
		int[] result=new int[objArray.length];
		for(int i=0;i<objArray.length;i++){
			Integer t=(Integer)objArray[i];
			result[i]=t.intValue();
		}
		return result;
	}
	
	/**
	 * �����ַ�����ĳ�����ַ������ֵĴ���
	 * @param str �ַ���
	 * @param token ���ַ���
	 * @return ���ַ������ֵĴ���
	 */
	public static int getTokenCount(String str,String token){
		int count=0;
		int beginPos=0;
		int pos=0;
		while ((pos=str.indexOf(token,beginPos))>=0){
			count++;
			beginPos=pos+token.length();
		}
		return count;
	}
	
	/**
	 * �г��ַ����е�token���ַ���
	 * @param content �ַ���
	 * @param cutToken Ҫ���г������ַ���
	 * @return �г�����ַ���
	 */
	public static String removeToken(String content,String cutToken){
		StringBuffer s=new StringBuffer(content);
		int pos=0;
		
		while((pos=s.toString().indexOf(cutToken))>=0){
			s.delete(pos,pos+cutToken.length());
		}
		return s.toString();
	}
	
	/**
	 * �г��ַ����е�token���ַ���
	 * @param content �ַ���
	 * @param cutToken ��Ҫ���г������ַ�����ɵ�����
	 * @return �г�����ַ���
	 */
	public static String removeToken(String content,String[] cutToken){
		StringBuffer s=new StringBuffer(content);
		int pos=0;
		
		for(int i=0;i<cutToken.length;i++){
			while((pos=s.toString().indexOf(cutToken[i]))>=0){
				s.delete(pos,pos+cutToken[i].length());
			}
		}
		return s.toString();
	}
	
	/**
	 * �滻�ַ����е�ĳ�����ַ���
	 * @param content �ַ���
	 * @param replacedToken ���滻�����ַ���
	 * @param replaceStr �滻������ַ���
	 * @return �滻��ɹ����ַ���
	 */
	public static String replaceToken(String content,String replacedToken,String replaceStr){
		StringBuffer s=new StringBuffer(content);
		int pos=0;
		
		while((pos=s.toString().indexOf(replacedToken))>=0){
			s.delete(pos,pos+replacedToken.length());
			s.insert(pos,replaceStr);
		}
		return s.toString();
	}
}

