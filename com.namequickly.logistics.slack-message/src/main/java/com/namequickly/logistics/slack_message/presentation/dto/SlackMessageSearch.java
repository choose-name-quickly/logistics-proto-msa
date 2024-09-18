package com.namequickly.logistics.slack_message.presentation.dto;

import java.sql.Timestamp;

public record SlackMessageSearch(
        String slackId,
        String title,
        String content,
        Timestamp sendAt
) {
}
