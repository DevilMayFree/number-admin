package com.freeying.common.webmvc.validation.constraints;

import com.freeying.common.webmvc.validation.constraintvalidators.ValidEmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be ValidEmail
 *
 * @author fx
 */
@Documented
@Constraint(validatedBy = {ValidEmailValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(ValidEmail.List.class)
public @interface ValidEmail {

    /**
     * enableEmpty
     *
     * @return boolean
     */
    boolean enableEmpty() default false;

    String message() default "{jakarta.validation.constraints.ValidEmail.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @ValidEmail} constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidEmail[] value();
    }
}