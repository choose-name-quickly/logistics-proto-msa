package com.namequickly.logistics.slack_message.presentation.controller;

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
            @RequestHeader(value = "X-User-Id") UUID userId) {

        return ResponseEntity.ok(slackMessageService.createMessage(request, userId));
    }


    // TODO: 본인이 생성한 메세지인지  체크
    // 메세지 수정
    @PatchMapping("/{messageId}")
    public ResponseEntity<SlackMessageResponse> updateMessage(
            @PathVariable final UUID messageId,
            @RequestBody final SlackMessageRequest request,
            @RequestHeader(value = "X-User-Id") UUID userId) {

        return ResponseEntity.ok(slackMessageService.updateMessage(messageId, request, userId));
    }

    // TODO: 권한 체크 + ResponseEntity<Void>?
    // 메세지 삭제
    @DeleteMapping("/{messageId}")
    public void deleteMessage(
            @PathVariable UUID messageId,
            @RequestHeader(value = "X-User-Id") UUID userId) {

        slackMessageService.deleteMessage(messageId, userId);
    }


    // 메세지 전체 조회
    @GetMapping
    public Page<SlackMessageResponse> getAllMessages(@RequestBody SlackMessageSearch search,
                                                     @RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestHeader(value = "X-User-Id") UUID userId) {
        Pageable pageable = PageRequest.of(page, size);
        return slackMessageService.getMessages(search, pageable);
    }

    // 메세지 상세 조회
    @GetMapping("/{messageId}")
    public ResponseEntity<SlackMessageResponse> getMessage(@PathVariable final UUID messageId,
                                                           @RequestHeader(value = "X-User-Id") UUID userId) {
        return ResponseEntity.ok(slackMessageService.getMessageById(messageId));
    }

    // 메세지 발송
    @GetMapping("/{messageId}/send")
    public void sendMessage(@PathVariable final UUID messageId,
            @RequestHeader(value = "X-User-Id") UUID userId) throws SlackApiException, IOException {

        slackMessageService.sendMessage(messageId);
    }
}
