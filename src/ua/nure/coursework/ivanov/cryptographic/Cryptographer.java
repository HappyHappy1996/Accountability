package ua.nure.coursework.ivanov.cryptographic;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cryptographer {

	public static String hash(String input, String algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		messageDigest.reset();
//		 messageDigest.update(input.getBytes());
		messageDigest.update(input.getBytes("UTF-16")); // encoding ???
		byte[] hash = messageDigest.digest();

		BigInteger bigInt = new BigInteger(1, hash);
		String md5Hex = bigInt.toString(16);

		while (md5Hex.length() < 32) {
			System.out.println(md5Hex);
			md5Hex = "0" + md5Hex;
		}

		return md5Hex;
	}

}