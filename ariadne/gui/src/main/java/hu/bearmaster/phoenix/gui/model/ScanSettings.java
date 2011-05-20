package hu.bearmaster.phoenix.gui.model;

import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.common.model.Type;
import hu.bearmaster.phoenix.gui.components.forms.ScanDiscForm;

/**
 * A model class for {@link ScanDiscForm}
 * @author "Zoltan Molnar"
 *
 */
public class ScanSettings {

	private String scanRoot;
	
	private String discName;
	
	private Category discCategory; /**using {@link Category}} type so the {@link ScanDiscForm}} can use the proper binder*/ 
	
	private Type discType; /**using {@link Type}} type so the {@link ScanDiscForm}} can use the proper binder*/
	
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

	public Category getDiscCategory() {
		return discCategory;
	}

	public void setDiscCategory(String discCategory) {
		this.discCategory = new Category(discCategory, "scansettings dummy");
	}

	public Type getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = new Type(discType);
	}

	public ScanResult getScanResult() {
		return scanResult;
	}

	public void setScanResult(ScanResult scanResult) {
		this.scanResult = scanResult;
	}	
}
