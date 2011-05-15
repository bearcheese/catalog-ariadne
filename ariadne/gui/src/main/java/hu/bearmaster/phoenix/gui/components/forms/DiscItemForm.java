package hu.bearmaster.phoenix.gui.components.forms;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import hu.bearmaster.phoenix.common.model.DiscItem;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.BindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;

public class DiscItemForm extends AbstractForm {

	public static final String DISC_ITEM_FORM = "discItemForm";

	public DiscItemForm(DiscItem discItem) {
		super(discItem);
		setId(DISC_ITEM_FORM);
		getFormModel().setId(DISC_ITEM_FORM);
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
        //idField.setEditable(false);
        //idField.setEnabled(false);
        formBuilder.row();
        
        JTextField titleField = (JTextField) formBuilder.add("name")[1];
        titleField.setColumns(50);
        formBuilder.row();
        
        formBuilder.add("path");
        formBuilder.row();
        
        formBuilder.add("length");
        formBuilder.row();
        
        //formBuilder.add("disc");
        //formBuilder.row();
        
        //TODO table for props?
        
		//legyártom a szükséges parancsokat a menühöz és a gombokhoz
        //ActionCommand[] popupActions = new ActionCommand[]{new NewAddressCommand(getItem()), new EditAddressCommand(getItem())};
        
        //A binding-gal felkerülő táblázat a context-ben kapja meg a parancsokat
        Map context = new HashMap();
        //context.put("popup_commands", popupActions);
        Binding itemPropertyBinding = bindingFactory.createBinding("propertyList", context);        
        
        JScrollPane scrollPane = (JScrollPane)formBuilder.addInScrollPane(itemPropertyBinding)[2];
        Dimension prefSize = new Dimension(scrollPane.getPreferredSize().width, 100);
        scrollPane.setPreferredSize(prefSize);
        formBuilder.row();
        
        //egy érdekes "hack", egyszerűen elkérem a layout builder-t és belrakok egy saját cellát
        //TableLayoutBuilder tableLayoutBuilder = formBuilder.getLayoutBuilder();
        //tableLayoutBuilder.cell(createButtonPanel(popupActions));
        
        return formBuilder.getForm();
	}

}
