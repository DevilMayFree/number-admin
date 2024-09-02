package com.freeying.common.webmvc.validation.constraints;

import com.freeying.common.webmvc.validation.constraintvalidators.ValidEnumValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be ValidEnum
 * 验证值是否包含在枚举类型中
 *
 * @author fx
 */
@Documented
@Constraint(validatedBy = {ValidEnumValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(ValidEnum.List.class)
public @interface ValidEnum {

    /**
     * enumClass
     */
    @SuppressWarnings("squid:S1452")
    Class<? extends Enum<?>> enumClass();

    /**
     * enableEmpty
     *
     * @return boolean
     */
    boolean enableEmpty() default false;

    /**
     * enumMethod
     */
    String enumMethod();

    String message() default "{jakarta.validation.constraints.ValidEnum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @ValidEmail} constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidEnum[] value();
    }
}