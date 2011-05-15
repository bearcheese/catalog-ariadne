package hu.bearmaster.phoenix.common.util;

import hu.bearmaster.phoenix.common.util.Constants.Unit;

public class Size { 
	
	private final long byteSize; 
	private double dynamicSize;
	private String dynamicSuffix;
	
	public Size(final long byteSize) {
		this.byteSize = byteSize;
		init();
	}
	
	private void init(){
		Unit prev = null;
		for (Unit u : Unit.values()) {
			if ( u == Unit.BYTE) {
				prev = Unit.BYTE;
				continue;
			}
			
			if (byteSize < u.divider()) {
				dynamicSize = getSize(prev);
				dynamicSuffix = prev.suffix();
				return;
			}
			prev = u;
		}
	}
	
	public double getDynamicSize(){
		return dynamicSize;
	}
	
	public String getDynamicSuffix() {
		return dynamicSuffix;
	}
	
	private double getSize(final Unit unit) {
		if ( unit == null ) {
			return 0;
		}
		else {
			return ((double)byteSize) / unit.divider();
		}
		
	}
	
	public double getByteSize() {
		return getSize(Unit.BYTE);
	}
	
	public double getKBSize() {
		return getSize(Unit.KB);
	}
	
	public double getMBSize() {
		return getSize(Unit.MB);
	}
	
	public double getGBSize() {
		return getSize(Unit.GB);
	}
	
	public double getTBSize() {
		return getSize(Unit.TB);
	}
	
	public double getPBSize() {
		return getSize(Unit.PB);
	}
	
}
