package com.namequickly.logistics.slack_message.domain.repository;

import com.namequickly.logistics.slack_message.application.dto.SlackMessageResponse;
import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackMessageRepoCustom {

    Page<SlackMessageResponse> searchMessages(SlackMessageSearch search, Pageable pageable);
}
