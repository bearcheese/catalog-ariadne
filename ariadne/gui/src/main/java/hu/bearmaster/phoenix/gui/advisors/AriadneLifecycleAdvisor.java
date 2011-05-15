package hu.bearmaster.phoenix.gui.advisors;

import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.config.ApplicationWindowConfigurer;
import org.springframework.richclient.application.config.DefaultApplicationLifecycleAdvisor;
import org.springframework.richclient.application.setup.SetupWizard;

public class AriadneLifecycleAdvisor extends DefaultApplicationLifecycleAdvisor {

	/**
	 * Show a setup wizard before actual applicationWindow is created. This
	 * should happen only on Application startup and only once. (note: for this
	 * to happen only once, a state should be preserved, which is not the case
	 * with this sample)
	 */
	public void onPreStartup() {
		if (getApplication().getApplicationContext().containsBean("setupWizard")) {
			SetupWizard setupWizard = (SetupWizard) getApplication()
					.getApplicationContext().getBean("setupWizard",	SetupWizard.class);
			setupWizard.execute();
		}
	}

	/**
	 * Additional window configuration before it is created.
	 */
	public void onPreWindowOpen(ApplicationWindowConfigurer configurer) {
		super.onPreWindowOpen(configurer);
		// comment out to hide the menubar, toolbar, or reduce window size...
		// configurer.setShowMenuBar(false);
		// configurer.setShowToolBar(false);
		// configurer.setInitialSize(new Dimension(640, 480));
	}
	
	@Override
	public void onWindowCreated(ApplicationWindow window) {
		super.onWindowCreated(window);
		//TODO switch to start in maximized
		//window.getControl().setExtendedState(window.getControl().getExtendedState()|JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * When commands are created, lookup the login command and execute it.
	 */
	public void onCommandsCreated(ApplicationWindow window) {
		/*
		ActionCommand command = (ActionCommand) window.getCommandManager()
				.getCommand("loginCommand", ActionCommand.class);
		command.execute();
		*/
	}

}
