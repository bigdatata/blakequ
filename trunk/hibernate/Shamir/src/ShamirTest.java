
public class ShamirTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double x[] = {1,2,3};
		double y[] = {17,34,61};
		Shamir sh = new Shamir(x, y);
		System.out.println(sh.lagrange(10));
		System.out.println(sh.getSecret());
	}

}
