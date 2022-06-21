package org.raven.spring.web.util;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class MessageUtil {

    public static String getMessage(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(allErrors)) {
            return allErrors.stream().filter(Objects::nonNull).map(e -> "[" + e.getDefaultMessage() + "]").collect(Collectors.joining(" "));
        }
        return ex.getMessage();
    }

    public static String getMessage(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        return !org.apache.commons.collections4.CollectionUtils.isEmpty(constraintViolations) ? (String) constraintViolations.stream().filter(Objects::nonNull).map((cv) -> {
            String messageTemplate = cv.getMessageTemplate();
            if (!Objects.isNull(messageTemplate) && messageTemplate.contains("javax.validation")) {
                Path propertyPath = cv.getPropertyPath();

                String resultName = null;
                for (Path.Node node : propertyPath) {
                    final ElementKind elementKind = node.getKind();

                    if (elementKind == ElementKind.PROPERTY) {
                        resultName = node.getName();
                    }

                }
                return "[" + resultName + cv.getMessage() + "]";
            }


            return "[" + cv.getMessage() + "]";
        }).collect(Collectors.joining(" ")) : ex.getMessage();
    }

}
