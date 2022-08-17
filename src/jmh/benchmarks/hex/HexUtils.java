package jmh.benchmarks.hex;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class HexUtils {

	private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (byte value : b) {
			resultSb.append(byteToHexString(value));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0){
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	public static String md5Hex(String origin, Charset charsetName) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetName == null) {
				resultString = byteArrayToHexString(md.digest(origin.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(origin.getBytes(charsetName)));
			}
		} catch (Exception ex) {

		}
		return resultString;
	}

}
