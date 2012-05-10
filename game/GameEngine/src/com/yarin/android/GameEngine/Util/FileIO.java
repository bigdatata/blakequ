package com.yarin.android.GameEngine.Util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;

//�ļ�IO��
public class FileIO
{
	//GBK���뷽ʽ
	public static final String GBK_CODE="GBK";
	//UTF-8���뷽ʽ
	public static final String UTF8_CODE="UTP-8";
	
	/**
	 * ��ȡ�ı��ļ�
	 * @param originalObject ������Դ���������
	 * @param resURL �ļ�����Դ���е�URL
	 * @param textCodeMethod ���ֱ��뷽ʽ
	 * @return �������ı�
	 */
	public static String readFileText(Object originalObject,String resURL,String textCodeType){
		InputStream is=originalObject.getClass().getResourceAsStream(resURL);
		try{
			DataInputStream dis=new DataInputStream(is);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			int ch;

			while ((ch = dis.read()) != -1)
				baos.write(ch);
			
			dis.close();
			
			return new String(baos.toByteArray(),textCodeType);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

