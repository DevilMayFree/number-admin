package com.freeying.common.webmvc.validation.constraintvalidators;

import cn.hutool.core.lang.Validator;
import com.freeying.common.webmvc.validation.constraints.ValidMobile;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ValidEmailValidator
 *
 * @author fx
 */
public class ValidMobileValidator implements ConstraintValidator<ValidMobile, CharSequence> {

    private boolean enableEmpty;

    @Override
    public void initialize(ValidMobile annotation) {
        ConstraintValidator.super.initialize(annotation);
        enableEmpty = annotation.enableEmpty();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(charSequence)) {
            return enableEmpty;
        }
        return Validator.isMobile(charSequence);
    }
}
