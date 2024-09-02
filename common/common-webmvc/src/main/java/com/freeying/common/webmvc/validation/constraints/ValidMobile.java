package com.freeying.common.webmvc.validation.constraints;

import com.freeying.common.webmvc.validation.constraintvalidators.ValidMobileValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be ValidMobile
 *
 * @author fx
 */
@Documented
@Constraint(validatedBy = {ValidMobileValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(ValidMobile.List.class)
public @interface ValidMobile {

    /**
     * enableEmpty
     *
     * @return boolean
     */
    boolean enableEmpty() default false;

    String message() default "{jakarta.validation.constraints.ValidMobile.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @ValidMobile} constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidMobile[] value();
    }
}