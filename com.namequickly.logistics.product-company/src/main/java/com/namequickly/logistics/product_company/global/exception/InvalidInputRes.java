package com.namequickly.logistics.product_company.global.exception;

public record InvalidInputRes(
    String field,
    String message
) {

}