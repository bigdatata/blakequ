import java.util.Calendar;


public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//----------------test bit operator---------------------------
		BitTest bt = new BitTest();
		System.out.println(bt.signReverse(-10));
		System.out.println(bt.abs(10)+","+bt.abs(-10));
//		Integer[] primes = bt.getPrime(100);
//		Integer[] primes = bt.getPrimeByBit(100);
//		Integer[] primes = bt.getPrimerMore(100);
//		for(int i=0; i<primes.length && primes[i] != null; i++){
//			System.out.print(primes[i]+" ");
//		}
		System.out.println("在10000000的数据量下普通的筛素数方法与改进之后的效率对比");
		long t1 = System.currentTimeMillis();
		bt.getPrimerMore(10000000);
		long t2 = System.currentTimeMillis();
		System.out.println("-------time:"+(t2-t1)+"ms");
		
		t1 = System.currentTimeMillis();
		bt.getPrimeByBit(10000000);
		t2 = System.currentTimeMillis();
		System.out.println("-------time:"+(t2-t1)+"ms");
//		bt.useBitInArray();
//		System.out.println(1<<15);
//		bt.changeBinary((short) 32555);
	}

}
