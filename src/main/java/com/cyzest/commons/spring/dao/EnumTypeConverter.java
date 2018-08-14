package com.cyzest.commons.spring.dao;

import com.cyzest.commons.spring.model.EnumTypeable;

import javax.persistence.AttributeConverter;

/**
 * JPA EnumType Converter
 */
public abstract class EnumTypeConverter<T extends Enum<T> & EnumTypeable> implements AttributeConverter<T, String> {

    private final Class<T> targetClass;

    public EnumTypeConverter(Class<T> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException();
        }
        this.targetClass = targetClass;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getType() : null;
    }

    @Override
    public T convertToEntityAttribute(String result) {
        T enumType = null;
        if (result != null && !result.isEmpty()) {
            T[] constants = targetClass.getEnumConstants();
            for (T constant : constants) {
                if (constant.getType().equals(result)) {
                    enumType = constant;
                    break;
                }
            }
        }
        return enumType;
    }

}
