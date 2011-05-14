package hu.bearmaster.phoenix.gui;
import hu.bearmaster.phoenix.common.util.SpringUtil;

import org.springframework.richclient.application.ApplicationLauncher;
import org.apache.log4j.Logger;


public class Main {

	public static void main(String[] args) {
		String rootContextDirectoryClassPath = SpringUtil.PRIMARY_CONTEXT_PATH;

        String startupContextPath = rootContextDirectoryClassPath + "/startup-context.xml";

        String richclientApplicationContextPath = rootContextDirectoryClassPath
                + "/application-context.xml";

        String businessLayerContextPath = rootContextDirectoryClassPath + "/spring-ariadne.xml";

        //String securityContextPath = rootContextDirectoryClassPath + "/standalone/security-context.xml";

        try {
            new ApplicationLauncher(startupContextPath, new String[] { richclientApplicationContextPath,
                    businessLayerContextPath /*, securityContextPath */});
        } catch (RuntimeException e) {
        	Logger.getLogger(Main.class).error("RuntimeException during startup", e);
        }

	}

	
}
