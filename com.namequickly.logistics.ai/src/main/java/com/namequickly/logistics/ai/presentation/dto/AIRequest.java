package com.namequickly.logistics.ai.presentation.dto;

public record AIRequest(
        String slackId,
        String receiveInfo,
        String sendInfo
){
}
