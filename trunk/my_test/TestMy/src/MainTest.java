
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
//		for(int i=0; i<primes.length && primes[i] != null; i++){
//			System.out.print(primes[i]+" ");
//		}
//		bt.useBitInArray();
		System.out.println(1<<15);
		bt.changeBinary((short) 32555);
	}

}
