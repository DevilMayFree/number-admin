package com.freeying.common.webmvc.validation.constraintvalidators;

import com.freeying.common.webmvc.validation.constraints.ValidIdList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * ValidLongValidator
 * 字符串传入的必须为数值  可为空
 *
 * @author fx
 */
public class ValidIdListValidator implements ConstraintValidator<ValidIdList, List<String>> {

    private boolean enableEmpty;

    @Override
    public void initialize(ValidIdList annotation) {
        ConstraintValidator.super.initialize(annotation);
        this.enableEmpty = annotation.enableEmpty();
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return enableEmpty;
        }
        try {
            value.forEach(Long::valueOf);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
