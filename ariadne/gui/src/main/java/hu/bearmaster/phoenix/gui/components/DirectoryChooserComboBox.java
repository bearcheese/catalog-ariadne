package hu.bearmaster.phoenix.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import org.springframework.binding.form.ValidatingFormModel;
import org.springframework.binding.validation.ValidationListener;
import org.springframework.richclient.factory.AbstractControlFactory;
import org.springframework.richclient.form.binding.swing.ComboBoxBinding;
import org.springframework.richclient.form.binding.swing.SwingBindingFactory;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.l2fprod.common.swing.JDirectoryChooser;

/**
 * A combo box that allows you to type and/or select files, as well as click a
 * Browse button to navigate to the file you wish to work with.
 * 
 * Source copied from the original {@link org.springframework.richclient.filechooser.FileChooserComboBox} class, and added some modifications
 * based on <a href="http://jira.springframework.org/browse/RCP-94">FileChooserComboBox improvements</a> suggestions. 
 * 
 * @see org.springframework.richclient.filechooser.FileChooserComboBox
 * 
 */
public class DirectoryChooserComboBox extends AbstractControlFactory {

    private JDirectoryChooser dirChooser;

    private String fileChooserLabel = "fileChooserLabel";

    private JComponent fileNameField;

    private JButton browseButton;

    private File startDirectory;

    private ValidatingFormModel formModel;

    private String formProperty;
    
    private final List<String> selectableItems;
    
    private ListCellRenderer comboBoxRenderer;

    public DirectoryChooserComboBox() {
    	selectableItems = null;
    }

    public DirectoryChooserComboBox(ValidatingFormModel formModel, String formProperty) {
    	this(formModel, formProperty, null);
    }
    
    public DirectoryChooserComboBox(ValidatingFormModel formModel, String formProperty, List<String> selectableItems) {
        this.formModel = formModel;
        this.formProperty = formProperty;
        this.selectableItems = selectableItems;
    }
    
    public void setComboBoxRenderer(ListCellRenderer renderer) {
    	comboBoxRenderer = renderer;
    }

    public void addValidationListener(ValidationListener listener) {
        formModel.getValidationResults().addValidationListener(listener);
    }

    public void removeValidationListener(ValidationListener listener) {
        formModel.getValidationResults().removeValidationListener(listener);
    }

    public void setLabelMessageCode(String labelKey) {
        this.fileChooserLabel = labelKey;
    }

    public void setStartDirectory(File file) {
        this.startDirectory = file;
    }

    public File getStartDirectory() {
        if (startDirectory != null) {
            return startDirectory;
        }
        else {
            File selectedFile = getSelectedFile();
            if (selectedFile != null) {
            	return selectedFile.isDirectory() ? 
                        selectedFile : selectedFile.getParentFile();
            }            
        }
        return null;
    }

    public File getSelectedFile() {
        String filePath = (String)formModel.getValueModel(formProperty).getValue();
        return (filePath == null) ? null : new File( filePath );
    }

    public void setEnabled(boolean enabled) {
        fileNameField.setEnabled(enabled);
        browseButton.setEnabled(enabled);
    }

    public JDirectoryChooser getDirectoryChooser(){
        if (dirChooser == null) {
        	dirChooser = new JDirectoryChooser(getStartDirectory());
        }
        return dirChooser;
    }
    
    protected JComponent createControl() {
        bindNameField();
        JLabel fileToProcess = getComponentFactory().createLabelFor(fileChooserLabel, fileNameField);
        this.browseButton = getComponentFactory().createButton("button.browse");
        BrowseActionHandler browseActionHandler = new BrowseActionHandler();
        browseButton.addActionListener(browseActionHandler);
        FormLayout layout = new FormLayout("pref:grow, 6dlu:none, max(65px;pref)", "pref, 3dlu, pref");
        JPanel panel = new JPanel(layout);
        CellConstraints cc = new CellConstraints();
        panel.add(fileToProcess, cc.xyw(1, 1, 3));
        panel.add(fileNameField, cc.xy(1, 3));
        panel.add(browseButton, cc.xy(3, 3));
        return panel;
    }
    
    protected void bindNameField() {
    	if (selectableItems == null) {
    		//Bind as a normal JTextField
    		fileNameField = new SwingBindingFactory(formModel).createBinding(JTextField.class, formProperty).getControl();
    	}
    	else {
    		//Bind as JComboBox
    		ComboBoxBinding binding = (ComboBoxBinding)new SwingBindingFactory(formModel).createBinding(JComboBox.class, formProperty);
    		binding.setSelectableItems(selectableItems);
    		binding.setRenderer(comboBoxRenderer); //have to set here, to make sure the JComboBox is already existing
    		fileNameField = binding.getControl();
    	}
    }
    
    protected void setFieldNameText(String text) {
    	if (selectableItems == null) {
    		((JTextField)fileNameField).setText(text);
    	}
    	else {
    		//This will only allow to select root drives in the directory chooser component
    		//TODO change to allow select any directory in the file system
    		JComboBox comboBox = (JComboBox)fileNameField;
    		ComboBoxModel comboBoxModel = comboBox.getModel();
    		comboBoxModel.setSelectedItem(text);
    	}
    }
    
    private class BrowseActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            getDirectoryChooser();
            dirChooser.setCurrentDirectory(getStartDirectory());
            int returnVal = dirChooser.showOpenDialog(SwingUtilities.getWindowAncestor(browseButton));
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = dirChooser.getSelectedFile();
                setFieldNameText(selectedFile.getAbsolutePath());
                if (selectedFile.isDirectory()) {
                    setStartDirectory(selectedFile);
                }
                else {
                    setStartDirectory(selectedFile.getParentFile());
                }
            }
        }
    }
}