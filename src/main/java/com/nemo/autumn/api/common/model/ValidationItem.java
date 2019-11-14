package com.nemo.autumn.api.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "validationItem")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ValidationItem {

    private String field;

    private List<String> messages;

    public ValidationItem() {
    }

    public ValidationItem(String field, List<String> messages) {
        this.field = field;
        this.messages = messages;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

}