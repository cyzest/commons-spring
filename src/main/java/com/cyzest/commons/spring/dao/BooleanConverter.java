package com.cyzest.commons.spring.dao;

import com.cyzest.commons.spring.model.BooleanFlag;

import javax.persistence.Converter;

/**
 * JPA BooleanFlag Converter
 */
@Converter(autoApply = true)
public class BooleanConverter extends EnumTypeConverter<BooleanFlag> {

    public BooleanConverter() {
        super(BooleanFlag.class);
    }

}
