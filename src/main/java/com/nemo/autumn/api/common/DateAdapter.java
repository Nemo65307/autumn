package com.nemo.autumn.api.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.ValidationException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class DateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    @Override
    public String marshal(Date v) {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }

    @Override
    public Date unmarshal(String v) throws ValidationException {
        synchronized (dateFormat) {
            try {
                return dateFormat.parse(v);
            } catch (ParseException e) {
                throw new ValidationException("Couldn't parse the date!");
            }
        }
    }

}