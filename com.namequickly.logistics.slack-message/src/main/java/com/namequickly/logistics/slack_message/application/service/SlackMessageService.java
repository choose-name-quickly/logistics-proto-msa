package com.namequickly.logistics.slack_message.application.service;

import com.namequickly.logistics.slack_message.application.mapper.SlackMessageMapper;
import com.namequickly.logistics.slack_message.application.dto.SlackMessageListResponse;
import com.namequickly.logistics.slack_message.application.dto.SlackMessageResponse;
import com.namequickly.logistics.slack_message.domain.model.SlackMessage;
import com.namequickly.logistics.slack_message.domain.repository.SlackMessageRepo;
import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageRequest;
import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageSearch;
import com.slack.api.methods.SlackApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
public class SlackMessageService {

    private final SlackMessageRepo slackMessageRepo;
    private final SlackService slackService;
    private final SlackMessageMapper slackMessageMapper;

    public SlackMessageService(SlackMessageRepo slackMessageRepo, SlackService slackService, SlackMessageMapper slackMessageMapper) {
        this.slackMessageRepo = slackMessageRepo;
        this.slackService = slackService;
        this.slackMessageMapper = slackMessageMapper;
    }


    // 메세지 생성
    @Transactional
    public SlackMessageResponse createMessage(SlackMessageRequest request, String username) {

            SlackMessage message = SlackMessage.create(request, username);
            slackMessageRepo.save(message);
            return slackMessageMapper.toResponse(message);
    }

    // 메세지 수정
    @Transactional
    public SlackMessageResponse updateMessage(UUID messageId, SlackMessageRequest request, String username) {
        SlackMessage message = slackMessageRepo.findById(messageId)
                .filter(p -> !p.getIsDelete()) // 삭제가 안된 것만
                .filter(p -> p.getCreatedBy().equals(username)) //본인이 작성한 메세지만
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        message.update(request, username);
        return slackMessageMapper.toResponse(message);
    }

    // 메세지 삭제
    @Transactional
    public void deleteMessage(UUID messageId, String username) {
        SlackMessage message = slackMessageRepo.findById(messageId)
                .filter(p -> !p.getIsDelete())
                .filter(p -> p.getCreatedBy().equals(username))  //본인이 작성한 메세지만
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        message.delete(username);
    }

    // 메세지 전체 조회
    public Page<SlackMessageListResponse> getMessages(SlackMessageSearch search, Pageable pageable,String username) {
        return slackMessageRepo.searchMessages(search, pageable, username);
    }

    // 메세지 상세 조회
    @Transactional(readOnly = true)
    public SlackMessageResponse getMessageById(UUID messageId, String username) {
        SlackMessage message = slackMessageRepo.findById(messageId)
                .filter(p -> !p.getIsDelete())
                .filter(p -> p.getCreatedBy().equals(username)) //본인이 작성한 메세지만
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));
        return slackMessageMapper.toResponse(message);
    }

    // 메세지 발송
    public void sendMessage(UUID messageId, String username) throws SlackApiException, IOException {
        SlackMessage message = slackMessageRepo.findById(messageId)
                .filter(p -> !p.getIsDelete())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        if(message.getCreatedBy().equals(username)) {
            slackService.sendSlackMessage(message.getChannel(), message.getSlackId(), message.getContent());
            message.send();
        } else  new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }
}