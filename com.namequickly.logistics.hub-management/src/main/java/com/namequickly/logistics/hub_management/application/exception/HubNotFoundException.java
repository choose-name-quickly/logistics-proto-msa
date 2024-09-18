package com.namequickly.logistics.hub_management.application.exception;

// 허브 아이디 찾을 수 없을 경우 예외 처리
public class HubNotFoundException extends RuntimeException {

    public HubNotFoundException(String message) {
        super(message);
    }
}