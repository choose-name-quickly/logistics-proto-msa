package com.namequickly.logistics.slack_message.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlackMessageListResponse {

    private UUID messageId;
    private String channel;
    private String content;
    private Timestamp sendAt;
}
