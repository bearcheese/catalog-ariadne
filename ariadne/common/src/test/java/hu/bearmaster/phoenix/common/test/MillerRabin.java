package hu.bearmaster.phoenix.common.test;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {
	
	private static final Random rnd = new Random();

	private static boolean miller_rabin_pass(BigInteger a, BigInteger n) {
	    BigInteger n_minus_one = n.subtract(BigInteger.ONE);
	    
	    BigInteger d = n_minus_one;
	    int s = d.getLowestSetBit();
	    d = d.shiftRight(s);
	    
	    BigInteger a_to_power = a.modPow(d, n);
	    if (a_to_power.equals(BigInteger.ONE)) return true;
	    for (int i = 0; i < s-1; i++) {
	        if (a_to_power.equals(n_minus_one)) return true;
	        a_to_power = a_to_power.multiply(a_to_power).mod(n);
	    }
	    if (a_to_power.equals(n_minus_one)) return true;
	    return false;
	}

    public static boolean miller_rabin(BigInteger n) {
        for (int repeat = 0; repeat < 20; repeat++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rnd);
            } while (a.equals(BigInteger.ZERO));
            if (!miller_rabin_pass(a, n)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
    	
    	long step = 0;
    	long kezd = System.currentTimeMillis();
    	
        if (args[0].equals("test")) {
            BigInteger n = new BigInteger(args[1]);
            System.out.println(miller_rabin(n) ? "PRIME" : "COMPOSITE");
        } else if (args[0].equals("genprime")) {
            int nbits = Integer.parseInt(args[1]);
            BigInteger p;
            do {
                p = new BigInteger(nbits, rnd);
                if (p.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) continue;
                if (p.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)) continue;
                if (p.mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO)) continue;
                if (p.mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO)) continue;
                step++;
            } while (!miller_rabin(p));
            long veg = System.currentTimeMillis();
            System.out.println(p);
            System.out.println("Steps: " + step);
            System.out.println("Time: " + (veg - kezd) + " ms");
        }
    }

}
