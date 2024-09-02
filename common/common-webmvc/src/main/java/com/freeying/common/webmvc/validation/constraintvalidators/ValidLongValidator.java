package com.freeying.common.webmvc.validation.constraintvalidators;

import com.freeying.common.webmvc.validation.constraints.ValidLong;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ValidLongValidator
 * 字符串传入的必须为数值  可为空
 *
 * @author fx
 */
public class ValidLongValidator implements ConstraintValidator<ValidLong, String> {

    private boolean enableEmpty;

    @Override
    public void initialize(ValidLong annotation) {
        ConstraintValidator.super.initialize(annotation);
        this.enableEmpty = annotation.enableEmpty();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return enableEmpty;
        }
        try {
            Long.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
