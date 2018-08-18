package io.github.cyzest.commons.spring.web;

import io.github.cyzest.commons.spring.model.EnumCode;

import java.beans.PropertyEditorSupport;

/**
 * EnumCode PropertyEditor
 */
public class EnumCodePropertyEditor<T extends Enum<T> & EnumCode> extends PropertyEditorSupport {

    private final Class<T> codeParameterClass;

    public EnumCodePropertyEditor(Class<T> codeParameterClass) {
        super();
        this.codeParameterClass = codeParameterClass;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {

        if (text == null) {
            throw new NullPointerException("text is null");
        }

        T[] constants = codeParameterClass.getEnumConstants();

        for (T constant : constants) {
            if (constant.getCode().equals(text)) {
                setValue(constant);
                return;
            }
        }

        String canonicalName = codeParameterClass.getCanonicalName();

        throw new IllegalArgumentException("No enum constant " + canonicalName + "." + text);
    }
}
