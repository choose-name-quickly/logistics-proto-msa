package com.namequickly.logistics.slack_message.infrastructure.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name="auth")
public interface AuthClient {

    // TODO: 실제 존재하는 slackID만 메세지 생성 가능
    @PostMapping("/api/auth/checkSlackId")
    boolean checkSlackId(@RequestParam String slackId);
}
