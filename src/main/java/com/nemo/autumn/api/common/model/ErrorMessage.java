package com.nemo.autumn.api.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "errorMessage")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ErrorMessage {

    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}