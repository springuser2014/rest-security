import java.security.SecureRandom;

/**
 * @author zhenhuazhao
 *
 */
public class Salt {

	/**
	 * 
	 */
	public Salt() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SecureRandom random = new SecureRandom();
		byte[] b = random.generateSeed(8);
		System.out.println(random.nextInt(100));
	}

}
