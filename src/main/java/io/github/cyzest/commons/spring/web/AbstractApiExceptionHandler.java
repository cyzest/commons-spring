package io.github.cyzest.commons.spring.web;

import io.github.cyzest.commons.spring.model.BasedException;
import io.github.cyzest.commons.spring.model.ExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * API 익셉션 핸들러 추상 클래스
 */
public abstract class AbstractApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractApiExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse paramFieldValidExceptionHandler(BindException ex) {

        log.debug("ParamFieldValidException Handling : {}", ex.getMessage());

        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST);

        setInvalidGlobal(apiResponse, ex.getGlobalError());
        setInvalidFieldList(apiResponse, ex.getFieldErrors());

        return apiResponse;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        log.debug("MethodArgumentNotValidException Handling : {}", ex.getMessage());

        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST);

        setInvalidGlobal(apiResponse, ex.getBindingResult().getGlobalError());
        setInvalidFieldList(apiResponse, ex.getBindingResult().getFieldErrors());

        return apiResponse;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse methodArgumentNotValidExceptionHandler(MethodArgumentTypeMismatchException ex) {

        log.debug("MethodArgumentTypeMismatchException Handling : {}", ex.getMessage());

        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST);

        String requiredType = Optional.ofNullable(ex.getRequiredType())
                .map(ClassUtils::getShortName).orElse("undefined");

        String message = "Failed to convert value of required type [" + requiredType + "]";

        apiResponse.putExtra("invalidMessages", Collections.singletonList(
                new FieldErrorInfo(ex.getName(), ex.getErrorCode(), message)));

        return apiResponse;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse constraintViolationExceptionHandler(ConstraintViolationException ex) {

        Set<String> messages = null;

        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();

        if (constraintViolationSet != null && !constraintViolationSet.isEmpty()) {
            messages = new HashSet<>(constraintViolationSet.size());
            messages.addAll(
                    constraintViolationSet.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList())
            );
        }

        log.debug("ConstraintViolationException Handling : {}", messages);

        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST);

        apiResponse.putExtra("invalidMessages", messages);

        return apiResponse;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            HttpMediaTypeException.class
    })
    public ApiResponse badRequestExceptionHandler(Exception ex) {

        log.debug(ex.getClass().getSimpleName() + " Handling : {}", ex.getMessage());

        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST);

        apiResponse.setExtra(ex.getMessage());

        return apiResponse;
    }

    @ResponseBody
    @ExceptionHandler(BasedException.class)
    public ResponseEntity<ApiResponse> basedExceptionHandler(BasedException ex) {

        log.debug("BasedException Handling : {}", ex.getMessage());

        ExceptionType exceptionType = ex.getExceptionType();

        ApiResponse apiResponse = new ApiResponse(exceptionType.getResultCode(), exceptionType.getResultMessage());

        Object exceptionData = ex.getExceptionData();

        if (exceptionData != null) {
            apiResponse.setExtra(exceptionData);
        }

        return new ResponseEntity<>(apiResponse, exceptionType.getStatusCode());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ApiResponse errorExceptionHandler(Throwable ex) {

        log.error("Exception Handling...", ex);

        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void setInvalidGlobal(ApiResponse apiResponse, ObjectError objectError) {

        if (apiResponse != null && objectError != null) {

            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("validCode", objectError.getCode());
            resultMap.put("message", objectError.getDefaultMessage());

            apiResponse.putExtra("invalidGlobal", resultMap);
        }
    }

    private void setInvalidFieldList(ApiResponse apiResponse, List<FieldError> fieldErrorList) {

        if (apiResponse != null && fieldErrorList != null && !fieldErrorList.isEmpty()) {

            List<FieldErrorInfo> invalidFiledList = new ArrayList<>();

            fieldErrorList.forEach(fieldError -> invalidFiledList.add(
                    new FieldErrorInfo(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage())
            ));

            apiResponse.putExtra("invalidMessages", invalidFiledList);
        }
    }

}
