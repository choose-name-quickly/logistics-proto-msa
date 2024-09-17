package com.namequickly.logistics.slack_message.domain.model;

import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_message", schema="slack_message")
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SlackMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID messageId;

    @Column(nullable = false)
    private String slackId;
    private String title;

    @Column(nullable = false)
    private String content;

    private Timestamp sendAt;

    @Column(updatable = false)
    @CreatedDate
    private Timestamp createdAt;
    @Column(nullable = false)
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

    // buildup패턴의 메서드만 static으로 사용하는 경우
    // static은 객체 생성 없이도 사용할 수 있음.
    public static SlackMessage create(SlackMessageRequest request, UUID userId){
        return SlackMessage.builder()
                .slackId(request.slackId())
                .title(request.title())
                .content(request.content())
                .createdBy(userId)
               .build();
    }

    public void update(String title, String content, UUID userId){
        this.slackId = slackId;
        this.title = title;
        this.content = content;
        this.updatedBy = userId;
    }

    public void delete(UUID userId) {
        this.deletedBy = userId;
        this.deletedAt = Timestamp.valueOf(LocalDateTime.now());
        this.isDelete = true;
    }

    public void send() {
        this.sendAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
