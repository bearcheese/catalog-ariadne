package hu.bearmaster.phoenix.common.test;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SecureTest {
	public static void main(String[] args) {
		
		SecureRandom randomGenerator = new SecureRandom();
		
		byte[] number = new byte[64];
		
		randomGenerator.nextBytes(number);
		
		BigInteger bi = new BigInteger(number);
		
		String s = bi.toString(2);
		
		while (s.length() % 2 != 0) {
			s = "0" + s;
		}
		
		System.out.println(s);
		
		System.out.println(byteArrayToHexString(number));
		
		System.out.println("size=" + s.length());
		
		/*Object[] tomb = new Object[15];
		
		System.out.println(tomb[5L]);*/
		
		BigInteger MAX_INT = new BigInteger(String.valueOf(Integer.MAX_VALUE));
		
	}
	
	public static boolean kongruens(BigInteger alap, BigInteger modulo, BigInteger ertek) {
		return alap.mod(modulo).compareTo(ertek) == 0;
	}
	
	private static final byte[] HEXBYTES = { (byte) '0', (byte) '1', (byte) '2', (byte) '3',
	      (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a',
	      (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };

	  /**
	   * Converts a byte array into a hexadecimal string
	   * 
	   * 
	   * @param b
	   *          byte array
	   * 
	   * @return hex string
	   */
	  public static String byteArrayToHexString(byte[] b) {

	    int len = b.length;
	    char[] s = new char[len * 2];

	    for (int i = 0, j = 0; i < len; i++) {
	      int c = ((int) b[i]) & 0xff;

	      s[j++] = (char) HEXBYTES[c >> 4 & 0xf];
	      s[j++] = (char) HEXBYTES[c & 0xf];
	    }

	    return new String(s);
	  }
}
