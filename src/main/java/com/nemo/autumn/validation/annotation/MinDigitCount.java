package com.nemo.autumn.validation.annotation;

import com.nemo.autumn.validation.validator.MinDigitCountValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MinDigitCountValidator.class})
public @interface MinDigitCount {

    int value() default 1;

    String digits() default "0123456789";

    String message() default "String should contain digits";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
