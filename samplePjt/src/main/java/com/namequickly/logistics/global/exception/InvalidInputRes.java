package com.namequickly.logistics.global.exception;

public record InvalidInputRes(
    String field,
    String message
) {

}