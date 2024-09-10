package com.namequickly.logistics.product_company.global.exception;

import com.namequickly.logistics.product_company.global.common.response.ResultCase;
import lombok.Getter;

/**
 * 전역 예외 처리를 위한 커스텀 예외 클래스 - ResultCase 받아서 처리
 */
@Getter
public class GlobalException extends RuntimeException {

    private final ResultCase resultCase;

    public GlobalException(ResultCase resultCase) {
        super(resultCase.getMessage());
        this.resultCase = resultCase;
    }
}
