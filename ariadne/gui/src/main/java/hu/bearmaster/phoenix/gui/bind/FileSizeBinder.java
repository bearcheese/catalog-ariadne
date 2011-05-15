package hu.bearmaster.phoenix.gui.bind;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.support.AbstractBinder;
import org.springframework.util.Assert;

public class FileSizeBinder extends AbstractBinder {

	protected FileSizeBinder() {
		super(Long.class);
	}

	@Override
	protected JComponent createControl(Map context) {
		 return getComponentFactory().createTextField();
	}

	@Override
	protected Binding doBind(JComponent control, FormModel formModel, String formPropertyPath, Map context) {
		Assert.isTrue(control instanceof JTextComponent, "Control must be an instance of JTextComponent.");
		return new FileSizeBinding((JTextComponent)control, formModel, formPropertyPath);
	}

}
