package com.namequickly.logistics.order.global.exception;

public record InvalidInputRes(
    String field,
    String message
) {

}