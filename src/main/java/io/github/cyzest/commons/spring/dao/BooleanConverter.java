package io.github.cyzest.commons.spring.dao;

import io.github.cyzest.commons.spring.model.BooleanFlag;

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
