package com.yarin.android.GameEngine.Script.pak;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;

/**
 * Pak������
 * ���ܣ�
 * ��Pak�ļ���ȡ��pngͼƬ������byte���飨������������Image����
 *
 */
public class PakUtil
{
	public PakUtil(){
	}
	
	/**
	 * �����ļ�λ�Ƶ���ʼ��
	 * @return �ļ�λ�Ƶ���ʼ��
	 */
	private long workOutOffsetStart(PakHeader header){
		//������ļ�ͷ+�ļ�table�ĳ���
		return PakHeader.size()+header.getNumFileTableEntries()*PakFileTable.size();
	}
	
	/**
	 * ��DataInputStream��ȡchar����
	 * @param dis DataInputStream
	 * @param readLength ��ȡ����
	 * @return char����
	 * @throws Exception
	 */
	private char[] readCharArray(DataInputStream dis,int readLength) throws Exception{
		char[] readCharArray=new char[readLength];
		
		for(int i=0;i<readLength;i++){
			readCharArray[i]=dis.readChar();
		}
		return readCharArray;
	}
	
	/**
	 * ��PAK�ļ��ж�ȡ�ļ�ͷ
	 * @param dis DataInputStream
	 * @return PakHeader
	 * @throws Exception
	 */
	private PakHeader readHeader(DataInputStream dis) throws Exception{
		PakHeader header=new PakHeader();
		char[] signature=readCharArray(dis,PakHeader.SIGNATURE_LENGTH);
		header.setSignature(signature);
		header.setVersion(dis.readFloat());
		header.setNumFileTableEntries(dis.readLong());
		header.setCipherAction(dis.readByte());
		header.setCipherValue(dis.readByte());
		char[] uniqueID=readCharArray(dis,PakHeader.UNIQUEID_LENGTH);
		header.setUniqueID(uniqueID);
		header.setReserved(dis.readLong());
		return header;
	}
	
	/**
	 * ��ȡ���е��ļ�table
	 * @param dis DataInputStream
	 * @param fileTableNumber �ļ�������
	 * @return �ļ�table����
	 * @throws Exception
	 */
	private PakFileTable[] readFileTable(DataInputStream dis,int fileTableNumber) throws Exception{
		PakFileTable[] fileTable=new PakFileTable[fileTableNumber];
		for(int i=0;i<fileTableNumber;i++){
			PakFileTable ft=new PakFileTable();
			ft.setFileName(readCharArray(dis,PakFileTable.FILENAME_LENGTH));
			ft.setFileSize(dis.readLong());
			ft.setOffSet(dis.readLong());
			fileTable[i]=ft;
		}
		return fileTable;
	}
	
	/**
	 * ��pak�ļ���ȡ�ļ���byte����
	 * @param dis DataInputStream
	 * @param fileTable PakFileTable
	 * @return byte����
	 * @throws Exception
	 */
	private byte[] readFileFromPak(DataInputStream dis,PakHeader header,PakFileTable fileTable) throws Exception{
		dis.skip(fileTable.getOffSet()-workOutOffsetStart(header));
		//
		int fileLength=(int)fileTable.getFileSize();
		byte[] fileBuff=new byte[fileLength];
		int readLength=dis.read(fileBuff,0,fileLength);
		if (readLength<fileLength){
			System.out.println("��ȡ���ݳ��Ȳ���ȷ");
			return null;
		}
		else{
			decryptBuff(fileBuff,readLength,header);
		}
		return fileBuff;
	}
	
	/**
	 * ʹ���ļ�ͷ�е���������ݽ��н���
	 * @param buff �����ܵ�����
	 * @param buffLength ���ݵĳ���
	 * @param header �ļ�ͷ
	 */
	private void decryptBuff(byte[] buff,int buffLength,PakHeader header){
		for(int i=0;i<buffLength;i++){
			switch(header.getCipherAction()){
			case PakHeader.ADDITION_CIPHERACTION:
				buff[i]-=header.getCipherValue();
				break;
			case PakHeader.SUBTRACT_CIHOERACTION:
				buff[i]+=header.getCipherValue();
				break;
			}
		}
	}
	
	/**
	 * ��pak�ļ���ȡ��ָ�����ļ���byte����
	 * @param pakResourceURL  pak�ļ�����Դ·��
	 * @param extractResourceName pak�ļ��н�Ҫ��ȡ�����ļ���
	 * @return byte����
	 * @throws Exception
	 */
	public byte[] extractResourceFromPak(String pakResourceURL
			,String extractResourceName) throws Exception{
		InputStream is=this.getClass().getResourceAsStream(pakResourceURL);
		DataInputStream dis=new DataInputStream(is);
		PakHeader header=readHeader(dis);
//		System.out.println("�ļ�ͷ:");
//		System.out.println(header);
		PakFileTable[] fileTable=readFileTable(dis,(int)header.getNumFileTableEntries());
//		for(int i=0;i<fileTable.length;i++){
//			System.out.println("�ļ�table["+i+"]:");
//			System.out.println(fileTable[i]);
//		}
		boolean find=false;
		int fileIndex=0;
		for(int i=0;i<fileTable.length;i++){
			String fileName=new String(fileTable[i].getFileName()).trim();
			if (fileName.equals(extractResourceName)){
				find=true;
				fileIndex=i;
				break;
			}
		}
		if (find==false){
			System.out.println("û���ҵ�ָ�����ļ�");
			return null;
		}
		else{
			byte[] buff=readFileFromPak(dis,header,fileTable[fileIndex]);
			return buff;
		}
	}
	
	
	/**
	 * ��pak�ļ���ȡ��ָ����Pak�ļ�����Ϣ
	 * @param pakResourcePath  pak�ļ���Դ·��
	 * @return װ���ļ�ͷ���ļ�table�����Vector
	 * @throws Exception
	 */
	public Vector showPakFileInfo(String pakResourcePath) throws Exception{
		InputStream is=this.getClass().getResourceAsStream(pakResourcePath);
		DataInputStream dis=new DataInputStream(is);
		
		PakHeader header=readHeader(dis);
		PakFileTable[] fileTable=readFileTable(dis,(int)header.getNumFileTableEntries());

		Vector result=new Vector();
		result.addElement(header);
		result.addElement(fileTable);
		return result;
	}
	
	public static void main(String[] argv) throws Exception{
		PakUtil pu=new PakUtil();
		String extractResourcePath="/test.pak";
		//��Pak�ļ���ȡ�����е�ͼƬ�ļ�
		Vector pakInfo=pu.showPakFileInfo(extractResourcePath);
		PakHeader header=(PakHeader)pakInfo.elementAt(0);
		System.out.println("Pak�ļ���Ϣ:");
		System.out.println("�ļ�ͷ:");
		System.out.println(header);
		
		PakFileTable[] fileTable=(PakFileTable[])pakInfo.elementAt(1);
		for(int i=0;i<fileTable.length;i++){
			System.out.println("�ļ�table["+i+"]:");
			System.out.println(fileTable[i]);
		}
		
		String restoreFileName=null;
		byte[] fileBuff=null;
		for(int i=0;i<fileTable.length;i++){
			restoreFileName=new String(fileTable[i].getFileName()).trim();
			System.out.println("��Pak�ļ���ȡ��"+restoreFileName+"�ļ�����...");
			fileBuff=pu.extractResourceFromPak(extractResourcePath,restoreFileName);
			System.out.println("��Pak�ļ���ȡ��"+restoreFileName+"�ļ��������");
		}
	}
}

