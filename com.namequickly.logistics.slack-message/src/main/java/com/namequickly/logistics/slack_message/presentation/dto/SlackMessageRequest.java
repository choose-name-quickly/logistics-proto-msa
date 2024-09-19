package com.namequickly.logistics.slack_message.presentation.dto;


public record SlackMessageRequest(
        String channel,
        String slackId,
        String content
) {
}
