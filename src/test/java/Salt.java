import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import net.zzh.sec.util.EncriptUtil;
import net.zzh.sec.util.Passwords;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

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
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String salt = KeyGenerators.string().generateKey();
		System.out.println(salt);
		
		StandardPasswordEncoder encoder = new StandardPasswordEncoder("secret");
		String result = encoder.encode("myPassword");
		System.out.println(result);
		System.out.println(encoder.matches("myPassword", result));

		System.out.println(Passwords.generateRandomPassword(6));
		byte[] byteSalt = Passwords.getNextSalt();
		salt = new String(byteSalt, "UTF-8");
		System.out.println(salt);
		salt = new String(Passwords.hash("123456".toCharArray(), byteSalt), "UTF-8");
		System.out.println(salt);
		System.out.println(EncriptUtil.sha512("123456"));
	}

}
