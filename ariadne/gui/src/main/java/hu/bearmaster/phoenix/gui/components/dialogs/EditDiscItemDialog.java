package hu.bearmaster.phoenix.gui.components.dialogs;

import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.gui.components.forms.DiscItemForm;

import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

public class EditDiscItemDialog extends TitledPageApplicationDialog {

	private Form form;
	private boolean approved;
	
	
	public EditDiscItemDialog(DiscItem discItem) {
		this.form = new DiscItemForm(discItem);
		setDialogPage(new FormBackedDialogPage(form));
	}
	
	@Override
	protected void onAboutToShow() {
		super.onAboutToShow();
		DiscItem item = (DiscItem)form.getFormObject();
		
		String title = getMessage("editDiscItemDialog.title", new String[]{item.getName()});
		setTitle(title);
	}
	
	@Override
	protected boolean onFinish() {
		approved = true;
		return false;
	}

	public boolean isApproved() {
		return approved;
	}
	
	@Override
	protected void onCancel() {
		super.onCancel();
		approved = false;
	}
	
	

}
