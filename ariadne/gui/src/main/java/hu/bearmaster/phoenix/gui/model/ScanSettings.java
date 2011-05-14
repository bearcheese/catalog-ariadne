package hu.bearmaster.phoenix.gui.model;

import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.gui.components.dialogs.ScanDiscForm;

/**
 * A model class for {@link ScanDiscForm}
 * @author "Zoltan Molnar"
 *
 */
public class ScanSettings {

	private String scanRoot;
	
	private String discName;
	
	private String discCategory;
	
	private String discType;
	
	private ScanResult scanResult;

	public String getScanRoot() {
		return scanRoot;
	}

	public void setScanRoot(String scanRoot) {
		this.scanRoot = scanRoot;
	}

	public String getDiscName() {
		return discName;
	}

	public void setDiscName(String discName) {
		this.discName = discName;
	}

	public String getDiscCategory() {
		return discCategory;
	}

	public void setDiscCategory(String discCategory) {
		this.discCategory = discCategory;
	}

	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public ScanResult getScanResult() {
		return scanResult;
	}

	public void setScanResult(ScanResult scanResult) {
		this.scanResult = scanResult;
	}	
}
