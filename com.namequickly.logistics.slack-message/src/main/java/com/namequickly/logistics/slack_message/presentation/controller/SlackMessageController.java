package com.namequickly.logistics.slack_message.presentation.controller;

import com.namequickly.logistics.slack_message.application.dto.SlackMessageListResponse;
import com.namequickly.logistics.slack_message.application.dto.SlackMessageResponse;
import com.namequickly.logistics.slack_message.application.service.SlackMessageService;
import com.namequickly.logistics.slack_message.application.service.SlackService;
import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageRequest;
import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageSearch;
import com.slack.api.methods.SlackApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class SlackMessageController {

    private final SlackMessageService slackMessageService;


    public SlackMessageController(SlackMessageService slackMessageService) {
        this.slackMessageService = slackMessageService;
    }

    // 메세지 생성
    @PostMapping
    public ResponseEntity<SlackMessageResponse> createMessage(
            @RequestBody final SlackMessageRequest request,
            @RequestHeader(value = "X-User-Name") String username) {

        return ResponseEntity.ok(slackMessageService.createMessage(request, username));
    }


    // TODO: 본인이 생성한 메세지인지  체크
    // 메세지 수정
    @PatchMapping("/{messageId}")
    public ResponseEntity<SlackMessageResponse> updateMessage(
            @PathVariable final UUID messageId,
            @RequestBody final SlackMessageRequest request,
            @RequestHeader(value = "X-User-Name") String username) {

        return ResponseEntity.ok(slackMessageService.updateMessage(messageId, request, username));
    }

    // TODO: 권한 체크 + ResponseEntity<Void>?
    // 메세지 삭제
    @DeleteMapping("/{messageId}")
    public void deleteMessage(
            @PathVariable UUID messageId,
            @RequestHeader(value = "X-User-Name") String username) {

        slackMessageService.deleteMessage(messageId, username);
    }


    // 메세지 전체 조회
    @GetMapping
    public Page<SlackMessageListResponse> getAllMessages(@RequestParam(required = false) String slackId,
                                                         @RequestParam(required = false) String content,
                                                         @RequestParam(required = false)Timestamp sendAt,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestHeader(value = "X-User-Name") String username) {

        SlackMessageSearch search = new SlackMessageSearch(slackId, content, sendAt);
        Pageable pageable = PageRequest.of(page, size);
        return slackMessageService.getMessages(search, pageable, username);
    }

    // 메세지 상세 조회
    @GetMapping("/{messageId}")
    public ResponseEntity<SlackMessageResponse> getMessage(@PathVariable final UUID messageId,
                                                           @RequestHeader(value = "X-User-Name") String username) {
        return ResponseEntity.ok(slackMessageService.getMessageById(messageId, username));
    }

    // 메세지 발송
    @GetMapping("/{messageId}/send")
    public void sendMessage(@PathVariable final UUID messageId,
            @RequestHeader(value = "X-User-Name") String username) throws SlackApiException, IOException {

        slackMessageService.sendMessage(messageId, username);
    }
}
