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
@Table(name = "p_message")
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SlackMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID messageId;

    @Column(nullable = false)
    private String channel;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false)
    private String content;

    private Timestamp sendAt;

    @Column(updatable = false)
    @CreatedDate
    private Timestamp createdAt;
    @Column(nullable = false)
    private String createdBy;

    @Column
    @LastModifiedDate
    private Timestamp updatedAt;
    private String updatedBy;

    private Timestamp deletedAt;
    private String deletedBy;
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
    public static SlackMessage create(SlackMessageRequest request, String username){
        return SlackMessage.builder()
                .channel(request.channel())
                .slackId(request.slackId())
                .content(request.content())
                .createdBy(username)
               .build();
    }

    public void update(SlackMessageRequest request, String username) {
        this.channel = request.channel();
        this.slackId = request.slackId();
        this.content = request.content();
        this.updatedBy = username;
    }

    public void delete(String username) {
        this.deletedBy = username;
        this.deletedAt = Timestamp.valueOf(LocalDateTime.now());
        this.isDelete = true;
    }

    public void send() {
        this.sendAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
