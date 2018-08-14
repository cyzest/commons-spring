package io.github.cyzest.commons.spring.web;

/**
 *
 * 파라미터 필드 에러 정보
 *
 */
public class FieldErrorInfo {

    private String field = null;			// 필드명
    private String validCode = null;		// Validation 코드명
    private String message = null;			// Validation 메세지

    public FieldErrorInfo() {}

    public FieldErrorInfo(String field, String validCode, String message) {
        this.field = field;
        this.validCode = validCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "FieldErrorInfo{" +
                "field='" + field + '\'' +
                ", validCode='" + validCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
