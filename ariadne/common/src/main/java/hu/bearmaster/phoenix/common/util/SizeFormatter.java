package hu.bearmaster.phoenix.common.util;

/**
 * Helper class for converting file sizes in byte dynamically to the highest unit, where
 * the size is still greater or equals with 1.
 * @author mzx
 * 
 */

public final class SizeFormatter {
	
	private SizeFormatter() {}
	
	/**
	 * Converts the file size into a string which contains the highest possible value 
	 * with the proper unit suffix. The value will be a double number with precision 2.
	 * @param s size in bytes
	 * @return string
	 */
	public static String getDoubleDynamicSuffix(final long byteSize){
		Size size = new Size(byteSize);
		return String.format("%.2f %s", size.getDynamicSize(), size.getDynamicSuffix());
	}
	
	/**
	 * Converts the file size into a string which contains the highest possible value 
	 * with the proper unit suffix. The value will be rounded to the nearest integer.
	 * @param s size in bytes
	 * @return string
	 */
	public static String getDynamicSuffix(final long byteSize){
		Size size = new Size(byteSize);
		return String.format("%d %s", Math.round(size.getDynamicSize()), size.getDynamicSuffix());		
	}

}
