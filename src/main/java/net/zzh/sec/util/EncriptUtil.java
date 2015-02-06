package net.zzh.sec.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptUtil {
	
	public static String md5(String inputStr){ 
		return encrypte(inputStr, "MD5");
	}
	public static String md2(String inputStr){ 
		return encrypte(inputStr, "MD2");
	}
	
	public static String sha1(String inputStr){ 
		return encrypte(inputStr, "SHA-1");
	}
	public static String sha256(String inputStr){ 
		return encrypte(inputStr, "SHA-256");
	}
	public static String sha512(String inputStr){ 
		return encrypte(inputStr, "SHA-512");
	}

	/**
	 * encrypted password based on JCA algorithm of message digest
	 * 
	 * @param plainText
	 *  orginal password text
	 * @param algorithm
	 *  name of algorithm SHA-1,SHA-256,SHA-512,MD2,MD5
	 * @return encrypted password
	 */ 
	private static String encrypte(String plainText, String algorithm) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(plainText.getBytes());
		byte[] b = md.digest();
		StringBuilder output = new StringBuilder(32);
		for (int i = 0; i < b.length; i++) {
			String temp = Integer.toHexString(b[i] & 0xff);
			if (temp.length() < 2) {
				output.append("0");
			}
			output.append(temp);
		}
		return output.toString();
	}
}
