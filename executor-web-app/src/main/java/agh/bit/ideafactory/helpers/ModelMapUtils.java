package agh.bit.ideafactory.helpers;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 13.07.13
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

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

	/**
	 * Method for setting success attribute into model when redirecting
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes passed to controller method
	 * @param message
	 *            String message
	 */
	public static void setRedirectSuccess(RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("success", true);
	}

	/**
	 * Method for setting error attribute into model when redirecting
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes passed to controller method
	 * @param message
	 *            String message
	 */
	public static void setRedirectError(RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("success", false);
	}

	/**
	 * Method adding all flash attributes to selected ModelMap
	 * 
	 * @param ModelMap
	 *            ModelMap to be filled
	 * @param request
	 *            HttpServletRequest from which attributes will be taken
	 */
	public static void addFlashAttributesToModelMap(ModelMap map, HttpServletRequest request) {
		if (RequestContextUtils.getInputFlashMap(request) != null) {
			for (Entry<String, ?> entry : RequestContextUtils.getInputFlashMap(request).entrySet()) {
				map.addAttribute(entry.getKey(), entry.getValue());
			}
		}
	}

}
