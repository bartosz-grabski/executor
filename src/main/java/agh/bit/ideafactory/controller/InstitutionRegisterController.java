package agh.bit.ideafactory.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import agh.bit.ideafactory.exception.IncorrectRegisterDataException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.service.InstitutionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static agh.bit.ideafactory.helpers.ModelMapUtils.setError;
import static agh.bit.ideafactory.helpers.ModelMapUtils.setSuccess;

/**
 * @author bgrabski Class for handling request to create InstitutionAccount
 */
@Controller
public class InstitutionRegisterController {

	private static final String viewName = "home/institution_register";

    @Autowired
	InstitutionService institutionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Simply returns the view
	 * 
	 * @return String representation of the associated view
	 */
	@RequestMapping(value = "/business/register", method = RequestMethod.GET)
	public String registerGet(ModelMap modelMap, HttpServletRequest request) {
		return viewName;
	}

	@RequestMapping(value = "/business/register", method = RequestMethod.POST)
	public String registerPost(ModelMap modelMap, HttpServletRequest request) {
		Map<String,String> requestData = fetchRequestData(request);

        try {
            checkPasswordConfirmedProperly(requestData);
        } catch (PasswordMatchException e) {
            setError(modelMap,e.getMessage());
            return viewName;
        }

        Institution institution = createInstitutionFromRequestData(requestData);

        try {
            validateInstitution(institution);
        } catch (IncorrectRegisterDataException e) {
            setError(modelMap,e.getMessage());
            return viewName;
        }

        try {
            persistInstitutionToDatabase(institution);
        } catch (Exception e) {
            setError(modelMap, e.getMessage());
            return viewName;
        }
        setSuccess(modelMap,"Acocunt created");
        return viewName;
	}

    private Institution createInstitutionFromRequestData(Map<String,String> requestData) {
        Institution newInstitution = new Institution();
        newInstitution.setUsername(requestData.get("email"));
        newInstitution.setPassword(requestData.get("password"));

        newInstitution.setEnabled(true);
        return newInstitution;
    }

    private Map<String,String> fetchRequestData(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordconf = request.getParameter("passwordconf");
        Map<String,String> requestData = new HashMap<String,String>();
        requestData.put("email",email);
        requestData.put("password",password);
        logger.info(requestData.get("password"));
        requestData.put("passwordconf",passwordconf);
        return requestData;
	}

    private void persistInstitutionToDatabase(Institution institution) {
        institutionService.addInstitution(institution);
    }

    private void checkPasswordConfirmedProperly(Map<String,String> requestData) throws PasswordMatchException {
        String password = null;
        if ((password = requestData.get("password")) == null || !(password.equals(requestData.get("passwordconf")))) {
            throw new PasswordMatchException("Passwords don't match");
        }
    }

    private void validateInstitution(Institution institution) throws IncorrectRegisterDataException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Institution>> violations = validator.validate(institution);
        if (violations.size() != 0) {
            throw new IncorrectRegisterDataException("Incorrect input data");
        }
    }


}
