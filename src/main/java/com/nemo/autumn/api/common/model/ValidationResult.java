package com.nemo.autumn.api.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "validationResult")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ValidationResult extends ErrorMessage {

    private List<ValidationItem> items;

    public ValidationResult() {
        super("Validation error");
    }

    public ValidationResult(List<ValidationItem> items) {
        this();
        this.items = items;
    }

    public List<ValidationItem> getItems() {
        return items;
    }

    public void setItems(List<ValidationItem> items) {
        this.items = items;
    }

}