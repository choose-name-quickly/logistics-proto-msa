package com.namequickly.logistics.slack_message.domain.repository;

import com.namequickly.logistics.slack_message.application.dto.SlackMessageListResponse;
import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SlackMessageRepoCustom {

    Page<SlackMessageListResponse> searchMessages(SlackMessageSearch search, Pageable pageable, String username);
}
