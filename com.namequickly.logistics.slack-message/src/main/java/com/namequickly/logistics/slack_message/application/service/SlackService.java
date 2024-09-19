package com.namequickly.logistics.slack_message.application.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackService {

    @Value("${slack.token}")
    private String slackToken;

    private final Slack slack = Slack.getInstance();

    // TODO: 설정 방식이 헷갈림
    // 슬랙 메세지 전송
    public void sendSlackMessage(String channel, String slackId, String message) throws IOException, SlackApiException {

        String formattedMessage = String.format("*%s:* %s", slackId, message);

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                        .channel(channel)
                        .text(formattedMessage)
                        .build();

        ChatPostMessageResponse response = slack.methods(slackToken).chatPostMessage(request);

        if(!response.isOk()){
            throw new RuntimeException("Error sending Slack message" + response.getError());
        }
    }
}
