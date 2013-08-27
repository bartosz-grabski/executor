package agh.bit.ideafactory.helpers;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 13.07.13
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.ui.ModelMap;

/**
 * This is a util class for handling various operations with ModelMap class
 * 
 * @author bgrabski
 */
public class ModelMapUtils {

	/**
	 * Method for setting error attribute into model
	 * 
	 * @param model
	 *            ModelMap to be filled
	 * @param message
	 *            String message
	 */
	public static void setError(ModelMap model, String message) {
		model.addAttribute("message", message);
		model.addAttribute("error", true);
	}

	/**
	 * Method for setting success attribute into model
	 * 
	 * @param model
	 *            ModelMap to be filled
	 * @param message
	 *            String message
	 */
	public static void setSuccess(ModelMap model, String message) {
		model.addAttribute("message", message);
		model.addAttribute("success", true);
	}

}
