package hu.bearmaster.phoenix.gui.components.render;

/**
 * A simple interface which can be used as a wrapper for model objects, to separate rendering
 * label for the UI (so don't have to use toString)
 * 
 * @author "Zoltan Molnar"
 *
 */
public interface CustomObjectRenderer {
	
	String getRenderValue(Object o);

}
