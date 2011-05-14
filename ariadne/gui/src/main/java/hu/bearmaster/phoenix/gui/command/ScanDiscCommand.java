package hu.bearmaster.phoenix.gui.command;

import hu.bearmaster.phoenix.common.services.FileManagementService;
import hu.bearmaster.phoenix.common.services.PersistenceService;
import hu.bearmaster.phoenix.gui.components.dialogs.ScanDiscDialog;
import hu.bearmaster.phoenix.gui.model.ScanSettings;

import org.springframework.richclient.application.support.DefaultViewDescriptor;
import org.springframework.richclient.command.support.ApplicationWindowAwareCommand;

public class ScanDiscCommand extends ApplicationWindowAwareCommand {

	private FileManagementService fileManagementService;
	private PersistenceService persistenceService;
	private DefaultViewDescriptor viewDescriptor;
	
	public FileManagementService getFileManagementService() {
		return fileManagementService;
	}

	public void setFileManagementService(FileManagementService fileManagementService) {
		this.fileManagementService = fileManagementService;
	}

	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	public void setPersistenceService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}
	
	public DefaultViewDescriptor getViewDescriptor() {
		return viewDescriptor;
	}

	public void setViewDescriptor(DefaultViewDescriptor view) {
		this.viewDescriptor = view;
	}

	@Override
	protected void doExecuteCommand() {
		ScanSettings settings = new ScanSettings();
		
		ScanDiscDialog dialog = new ScanDiscDialog(settings);
		dialog.showDialog();
	}
}
