package hu.bearmaster.phoenix.gui.executor;

import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.gui.components.dialogs.EditDiscItemDialog;
import hu.bearmaster.phoenix.gui.views.MainView;

import org.apache.log4j.Logger;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;

public class EditDiscItemExecutor extends AbstractActionCommandExecutor {

private static final Logger LOG = Logger.getLogger(EditDiscExecutor.class);
	
	private MainView view;
	
	public EditDiscItemExecutor(MainView view) {
		this.view = view;
	}	
	
	@Override
	public void execute() {
		DiscItem selectedDiscItem = view.getSelectedDiscItem();
		
		EditDiscItemDialog dialog = new EditDiscItemDialog(selectedDiscItem);
		dialog.showDialog();
		
		if (dialog.isApproved()) {
			LOG.info("Dialog approved, save changes: " + selectedDiscItem);
			//TODO update DB
		} else {
			LOG.info("Disc item update cancelled...");
		}
	}
	
}
