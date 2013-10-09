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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	 * Method for setting error attribute into model before redirecting
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes passed to controller method
	 * @param message
	 *            String message
	 */
	public static void setRedirectError(RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("error", true);
	}

	/**
	 * Method adding all flash attributes to selected ModelMap - used in GET controller methods when redirected from POST method
	 * 
	 * @param modelMap
	 *            ModelMap to be filled
	 * @param request
	 *            HttpServletRequest from which attributes will be taken
	 */
	public static void addFlashAttributesToModelMap(ModelMap map, HttpServletRequest request) {
		if (RequestContextUtils.getInputFlashMap(request) != null) {
			for (Entry<String, ?> entry : RequestContextUtils.getInputFlashMap(request).entrySet()) {

				if (!entry.getKey().startsWith("_bindingResult")) {
					map.addAttribute(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	/**
	 * Method setting bindingResult errors before redirect to controller GET method with form previously send - used in POST controller methods before redirected to GET method
	 * 
	 * @param modelAttributeValue
	 *            name value of @ModelAttribute annotation from this methods
	 * @param bindingResult
	 *            bindingResult to be redirected
	 * @param redirectAttributes
	 *            RedirectAttributes passed to controller method
	 */
	public static void setRedirectBindingResult(String modelAttributeValue, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("_bindingResult", bindingResult);
		redirectAttributes.addFlashAttribute("_bindingResultModelAttributeValue", modelAttributeValue);
	}

	/**
	 * Method adding bindingResult errors to ModelMap when redirected to controller GET method with form previously send - used in GET controller methods when redirected from POST method
	 * 
	 * @param map
	 *            ModelMap passed to controller method
	 */
	public static void addBindingResultToModelMap(ModelMap map) {
		if (map.containsKey("_bindingResult") && map.containsKey("_bindingResultModelAttributeValue")) {
			map.addAttribute("org.springframework.validation.BindingResult." + map.get("_bindingResultModelAttributeValue"), map.get("_bindingResult"));
		}
	}

}
