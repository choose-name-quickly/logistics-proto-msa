package com.namequickly.logistics.slack_message.application.dto;

import com.namequickly.logistics.slack_message.domain.model.SlackMessage;
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
public class SlackMessageResponse {

    private UUID messageId;
    private String channel;
    private String slackId;
    private String content;
    private Timestamp sendAt;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;
    private Timestamp deletedAt;
    private String deletedBy;
    private Boolean isDelete;
}
