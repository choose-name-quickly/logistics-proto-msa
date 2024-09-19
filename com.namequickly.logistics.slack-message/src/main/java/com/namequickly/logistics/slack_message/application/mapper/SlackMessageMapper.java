package com.namequickly.logistics.slack_message.application.mapper;

import com.namequickly.logistics.slack_message.application.dto.SlackMessageListResponse;
import com.namequickly.logistics.slack_message.application.dto.SlackMessageResponse;
import com.namequickly.logistics.slack_message.domain.model.SlackMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SlackMessageMapper {

    SlackMessageResponse toResponse(SlackMessage slackMessage);

    SlackMessageListResponse toListResponse(SlackMessage slackMessage);
}
