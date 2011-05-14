package hu.bearmaster.phoenix.gui.components.dialogs;

import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.gui.model.ScanSettings;

import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

public class ScanDiscDialog extends TitledPageApplicationDialog {

	private Form form;
	
	public ScanDiscDialog(ScanSettings settings) {
		this.form = new ScanDiscForm(settings); 
		setDialogPage(new FormBackedDialogPage(this.form));
	}
	
	@Override
	protected boolean onFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
