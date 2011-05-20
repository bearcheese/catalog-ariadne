package hu.bearmaster.phoenix.gui.bind;

import hu.bearmaster.phoenix.common.util.SizeFormatter;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.springframework.binding.form.FormModel;
import org.springframework.binding.value.ValueModel;
import org.springframework.richclient.form.binding.support.AbstractBinding;

public class FileSizeBinding extends AbstractBinding {

	private final JTextComponent textComponent;
	
	protected FileSizeBinding(JTextComponent control, FormModel formModel, String formPropertyPath) {
		super(formModel, formPropertyPath, Long.class);
		this.textComponent = control;
	}

	@Override
	protected JComponent doBindControl() {
		final ValueModel valueModel = getValueModel();
        try {
            textComponent.setText(getSizeString((Long)valueModel.getValue()));
        }
        catch (ClassCastException e) {
            IllegalArgumentException ex = new IllegalArgumentException("Class cast exception converting '"
                    + getProperty() + "' property value to string - did you install a type converter?");
            ex.initCause(e);
            throw ex;
        }
        return textComponent;
	}
	
	protected String getSizeString(Long size) {
		return SizeFormatter.getDoubleDynamicSuffix(size) + " (" + size + " bytes)";
	}

	@Override
	protected void readOnlyChanged() {
        textComponent.setEditable(! isReadOnly());
    }

	@Override
    protected void enabledChanged() {
        textComponent.setEnabled(isEnabled());
    }

}
