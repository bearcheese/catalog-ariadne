package hu.bearmaster.phoenix.gui.executor;

import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.gui.components.dialogs.EditDiscDialog;
import hu.bearmaster.phoenix.gui.views.MainView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;

public class EditDiscExecutor extends AbstractActionCommandExecutor {
	
	private static final Logger LOG = LoggerFactory.getLogger(EditDiscExecutor.class);
	
	private MainView view;
	
	public EditDiscExecutor(MainView view) {
		this.view = view;
	}	
	
	@Override
	public void execute() {
		Disc selectedDisc = view.getSelectedDisc();
		
		EditDiscDialog dialog = new EditDiscDialog(selectedDisc);
		dialog.showDialog();
		
		if (dialog.isApproved()) {
			LOG.info("Dialog approved, save changes: {}", selectedDisc);
			//TODO update the DB
		} else {
			LOG.info("Disc update cancelled...");
		}
	}

}
