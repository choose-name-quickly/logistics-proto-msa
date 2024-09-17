package com.namequickly.logistics.ai.application.dto;

import com.namequickly.logistics.ai.domain.model.AI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

// TODO : ResponseDto는 record타입으로 생성할 필요가 없는가? 데이터의 불변성?
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {

    private String slackId;
    private String receiveInfo;
    private String sendInfo;
    private Timestamp createdAt;
    private UUID createdBy;
    private Timestamp updatedAt;
    private UUID updatedBy;
    private Timestamp deletedAt;
    private UUID deletedBy;
    private Boolean isDelete;

    // 변환 메서드
    public static AIResponse toResponse(AI ai){
        return new AIResponse(
                ai.getSlackId(),
                ai.getReceiveInfo(),
                ai.getSendInfo(),
                ai.getCreatedAt(),
                ai.getCreatedBy(),
                ai.getUpdatedAt(),
                ai.getUpdatedBy(),
                ai.getDeletedAt(),
                ai.getDeletedBy(),
                ai.getIsDelete()
        );
    }
}
