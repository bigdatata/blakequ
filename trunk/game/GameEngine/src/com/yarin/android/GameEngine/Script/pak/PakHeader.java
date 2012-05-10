package com.yarin.android.GameEngine.Script.pak;
/**
 * Pak�ļ�ͷ��
 * �ṹ��
 *   ǩ����6�ֽ�char����
 *   �汾�ţ�32λfloat
 *   �ļ�table������32λ����
 *   ������Ϊ��8λ�ֽ�
 *   ���룺8λ�ֽ�
 *   �ļ�ΨһID��10�ֽ�char����
 *   ����λ��32λ����(4�ֽ�)
 *
 */
public class PakHeader
{
	//�����ļ�ΨһID����
	public static final int UNIQUEID_LENGTH=10;
	//�����ļ�ǩ������
	public static final int SIGNATURE_LENGTH=6;
	//����ӷ�����
	public static final int ADDITION_CIPHERACTION=0;
	//�����������
	public static final int SUBTRACT_CIHOERACTION=1;
	//�ļ�ǩ��
	private char[] signature=new char[SIGNATURE_LENGTH];
	//�汾��
	private float version=0f;
	//�ļ�table����
	private long numFileTableEntries=0;
	//����ʹ�÷�������ԭ�����Ͻ��мӷ����Ǽ���
	private byte cipherAction=ADDITION_CIPHERACTION;
	//����ֵ
	private byte cipherValue=0x00;
	//ΨһID
	private char[] uniqueID=new char[UNIQUEID_LENGTH];
	//������4�ֽ�
	private long reserved=0;
	
	public PakHeader(){	
	}
	
	/**
	 * ���췽��
	 * @param signature ǩ��
	 * @param version �汾
	 * @param numFileTableEntries �ļ�table����
	 * @param cipherAction ����ʹ�÷���
	 * @param cipherValue ����ֵ
	 * @param uniqueID ΨһID
	 * @param reserved ������2�ֽ�
	 */
	public PakHeader(char[] signature,float version,
			long numFileTableEntries,byte cipherAction,
			byte cipherValue,char[] uniqueID,long reserved){
		for(int i=0;i<SIGNATURE_LENGTH;this.signature[i]=signature[i],i++)
			;
		this.version=version;
		this.cipherAction=cipherAction;
		this.numFileTableEntries=numFileTableEntries;
		this.cipherValue=cipherValue;
		for(int i=0;i<UNIQUEID_LENGTH;this.uniqueID[i]=uniqueID[i],i++)
			;
		
		this.reserved=reserved;
	}
	
	public byte getCipherValue() {
		return cipherValue;
	}
	public void setCipherValue(byte cipherValue) {
		this.cipherValue = cipherValue;
	}
	public long getNumFileTableEntries() {
		return numFileTableEntries;
	}
	public void setNumFileTableEntries(long numFileTableEntries) {
		this.numFileTableEntries = numFileTableEntries;
	}
	public long getReserved() {
		return reserved;
	}
	public void setReserved(long reserved) {
		this.reserved = reserved;
	}
	public char[] getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(char[] uniqueID) {
		for(int i=0;i<UNIQUEID_LENGTH;this.uniqueID[i]=uniqueID[i],i++)
			;
	}
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}
	public byte getCipherAction() {
		return cipherAction;
	}

	public void setCipherAction(byte cipherAction) {
		this.cipherAction = cipherAction;
	}

	public char[] getSignature() {
		return signature;
	}

	public void setSignature(char[] signature) {
		for(int i=0;i<SIGNATURE_LENGTH;this.signature[i] = signature[i],i++)
			;
	}
	
	/**
	 * ����PakHeader�Ĵ�С
	 * @return ����PakHeader�Ĵ�С
	 */
	public static int size(){
		return SIGNATURE_LENGTH+4+4+1+1+UNIQUEID_LENGTH+4;
	}
	
	public String toString(){
		String result="";
		result+="\tǩ��:"+new String(this.signature).trim()
			+"\t�汾��:"+this.version
			+"\t�ļ�table����:"+this.numFileTableEntries
			+"\t������Ϊ:" +this.cipherAction
			+"\t����:"+this.cipherValue
			+"\t�ļ�ΨһID:"+new String(this.uniqueID).trim()
			+"\t����λ:"+this.reserved;
		return result;
	}
}

