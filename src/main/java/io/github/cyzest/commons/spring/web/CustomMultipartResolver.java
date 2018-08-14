package io.github.cyzest.commons.spring.web;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * 커스텀 멀티파트 리졸버
 */
public class CustomMultipartResolver extends CommonsMultipartResolver {

    /**
     * PUT Method 에서도 Multipart 방식을 사용할 수 있게 변경
     */
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        return (request != null && FileUploadBase.isMultipartContent(new ServletRequestContext(request)));
    }
}
