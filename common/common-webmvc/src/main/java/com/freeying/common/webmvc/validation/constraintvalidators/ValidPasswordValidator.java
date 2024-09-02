package com.freeying.common.webmvc.validation.constraintvalidators;

import com.freeying.common.webmvc.utils.PasswordUtil;
import com.freeying.common.webmvc.validation.constraints.ValidPassword;
import org.apache.commons.lang3.StringUtils;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ValidPasswordValidator
 *
 * @author fx
 */
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final MessageResolver messageResolver;

    public ValidPasswordValidator(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public boolean isValid(final String charSequence, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(charSequence)) {
            return false;
        }

        PasswordValidator validator = new PasswordValidator(messageResolver, PasswordUtil.getPasswordRules());
        RuleResult result = validator.validate(new PasswordData(charSequence));
        if (result.isValid()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        String.join(",", validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }

}
