package org.raven.spring.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author yanfeng
 * date 2019.05.27 15:33
 */
@Component
public class ValidatorUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidatorUtil() {
    }

    @Autowired
    public void setValidator(Validator validator) {
        ValidatorUtil.validator = validator;
    }

    public static <T> void validate(T data) {

        Set<ConstraintViolation<T>> set = validator.validate(data);

        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }

    }

}
