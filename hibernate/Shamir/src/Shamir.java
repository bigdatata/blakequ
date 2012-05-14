
/**
 * ʵ��Shamir�㷨
 * @author quhao
 *
 */
public class Shamir {
	double x[];
	double y[];
	int n;
	/**
	 * @param x the array of x
	 * @param y the array of y
	 * @param n the number of array@param n
	 */
	public Shamir(double x[], double y[]){
		this.x = x;
		this.y = y;
		n = x.length;
	}
	
	/**
	 * ���ڼ����������ղ�ֵ
	 * @param testValue ���Ե�xֵ
	 * @return
	 */
	public double lagrange(int testValue){
		double result = 0;
		double temp[] = new double[n];
		for(int i=0; i<n; i++){
			temp[i] = y[i];
			for(int j=0 ; j<n; j++){
				if(i != j){
					temp[i] *= (testValue - x[j])/(x[i] - x[j]);
				}
			}
			result += temp[i];
		}
		return result;	
	}
	
	/**
	 * return the shamir secret
	 * @return
	 */
	public double getSecret(){
		return lagrange(0);
	}
}
