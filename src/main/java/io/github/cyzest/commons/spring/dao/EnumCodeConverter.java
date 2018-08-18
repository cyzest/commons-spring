package io.github.cyzest.commons.spring.dao;

import io.github.cyzest.commons.spring.model.EnumCode;

import javax.persistence.AttributeConverter;

/**
 * JPA EnumCode Converter
 */
public abstract class EnumCodeConverter<T extends Enum<T> & EnumCode> implements AttributeConverter<T, String> {

    private final Class<T> targetClass;

    public EnumCodeConverter(Class<T> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException();
        }
        this.targetClass = targetClass;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public T convertToEntityAttribute(String result) {
        T enumType = null;
        if (result != null && !result.isEmpty()) {
            T[] constants = targetClass.getEnumConstants();
            for (T constant : constants) {
                if (constant.getCode().equals(result)) {
                    enumType = constant;
                    break;
                }
            }
        }
        return enumType;
    }

}
