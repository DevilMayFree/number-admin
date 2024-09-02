package com.freeying.common.webmvc.validation.constraints;

import com.freeying.common.webmvc.validation.constraintvalidators.ValidIdListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be ValidLong
 *
 * @author fx
 */
@Documented
@Constraint(validatedBy = {ValidIdListValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(ValidIdList.List.class)
public @interface ValidIdList {

    /**
     * enableEmpty
     *
     * @return boolean
     */
    boolean enableEmpty() default false;

    String message() default "{jakarta.validation.constraints.ValidLong.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @ValidEmail} constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidIdList[] value();
    }
}