package hu.bearmaster.phoenix.common.test;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Generalas {
	
	private static final BigInteger MINUS_ONE = new BigInteger("-1");

	public BigInteger general(){
		SecureRandom randomGenerator = new SecureRandom();
		byte[] number = new byte[64];
		randomGenerator.nextBytes(number);
		BigInteger bi = new BigInteger(number);
		//System.out.println("Gen elott: " + bi.toString(16));
		//System.out.println("Signum elott: " + bi.signum());
		if (bi.compareTo(BigInteger.ZERO) < 0){
			bi = bi.multiply(MINUS_ONE);
		}
		//System.out.println("Gen utan: " + bi.toString(16));
		//System.out.println("Signum utan: " + bi.signum());
	    
	    return bi;
	}
	
	/*private static boolean kongruens(BigInteger alap, BigInteger modulo, BigInteger ertek) {
		return alap.mod(modulo).compareTo(ertek) == 0;
		}*/

	private BigInteger d;
    
    public boolean MillerRabin(BigInteger n){  // n prím-e?
    	
    	BigInteger two = new BigInteger("2");
    	BigInteger n_minus_one = n.subtract(BigInteger.ONE); //n = n-1
    	int s;
    	
    	//n-1 = d*2^s;
    	
    	for(s = 1; ; s++){
    		d = n_minus_one.divide(two.pow(s));
    		if ((d.mod(two)).compareTo(BigInteger.ONE) == 0)
    			break;
    	}
    	//System.out.println("d =" + d + "\ns =" + s);
    	
    	BigInteger a;
    	do {
    		a = general();
    		} while (!(a.compareTo(BigInteger.ONE) >= 0 && a.compareTo(n) < 0));
    	BigInteger b = a.modPow(d, n);
    	
    	if (b.compareTo(BigInteger.ONE) == 0)
			return true;
    	for (int i=0; i<s-1; i++){
    		if(b.compareTo(n_minus_one) == 0){
    			return true;
    		}
    		else b = (b.multiply(b)).mod(n);	
    	}
    	return false;
    }
    
    public static void main(String[] args){
    	
    	/*boolean prim_e = false;
    	boolean bi_prim_e = false;
    	Generalas generator = new Generalas();
    	long i = 1;
    	BigInteger prim;
    	do {
			
			prim = generator.general();
			prim_e = generator.MillerRabin(prim);
			System.out.println(i++);
			System.out.println("MR: " + prim_e);
			bi_prim_e = prim.isProbablePrime(1);
			System.out.println("BI1: " + bi_prim_e);
			System.out.println("BI2: " + prim.isProbablePrime(2));
			System.out.println();
		} while (!(prim_e || bi_prim_e));
    	
    	System.out.println(prim);
    	
    	System.out.println("1: " + generator.MillerRabin(prim));
    	System.out.println("2: " + generator.MillerRabin(prim));
    	System.out.println("3: " + generator.MillerRabin(prim));*/
    	
    	//BigInteger biztos_prim = new BigInteger("13362365416977335965756615288352107153040852791479101008196334279632659156056300629485648586093002962312678028159604042255434574369804570639195245847261281");
    	BigInteger biztos_prim = new BigInteger("481253537");
    	System.out.println("Biztos_prim: " + new Generalas().MillerRabin(biztos_prim));
    }

}
