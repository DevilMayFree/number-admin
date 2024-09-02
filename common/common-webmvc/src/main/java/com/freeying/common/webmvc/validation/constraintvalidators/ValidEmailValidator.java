package com.freeying.common.webmvc.validation.constraintvalidators;

import cn.hutool.core.lang.Validator;
import com.freeying.common.webmvc.validation.constraints.ValidEmail;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Email;

/**
 * ValidEmailValidator
 *
 * @author fx
 */
public class ValidEmailValidator implements ConstraintValidator<ValidEmail, CharSequence> {

    private boolean enableEmpty;

    @Override
    public void initialize(ValidEmail annotation) {
        ConstraintValidator.super.initialize(annotation);
        enableEmpty = annotation.enableEmpty();
    }

    @Email
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(charSequence)) {
            return enableEmpty;
        }
        return Validator.isEmail(charSequence);
    }
}
