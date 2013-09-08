package agh.bit.ideafactory.helpers;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.validation.Errors;

/**
 * BeanValidator automatically validates correctness of model classes based on annotations.
 */
public class BeanValidator implements org.springframework.validation.Validator {

	private Validator validator;

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	/**
	 * Validates model classes correctness and adds errors found target - object to validate errors - usually BindingResult from Spring controller<br/>
	 * <b>IMPORTANT </b> - when using errors.rejectValue(...) set 2nd parameter as " " to work as expected
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.usingContext().getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			String propertyPath = constraintViolation.getPropertyPath().toString();
			String message = "Field " + propertyPath + " " + constraintViolation.getMessage();
			errors.rejectValue(propertyPath, " ", message);
		}
	}

}
