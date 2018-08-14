package com.cyzest.commons.spring.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 파일 타입 Validator 어노테이션
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER})
@Constraint(validatedBy = FileTypeConstraintValidator.class)
public @interface FileTypeValidator {

    String message() default "not support file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] mimeTypes() default {};

    @Documented
    @Retention(RUNTIME)
    @Target({METHOD, FIELD, PARAMETER})
    @interface List {
        FileTypeValidator[] value();
    }

}
