package io.github.cyzest.commons.spring.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * 커스텀 RestTemplate 리스폰스 에러 핸들러
 *
 * RestTemplate 구현체를 생성 시 기본적으로 DefaultResponseErrorHandler를 사용한다.
 * DefaultResponseErrorHandler는 기본적으로 400번대와 500번대 statusCode에 대해서 에러 헨들링을 강제하고 있다.
 * 설정된 번호대의 statusCode를 제외한 나머지 statusCode는 로직레벨에서 처리를 원할 경우 해당 구현체로 교체한다.
 * 기본 설정값은 500번대 이다.
 */
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

    private final Set<HttpStatus.Series> handlingSeriesSet;

    public CustomResponseErrorHandler() {
        this.handlingSeriesSet = new HashSet<>();
        this.handlingSeriesSet.add(HttpStatus.Series.SERVER_ERROR);
    }

    public CustomResponseErrorHandler(Set<HttpStatus.Series> handlingSeriesSet) {
        if (handlingSeriesSet == null) {
            throw new IllegalArgumentException("handlingSeriesSet is not be null");
        }
        this.handlingSeriesSet = handlingSeriesSet;
    }

    @Override
    protected boolean hasError(HttpStatus statusCode) {
        return handlingSeriesSet.contains(statusCode.series());
    }

}
