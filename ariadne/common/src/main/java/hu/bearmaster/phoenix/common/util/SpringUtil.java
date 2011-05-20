package hu.bearmaster.phoenix.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * A utility class for obtaining Spring beans.
 * @author Zoltan_Molnar1
 *
 */


public final class SpringUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtil.class);

    private static ApplicationContext userContext;
    public static final String PRIMARY_TARGET = "spring-ariadne.xml";
    
    public static final String PRIMARY_CONTEXT_PATH = "/hu/bearmaster/phoenix/common/context";
    
    public static final String SPRING_XML = "Spring-xml";
    public static final String SPRING_CONTEXT_PATH = "Spring-context";
    
    static {
        try {
            String targetXml = System.getProperty(SPRING_XML) == null ? PRIMARY_TARGET : System.getProperty(SPRING_XML);
            String targetContextPath = System.getProperty(SPRING_CONTEXT_PATH) == null ? PRIMARY_CONTEXT_PATH : System.getProperty(SPRING_CONTEXT_PATH);
            String springXml = targetContextPath + "/" + targetXml;
            
            userContext = new ClassPathXmlApplicationContext(springXml);
        } catch (Exception ex) {
            LOGGER.error("Spring Application Context Initialization Failed!", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * SpringUtil is a utility class, so it should has a private constructor (Checkstyle).
     */
    private SpringUtil() { }

    /**
     * Returns a bean object with the given name, via Spring.
     * @param beanName the bean's name, that is needed
     * @return an object with a given bean name, or a RuntimeException if the given name is not valid
     */
    public static Object getBean(final String beanName) {
        try {
            return userContext.getBean(beanName);
        } catch (Exception ex) {
            LOGGER.error("Spring bean not found! Bean: {}", beanName);
            throw new RuntimeException(ex); // NOPMD by "Zoltan Molnar" on 2010.05.15. 18:52
        }
    }
}
