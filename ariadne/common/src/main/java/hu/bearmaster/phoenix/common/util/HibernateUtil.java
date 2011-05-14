package hu.bearmaster.phoenix.common.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton utility class for handling Hibernate session factory.
 * @author "Zoltan Molnar"
 *
 */

public final class HibernateUtil {
	
	private HibernateUtil() {}
	
	private static final SessionFactory SESSION_FACTORY;
	private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);
	
	static {
		try {
			//Create the SessionFactory from hibernate.cfg.xml
			SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
		} catch (Exception ex) {
			// Make sure you log the exception, as it might be swallowed
			LOGGER.error("Initial Hibernate SessionFactory creation failed!", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	/**
	 * Returns the created Hibernate session factory
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}

}
