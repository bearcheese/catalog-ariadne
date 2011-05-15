package hu.bearmaster.phoenix.gui.components.forms;

import hu.bearmaster.phoenix.common.model.Disc;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.binding.BindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;

public class DiscForm extends AbstractForm {

	public static final String DISC_FORM = "discForm";
	
	public DiscForm(Disc disc) {
		super(disc);
		setId(DISC_FORM);
		getFormModel().setId(DISC_FORM);
	}

	@Override
	protected JComponent createFormControl() {
		BindingFactory bindingFactory = getBindingFactory();		
		
		TableFormBuilder formBuilder = new TableFormBuilder(bindingFactory);
        formBuilder.setLabelAttributes("colGrId=label colSpec=right:pref");
        formBuilder.addSeparator("General");
        formBuilder.row();
        
        JTextField idField = (JTextField)formBuilder.add("id", "align=left")[1];
        idField.setColumns(5);
        idField.setEditable(false);
        idField.setEnabled(false);
        formBuilder.row();
        
        JTextField titleField = (JTextField) formBuilder.add("name")[1];
        titleField.setColumns(50);
        formBuilder.row();
        
        formBuilder.add("volumeName");
        formBuilder.row();
        
        formBuilder.add("type");
        formBuilder.row();
        
        formBuilder.add("category");
        formBuilder.row();
        
        formBuilder.add("size");
        formBuilder.row();
        
        formBuilder.add("created");
        formBuilder.row();
        
        formBuilder.addTextArea("comment");    
        formBuilder.row();
        
        return formBuilder.getForm();
	}

}
