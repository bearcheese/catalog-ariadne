package hu.bearmaster.phoenix.gui.components.dialogs;

import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.gui.components.forms.DiscForm;

import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

public class EditDiscDialog extends TitledPageApplicationDialog {

	private Form form;
	private boolean approved;
	
	public EditDiscDialog(Disc disc) {
		this.form = new DiscForm(disc);
		setDialogPage(new FormBackedDialogPage(form));
	}

	@Override
	protected void onAboutToShow() {
		Disc disc = (Disc) form.getFormObject();
		String name = disc.getName();
		String title;
		if(name == null) {
			title = getMessage("disc.properties.title.new");
		} 
		else {
			title = getMessage("disc.properties.title.edit", new Object[]{name});
		}
		setTitle(title);
	}
	
	@Override
	protected boolean onFinish() {
		form.getFormModel().commit();
		Disc disc = (Disc)form.getFormObject();
		
		//TODO review validation
		/*
		ValidationService validationService = (ValidationService)getApplicationContext().getBean("validatorService", ValidationService.class);
		
		Errors errors = new BeanPropertyBindingResult(account, "account");
		validationService.validate(account, errors);
		
		if(errors.hasErrors()) {
			if(editable && errors.getFieldError("bookNumber") != null) {
				handleErrors(account, errors);
				return false;
			}			
		}
		*/
		approved = true;
		return true;
	}
	
	@Override
	protected void onCancel() {
		super.onCancel();
		approved = false;
	}
	
	public boolean isApproved() {
		return approved;
	}

}
