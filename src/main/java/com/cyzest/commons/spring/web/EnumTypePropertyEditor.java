package com.cyzest.commons.spring.web;

import com.cyzest.commons.spring.model.EnumTypeable;

import java.beans.PropertyEditorSupport;

/**
 * EnumType PropertyEditor
 */
public class EnumTypePropertyEditor<T extends Enum<T> & EnumTypeable> extends PropertyEditorSupport {

    private final Class<T> typeParameterClass;

    public EnumTypePropertyEditor(Class<T> typeParameterClass) {
        super();
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {

        if (text == null) {
            throw new NullPointerException("text is null");
        }

        T[] constants = this.typeParameterClass.getEnumConstants();

        for (T constant : constants) {
            if (constant.getType().equals(text)) {
                setValue(constant);
                return;
            }
        }

        throw new IllegalArgumentException("No enum constant " + this.typeParameterClass.getCanonicalName() + "." + text);
    }
}
