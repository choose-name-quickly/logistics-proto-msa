package com.namequickly.logistics.common.exception;

public record InvalidInputRes(
    String field,
    String message
) {

}