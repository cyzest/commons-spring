package com.cyzest.commons.spring.validator;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileTypeConstraintValidator implements ConstraintValidator<FileTypeValidator, MultipartFile> {

    private FileTypeValidator fileTypeValidator;

    private Set<String> mimeTypeSet = null;

    private final Tika tika = new Tika();

    @Override
    public void initialize(FileTypeValidator constraintAnnotation) {

        this.fileTypeValidator = constraintAnnotation;

        String[] mimeTypes = fileTypeValidator.mimeTypes();

        if(mimeTypes.length > 0) {
            mimeTypeSet = new HashSet<>();
            mimeTypeSet.addAll(Arrays.asList(mimeTypes));
        }
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        boolean result = true;

        if (mimeTypeSet != null && file != null) {
            try (InputStream is = file.getInputStream()) {
                String mimeType = tika.detect(is);
                if (!mimeTypeSet.contains(mimeType)) {
                    result = false;
                }
            } catch (Throwable ex) {
                result = false;
            }
        }

        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fileTypeValidator.message())
                    .addBeanNode().addConstraintViolation();
        }

        return result;
    }

}
