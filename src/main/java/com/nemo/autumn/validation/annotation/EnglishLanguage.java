package com.nemo.autumn.validation.annotation;

import com.nemo.autumn.validation.validator.EnglishLanguageValidator;

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

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { EnglishLanguageValidator.class })
public @interface EnglishLanguage {

    String message() default "Only english alphabet";

    // 0123456789
    boolean withNumbers() default true;

    //.,?!-:()'"[]{}; \t\n
    boolean withPunctuations() default true;

    //~#$%^&*-+=_\\|/@`!'\";:><,.?{}
    boolean withSpecSymbols() default true;

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

}
