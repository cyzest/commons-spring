package com.cyzest.commons.spring.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * HTTP 유틸
 */
public class HttpUtils {

    /**
     * Request에서 해당하는 헤더의 값을 반환한다.
     *
     * @param request
     * @param headerName
     * @return 헤더이름의 헤더값
     * @throws Exception
     */
    public static String getHeaderValue(HttpServletRequest request, String headerName) throws Exception {

        String headerValue = null;

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String nextHeaderName = headerNames.nextElement();
                nextHeaderName = nextHeaderName.toLowerCase();
                headerName = headerName.toLowerCase();
                if (nextHeaderName.equals(headerName)) {
                    headerValue = request.getHeader(headerName);
                    break;
                }
            }
        }

        return headerValue;
    }

    /**
     * 파일 다운로드 관련 Response 헤더를 세팅한다.
     *
     * @param request
     * @param response
     * @param fileName
     * @param contentType
     * @param contentsLength
     * @throws Exception
     */
    public static void setResponseHeaderForDownload(
            HttpServletRequest request, HttpServletResponse response, String fileName, String contentType, long contentsLength) throws Exception {

        if(request.getHeader("User-Agent") != null){

            response.reset();
            response.setContentType(contentType);

            String header = request.getHeader("User-Agent");

            if(header.contains("MSIE 5.5")){
                response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(fileName, "UTF-8") + ";");
            }else if(header.contains("MSIE")){
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ";");
            }else{
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8")) + ";");
            }

            response.setHeader("Content-Length", "" + contentsLength);
        }
    }

    /**
     * Json ContentType 헤더가 세팅된 HttpHeaders를 반환한다.
     *
     * @return HttpHeaders
     */
    public static HttpHeaders getJsonContentsTypeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    /**
     * Request에서 RemoteAddr 정보를 반환한다. (Proxy 환경 대응)
     *
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = null;
        if(request != null){
            remoteAddr = request.getHeader("X-Forwarded-For");
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("Proxy-Client-IP");
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("WL-Proxy-Client-IP");
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("HTTP_CLIENT_IP");
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("X-Real-IP");
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("X-RealIP");
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

}
