package com.nemo.autumn.validation.validator;

import com.nemo.autumn.validation.annotation.MinDigitCount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinDigitCountValidator
        implements ConstraintValidator<MinDigitCount, String> {

    private String digits;
    private int count;

    @Override
    public void initialize(MinDigitCount constraintAnnotation) {
        this.digits = constraintAnnotation.digits();
        this.count = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        int countDigits = 0;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (digits.indexOf(ch) != -1) {
                countDigits++;
            }
        }
        return countDigits >= count;
    }

}
