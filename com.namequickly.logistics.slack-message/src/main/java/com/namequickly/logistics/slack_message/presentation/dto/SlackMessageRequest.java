package com.namequickly.logistics.slack_message.presentation.dto;


public record SlackMessageRequest(
    String slackId,
    String title,
    String content
) {
}
