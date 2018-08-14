package io.github.cyzest.commons.spring.model;

/**
 * 
 * 기본 익셉션 구현체
 *
 */
@SuppressWarnings("serial")
public class BasedException extends Exception {
    
    private ExceptionTypeable exceptionType;

    private Object exceptionData = null;

    public BasedException(ExceptionTypeable exceptionType) {
        super(exceptionType.getResultMessage());
        this.exceptionType = exceptionType;
    }

    public BasedException(ExceptionTypeable exceptionType, Object exceptionData) {
        super(exceptionType.getResultMessage());
        this.exceptionType = exceptionType;
        this.exceptionData = exceptionData;
    }

    public BasedException(ExceptionTypeable exceptionType, Throwable e) {
        super(exceptionType.getResultMessage(), e);
        this.exceptionType = exceptionType;
    }

    public BasedException(ExceptionTypeable exceptionType, Object exceptionData, Throwable e) {
        super(exceptionType.getResultMessage(), e);
        this.exceptionType = exceptionType;
        this.exceptionData = exceptionData;
    }

    public ExceptionTypeable getExceptionType() {
        return exceptionType;
    }

    public Object getExceptionData() {
        return exceptionData;
    }
    
}
