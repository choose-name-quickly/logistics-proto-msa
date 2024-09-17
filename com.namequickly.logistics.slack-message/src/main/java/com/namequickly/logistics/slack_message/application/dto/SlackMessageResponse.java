package com.namequickly.logistics.slack_message.application.dto;

import com.namequickly.logistics.slack_message.domain.model.SlackMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SlackMessageResponse {
    private UUID messageId;
    private String slackId;
    private String title;
    private String content;
    private Timestamp sendAt;
    private Timestamp createdAt;
    private UUID createdBy;
    private Timestamp updatedAt;
    private UUID updatedBy;
    private Timestamp deletedAt;
    private UUID deletedBy;
    private Boolean isDelete;

    // 변환 메서드
    public static SlackMessageResponse toResponse(SlackMessage message) {
        return new SlackMessageResponse(
                message.getMessageId(),
                message.getSlackId(),
                message.getTitle(),
                message.getContent(),
                message.getSendAt(),
                message.getCreatedAt(),
                message.getCreatedBy(),
                message.getUpdatedAt(),
                message.getUpdatedBy(),
                message.getDeletedAt(),
                message.getDeletedBy(),
                message.getIsDelete()
        );
    }
}
