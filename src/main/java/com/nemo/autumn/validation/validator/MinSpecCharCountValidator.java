package com.nemo.autumn.validation.validator;

import com.nemo.autumn.validation.annotation.MinSpecCharCount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinSpecCharCountValidator
        implements ConstraintValidator<MinSpecCharCount, String> {

    private String symbols;

    private int count;

    @Override
    public void initialize(MinSpecCharCount constraintAnnotation) {
        this.count = constraintAnnotation.value();
        this.symbols = constraintAnnotation.specSymbols();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        int countSymbols = 0;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (symbols.indexOf(ch) != -1) {
                countSymbols++;
            }
        }
        return countSymbols >= count;
    }

}
