package io.github.cyzest.commons.spring.model;

import org.springframework.http.HttpStatus;

/**
 * 익셉션 타입 인터페이스
 */
public interface ExceptionTypeable {

    int getResultCode();
    String getResultMessage();
    HttpStatus getStatusCode();

}
