package com.namequickly.logistics.ai.domain.model;


import com.namequickly.logistics.ai.presentation.dto.AIRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_ai", schema="ai")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class AI {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID aiId;

    private String slackId;
    private String receiveInfo;
    private String sendInfo;

    @Column(updatable = false)
    @CreatedDate
    private Timestamp createdAt;
    private UUID createdBy;

    @Column
    @LastModifiedDate
    private Timestamp updatedAt;
    private UUID updatedBy;

    private Timestamp deletedAt;
    private UUID deletedBy;

    @Builder.Default
    private Boolean isDelete = false;


    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = Timestamp.valueOf(LocalDateTime.now());}

    public static AI create(AIRequest requestDto, UUID userId) {
        return AI.builder()
                .slackId(requestDto.slackId())
                .receiveInfo(requestDto.receiveInfo())
                .sendInfo(requestDto.sendInfo())
                .build();
    }

    public void update(AIRequest requestDto, UUID userId) {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        this.updatedBy = userId;
        this.slackId = requestDto.slackId();
        this.receiveInfo = requestDto.receiveInfo();
        this.sendInfo = requestDto.sendInfo();
    }

    public void delete(UUID userId) {
        this.deletedAt = Timestamp.valueOf(LocalDateTime.now());
        this.deletedBy = userId;
        this.isDelete = true;
    }
}
