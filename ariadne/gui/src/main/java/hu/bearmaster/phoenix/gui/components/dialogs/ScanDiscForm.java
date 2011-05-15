package hu.bearmaster.phoenix.gui.components.dialogs;

import hu.bearmaster.phoenix.common.filewalker.FileWalker;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.gui.components.DirectoryChooserComboBox;
import hu.bearmaster.phoenix.gui.components.render.CustomComboBoxRenderer;
import hu.bearmaster.phoenix.gui.components.render.CustomObjectRenderer;
import hu.bearmaster.phoenix.gui.model.ScanSettings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.form.FormModel;
import org.springframework.binding.value.ValueModel;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.binding.BindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ScanDiscForm extends AbstractForm {
	
	public static final String SCAN_DISC_FORM = "scanDiscForm";
	private static final Logger LOG = LoggerFactory.getLogger(ScanDiscForm.class);

	private JTextArea scannedFiles;
	private DirectoryChooserComboBox dirChooser;
	
	public ScanDiscForm(ScanSettings scanResult) {
		super(scanResult);
		setId(SCAN_DISC_FORM);
		getFormModel().setId(SCAN_DISC_FORM);
	}

	@Override
	protected JComponent createFormControl() {
		BindingFactory bindingFactory = getBindingFactory();
		
		TableFormBuilder formBuilder = new TableFormBuilder(bindingFactory);
        formBuilder.setLabelAttributes("colGrId=label colSpec=right:pref");
        formBuilder.addSeparator("Select drive");
        formBuilder.row();
        
        FormModel formModel = getFormModel();
        dirChooser = new DirectoryChooserComboBox(getFormModel(), "scanRoot", getSelectableRoots());
        
        dirChooser.setLabelMessageCode(SCAN_DISC_FORM + ".dirChooser");
        dirChooser.getDirectoryChooser().setCurrentDirectory(getMyComputer()); //FIXME not working as expected
        dirChooser.getDirectoryChooser().setShowingCreateDirectory(false);
        dirChooser.setComboBoxRenderer(new CustomComboBoxRenderer(new RootPathRenderer()));

        formBuilder.getLayoutBuilder().cell( dirChooser.getControl() );
        formBuilder.row();
        formBuilder.getLayoutBuilder().cell( createScanButtonPanel() );
        formBuilder.row();
        
        formBuilder.addSeparator("Disc settings");
        formBuilder.row();
        
        formBuilder.add("discName");
        formBuilder.row();
        
        formBuilder.add("discCategory");
        formBuilder.row();
        
        formBuilder.add("discType");
        formBuilder.row();
        
        
        scannedFiles = new JTextArea();
        scannedFiles.setRows(10);
        scannedFiles.setEditable(false);
        formBuilder.getLayoutBuilder().cell(new JScrollPane(scannedFiles));
        formBuilder.row();
        
		return formBuilder.getForm();
	}
	
	private JPanel createScanButtonPanel() {
		JPanel panel = getComponentFactory().createPanel();
		
		//FormLayout layout = new FormLayout("max(200px;pref), 5dlu, left:pref", "pref");
		FormLayout layout = new FormLayout("pref:grow, 6dlu:none, max(65px;pref)", "pref");
		
		panel.setLayout(layout);
		
		JProgressBar progressBar = new JProgressBar(0,100);
		progressBar.setStringPainted(true);
		CellConstraints cc = new CellConstraints();
		
		panel.add(progressBar, cc.xy(1, 1));
		
		FormModel formModel = getFormModel();
		panel.add(new ScanCommand(formModel.getValueModel("scanRoot"), progressBar).createButton(), cc.xy(3, 1));
		
		return panel;
	}
	
	private class ScanTask extends SwingWorker<ScanResult, Void> {

		private ScanCommand command;
		
		public ScanTask(ScanCommand command) {
			this.command = command;
		}
		
		@Override
		protected ScanResult doInBackground() throws Exception {
			FileWalker fw = new FileWalker(dirChooser.getSelectedFile());
			fw.scan();
			ScanResult scan = fw.getResult();
			return scan;
		}
		
		@Override
		protected void done() {
			try {
				if(!isCancelled()) {
					command.setScanResult(get());
				}				
			} catch (InterruptedException e) {
				LOG.warn("Getting scanresults is interrupted!", e);
			} catch (ExecutionException e) {
				LOG.warn("Exception during scan execution!", e);
			} catch (CancellationException e) {
				LOG.warn("ScanTask was cancelled!", e);
			}
		}
		
	}
	
	private class ScanCommand extends ActionCommand implements PropertyChangeListener {
		
		private JProgressBar progressBar;
		private boolean underExecution = false;
		private ScanTask task;
		
		public ScanCommand(ValueModel model, JProgressBar progressBar) {
			super("scanCommandId", "Scan", null, "Scan command caption");
			model.addValueChangeListener(this);
			setEnabled(false);
			this.progressBar = progressBar;
		}

		public void setScanResult(ScanResult scanResult) {
			progressBar.setIndeterminate(false);
			progressBar.setString("Done!");
			progressBar.setValue(100);
			
			StringBuilder sb = new StringBuilder("Disc: ");
			sb.append(scanResult.getDisc());
			sb.append("\n---------\nDiscItems:\n");
			
			int i = 1;
			for (DiscItem di : scanResult.getDiscItems()) {
				sb.append(" (" + (i++) + ") - ");
				sb.append(di);
				sb.append("\n");
			}
			scannedFiles.setText(sb.toString());
			scannedFiles.repaint();
			setLabel("Scan");
			setEnabled(true);
			dirChooser.setEnabled(true);
			underExecution = false;
		}

		@Override
		protected void doExecuteCommand() {
			if (!underExecution) {
				progressBar.setIndeterminate(true);
				progressBar.setString("Scanning...");
				//setEnabled(false);
				setLabel("Cancel");
				dirChooser.setEnabled(false);
				task = new ScanTask(this);
				underExecution = true;
				task.execute();
			}
			else {
				boolean cancelSucceeded = task.cancel(true);				
				progressBar.setIndeterminate(false);
				progressBar.setString("Cancelled");
				progressBar.setValue(0);
				setLabel("Scan");
				setEnabled(true);
				dirChooser.setEnabled(true);
				underExecution = false;
			}
		}

		public void propertyChange(PropertyChangeEvent pce) {
			/*
			Object newValue = pce.getNewValue();
			String fileName = newValue.toString();
			File f = new File(fileName);
			setEnabled(f.exists());
			*/
			if (!underExecution) {
				setEnabled(new File(pce.getNewValue().toString()).exists());
			}					
		}
		
	}
	
	private List<String> getSelectableRoots() {
		ArrayList<String> roots = new ArrayList<String>();
		for (File r : File.listRoots()) {
			roots.add(r.getAbsolutePath());
		}
		return roots;
	}
	
	private File getMyComputer() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File myComputer = null;
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			File desktop = fsv.getRoots()[0]; 
			for (File c : desktop.listFiles()) {
				//XXX Hack for determining which file is the My Computer (tested on Windows XP only)
				LOG.info("ToS: {} Abs: {}", c.toString(), c.getAbsolutePath());
				if(c.getAbsolutePath().endsWith("{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
					myComputer = c;
					break;
				}
			}
		}
		
		return myComputer;
	}
	
	private class RootPathRenderer implements CustomObjectRenderer {
		
		private Map<String, String> cache;
		
		public RootPathRenderer() {
			//TODO Consider to put this constructor block into a SwingWorker thread
			File[] roots = File.listRoots();
			cache = new HashMap<String, String>(roots.length);
			for (File r : roots) {
				String key = r.toString();
				String value = String.format("%s - %s", r.toString(), FileSystemView.getFileSystemView().getSystemDisplayName(r));
				cache.put(key, value);
			}
		}

		public String getRenderValue(Object o) {
			String value = cache.get(o.toString());
			if (value != null) {
				return value;
			}
			return o.toString();
		}
		
	}

}
