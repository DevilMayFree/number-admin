package com.freeying.common.webmvc.validation.constraintvalidators;

import com.freeying.common.webmvc.validation.constraints.ValidEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * ValidEmailValidator
 *
 * @author fx
 */
public class ValidEnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;
    private boolean enableEmpty;
    private String enumMethod;

    @Override
    public void initialize(ValidEnum annotation) {
        enumClass = annotation.enumClass();
        enumMethod = annotation.enumMethod();
        enableEmpty = annotation.enableEmpty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (null == value) {
            return enableEmpty;
        }
        if (null == enumClass || null == enumMethod) {
            return false;
        }

        Class<?> valueClass = value.getClass();

        try {
            Method method = enumClass.getMethod(enumMethod, valueClass);
            if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                throw new ValidationException(String.format("%s method return is not boolean type in the %s class", enumMethod, enumClass));
            }

            if (!Modifier.isStatic(method.getModifiers())) {
                throw new ValidationException(String.format("%s method is not static method in the %s class", enumMethod, enumClass));
            }

            Boolean result = (Boolean) method.invoke(null, value);
            return result != null && result;
        } catch (NoSuchMethodException | SecurityException e) {
            throw new ValidationException(String.format("This %s(%s) method does not exist in the %s", enumMethod, valueClass, enumClass), e);
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
