/**
 * 位操作的应用
 * @author Administrator
 *
 */
public class BitTest {
	/**
	 * 符号位取反
	 * @param value
	 * @return
	 */
	public int signReverse(int value){
		return ~value+1;
	}
	
	/**
	 * 求绝对值
	 * @param value
	 * @return
	 */
	public int abs(int value){
		int i = value>>31;//结果为0或者-1，与0异或不变，与-1异或是取反
		//return i==0?value:(~value+1);//这个用了判断，下面简化
		return (i^value)-i;//如果为正数则i=0,负数是i=-1
	}
	
	//-----------------位操作与空间压缩------------------
	/**
	 * 筛素数法
	 * 获取从0-end的所有素数
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
				//对每个素数，它的倍数不是素数
				for(j=i; j<end; j+=i){
					flag[j] = true;
				}
			}
		}
		return primes;
	}
	
	/**
	 * 使用位操作压缩后的筛素数方法,可以极大的减少筛选法使用的附属空间
	 * @param end
	 * @return
	 */
	public Integer[] getPrimeByBit(int end){
		Integer[] primes = new Integer[end/3];
		//Boolean[] flag = new Boolean[end];
		//这里用数组来代替存储boolean
		int length = end/32+1;//因为int占用4字节=32bit，故而只需要end/32+1个数组长度就可以(************+1不能少)
		Integer[] flag = new Integer[length];
		int i, j;
		int pi = 0;
		for(i =0; i<length; i++) flag[i] = 0;
		for(i=2 ; i<end; i++){
			//判断第i位是否为1(先将第i位移动到0位置，然后和1求与就可判断是否为1),*******注意是">>"
			if(((flag[i/32]>>(i%32)) & 1) == 0){
//				System.out.println("-----:"+i);
				primes[pi++]=i;
				//对每个素数，它的倍数不是素数
				for(j=i; j<end; j+=i){
					flag[j/32] |= 1<<(j%32);//将第j位置为1,*******注意是"<<"
				}
			}
		}
		return primes;
	}
	
	/**
	 * 对筛选法的进一步改进，减少重复遍历的次数
	 * @param end
	 * @return
	 */
	public Integer[] getPrimerMore(int end){
		Integer[] primes = new Integer[end/3];
		//Boolean[] flag = new Boolean[end];
		//这里用数组来代替存储boolean
		int length = end/32+1;//因为int占用4字节=32bit，故而只需要end/32+1个数组长度就可以(************+1不能少)
		Integer[] flag = new Integer[length];
		int i, j;
		int pi = 0;
		for(i =0; i<length; i++) flag[i] = 0;
		for(i=2 ; i<end; i++){
			//判断第i位是否为1(先将第i位移动到0位置，然后和1求与就可判断是否为1),*******注意是">>"
			if(((flag[i/32]>>(i%32)) & 1) == 0){
//				System.out.println("-----:"+i);
				primes[pi++]=i;
				//对每个素数，它的倍数不是素数
				for(j=i; j<end; j+=i){
					flag[j/32] |= 1<<(j%32);//将第j位置为1,*******注意是"<<"
				}
			}
		}
		return primes;
	}
	
	/**
	 * 数组中使用位操作
	 * 数组在内存上也是连续分配的一段空间，完全可以“认为”是一个很长的整数
	 */
	public void useBitInArray(){
		System.out.println("------------指定位置设置1----------------");
		Integer[] a = new Integer[5];
		int i=0;
		for(; i<5; i++) a[i] = 0;
		//在第i个位置上写1,这里只是取了40个bit位（共32*5个），只改变了a[0]和a[1]
		for(i=0; i<40; i+=3){
			a[i/32] |= (1<<(i%32));
		}
		System.out.println("a[0]:"+Integer.toBinaryString(a[0]));
		System.out.println("a[1]:"+Integer.toBinaryString(a[1]));
		System.out.println("a[2]:"+Integer.toBinaryString(a[2]));
		//输出整个bitset  
	    for (i = 0; i < 40; i++)  
	    {  
	    	//一次判断i%32位是否为1
	        if (((a[i/32]>>(i%32)) & 1) != 0)  
	            System.out.print("1");  
	        else   
	            System.out.print("0"); 
	    }  
	    System.out.println();
	}
	
	/**
	 * 高低位交换
	 */
	public void changeBinary(short i){
		System.out.println("i:"+i+", binary:"+Integer.toBinaryString(i));
		i = (short) ((i>>8) | (i<<8));
		System.out.println("i:"+i+", binary:"+Integer.toBinaryString(i));
	}
	
	/**
	 * 二进制逆序
	 */
	public void reverseBinary(short a){
		//如a=34520;
		//第一步：每2位为一组，组内高低位交换:10 00 01 10  11 01 10 00-->01 00 10 01 11 10 01 00
		a = (short) (((a & 0xAAAA)>>1) | ((a & 0x5555)<<1));
		//第二步：每4位为一组，组内高低位（高2位与低2位）交换:0100 1001 1110 0100-->0001 0110 1011 0001
		a = (short) (((a & 0xCCCC)>>2) | ((a & 0x3333)<<2));
		//第三步：每8位为一组，组内高低位（高4位与低4位）交换:00010110 10110001-->01100001 00011011
		a = (short) (((a & 0xF0F0)>>4) | ((a & 0x0F0F)<<4));
		//第四步：每16位为一组，组内高低位（高8位与低8位）交换:01100001 00011011-->00011011 01100001
	    a = (short) (((a & 0xFF00)>>8) | ((a & 0x00FF)<<8));
	}
	
	/**
	 * using for test
	 */
	public void test(){
		//在一个数指定位上置1
		int j=0;
		System.out.println(j|1<<10);
		//判断在指定位置上是否为1
		int k=1<<10;
		if((k & (1<<10)) != 0){
			System.out.println("指定位置是1");
		}else{
			System.out.println("指定位置是0");
		}
	}
}
