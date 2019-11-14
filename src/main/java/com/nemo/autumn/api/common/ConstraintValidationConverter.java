package com.nemo.autumn.api.common;

import com.nemo.autumn.api.common.model.ValidationItem;
import com.nemo.autumn.api.common.model.ValidationResult;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ConstraintValidationConverter {

    public static <T> ValidationResult convert(
            Set<ConstraintViolation<T>> violations) {
        Map<String, List<String>> listMap = new LinkedHashMap<>();
        for (ConstraintViolation<T> violation : violations) {
            for (Path.Node node : violation.getPropertyPath()) {
                List<String> list = listMap.computeIfAbsent(node.getName(),
                        k -> new ArrayList<>());
                list.add(violation.getMessage());
            }
        }
        return createValidationResult(listMap);
    }

    private static ValidationResult createValidationResult(
            Map<String, List<String>> listMap) {
        List<ValidationItem> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : Collections.unmodifiableMap(
                listMap).entrySet()) {
            list.add(new ValidationItem(entry.getKey(), entry.getValue()));
        }
        return new ValidationResult(list);
    }

}