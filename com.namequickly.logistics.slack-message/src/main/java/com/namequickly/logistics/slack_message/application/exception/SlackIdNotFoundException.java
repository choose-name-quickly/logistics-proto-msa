package com.namequickly.logistics.slack_message.application.exception;

public class SlackIdNotFoundException extends RuntimeException{

    public SlackIdNotFoundException(String message) {
        super(message);
    }
}
