/**
 * λ������Ӧ��
 * @author Administrator
 *
 */
public class BitTest {
	/**
	 * ����λȡ��
	 * @param value
	 * @return
	 */
	public int signReverse(int value){
		return ~value+1;
	}
	
	/**
	 * �����ֵ
	 * @param value
	 * @return
	 */
	public int abs(int value){
		int i = value>>31;//���Ϊ0����-1����0��򲻱䣬��-1�����ȡ��
		//return i==0?value:(~value+1);//��������жϣ������
		return (i^value)-i;//���Ϊ������i=0,������i=-1
	}
	
	//-----------------λ������ռ�ѹ��------------------
	/**
	 * ɸ������
	 * ��ȡ��0-end����������
	 * @param end
	 */
	public Integer[] getPrime(int end){
		Integer[] primes = new Integer[end/3];
		Boolean[] flag = new Boolean[end];
		int i, j;
		int pi = 0;
		for(i =0; i<end; i++) flag[i] = false;
		for(i=2 ; i<end; i++){
			if(!flag[i]){
				primes[pi++]=i;
				//��ÿ�����������ı�����������
				for(j=i; j<end; j+=i){
					flag[j] = true;
				}
			}
		}
		return primes;
	}
	
	/**
	 * ʹ��λ����ѹ�����ɸ��������,���Լ���ļ���ɸѡ��ʹ�õĸ����ռ�
	 * @param end
	 * @return
	 */
	public Integer[] getPrimeByBit(int end){
		Integer[] primes = new Integer[end/3];
		//Boolean[] flag = new Boolean[end];
		//����������������洢boolean
		int length = end/32+1;//��Ϊintռ��4�ֽ�=32bit���ʶ�ֻ��Ҫend/32+1�����鳤�ȾͿ���(************+1������)
		Integer[] flag = new Integer[length];
		int i, j;
		int pi = 0;
		for(i =0; i<length; i++) flag[i] = 0;
		for(i=2 ; i<end; i++){
			//�жϵ�iλ�Ƿ�Ϊ1(�Ƚ���iλ�ƶ���0λ�ã�Ȼ���1����Ϳ��ж��Ƿ�Ϊ1),*******ע����">>"
			if(((flag[i/32]>>(i%32)) & 1) == 0){
//				System.out.println("-----:"+i);
				primes[pi++]=i;
				//��ÿ�����������ı�����������
				for(j=i; j<end; j+=i){
					flag[j/32] |= 1<<(j%32);//����jλ��Ϊ1,*******ע����"<<"
				}
			}
		}
		return primes;
	}
	
	/**
	 * ��ɸѡ���Ľ�һ���Ľ��������ظ������Ĵ���
	 * @param end
	 * @return
	 */
	public Integer[] getPrimerMore(int end){
		Integer[] primes = new Integer[end/3];
		//Boolean[] flag = new Boolean[end];
		//����������������洢boolean
		int length = end/32+1;//��Ϊintռ��4�ֽ�=32bit���ʶ�ֻ��Ҫend/32+1�����鳤�ȾͿ���(************+1������)
		Integer[] flag = new Integer[length];
		int i, j;
		int pi = 0;
		for(i =0; i<length; i++) flag[i] = 0;
		for(i=2 ; i<end; i++){
			//�жϵ�iλ�Ƿ�Ϊ1(�Ƚ���iλ�ƶ���0λ�ã�Ȼ���1����Ϳ��ж��Ƿ�Ϊ1),*******ע����">>"
			if(((flag[i/32]>>(i%32)) & 1) == 0){
//				System.out.println("-----:"+i);
				primes[pi++]=i;
				//��ÿ�����������ı�����������
				for(j=i; j<end; j+=i){
					flag[j/32] |= 1<<(j%32);//����jλ��Ϊ1,*******ע����"<<"
				}
			}
		}
		return primes;
	}
	
	/**
	 * ������ʹ��λ����
	 * �������ڴ���Ҳ�����������һ�οռ䣬��ȫ���ԡ���Ϊ����һ���ܳ�������
	 */
	public void useBitInArray(){
		System.out.println("------------ָ��λ������1----------------");
		Integer[] a = new Integer[5];
		int i=0;
		for(; i<5; i++) a[i] = 0;
		//�ڵ�i��λ����д1,����ֻ��ȡ��40��bitλ����32*5������ֻ�ı���a[0]��a[1]
		for(i=0; i<40; i+=3){
			a[i/32] |= (1<<(i%32));
		}
		System.out.println("a[0]:"+Integer.toBinaryString(a[0]));
		System.out.println("a[1]:"+Integer.toBinaryString(a[1]));
		System.out.println("a[2]:"+Integer.toBinaryString(a[2]));
		//�������bitset  
	    for (i = 0; i < 40; i++)  
	    {  
	    	//һ���ж�i%32λ�Ƿ�Ϊ1
	        if (((a[i/32]>>(i%32)) & 1) != 0)  
	            System.out.print("1");  
	        else   
	            System.out.print("0"); 
	    }  
	    System.out.println();
	}
	
	/**
	 * �ߵ�λ����
	 */
	public void changeBinary(short i){
		System.out.println("i:"+i+", binary:"+Integer.toBinaryString(i));
		i = (short) ((i>>8) | (i<<8));
		System.out.println("i:"+i+", binary:"+Integer.toBinaryString(i));
	}
	
	/**
	 * ����������
	 */
	public void reverseBinary(short a){
		//��a=34520;
		//��һ����ÿ2λΪһ�飬���ڸߵ�λ����:10 00 01 10  11 01 10 00-->01 00 10 01 11 10 01 00
		a = (short) (((a & 0xAAAA)>>1) | ((a & 0x5555)<<1));
		//�ڶ�����ÿ4λΪһ�飬���ڸߵ�λ����2λ���2λ������:0100 1001 1110 0100-->0001 0110 1011 0001
		a = (short) (((a & 0xCCCC)>>2) | ((a & 0x3333)<<2));
		//��������ÿ8λΪһ�飬���ڸߵ�λ����4λ���4λ������:00010110 10110001-->01100001 00011011
		a = (short) (((a & 0xF0F0)>>4) | ((a & 0x0F0F)<<4));
		//���Ĳ���ÿ16λΪһ�飬���ڸߵ�λ����8λ���8λ������:01100001 00011011-->00011011 01100001
	    a = (short) (((a & 0xFF00)>>8) | ((a & 0x00FF)<<8));
	}
	
	/**
	 * using for test
	 */
	public void test(){
		//��һ����ָ��λ����1
		int j=0;
		System.out.println(j|1<<10);
		//�ж���ָ��λ�����Ƿ�Ϊ1
		int k=1<<10;
		if((k & (1<<10)) != 0){
			System.out.println("ָ��λ����1");
		}else{
			System.out.println("ָ��λ����0");
		}
	}
}
