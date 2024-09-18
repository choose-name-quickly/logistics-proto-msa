package com.namequickly.logistics.auth.presentation.FeignController;

import com.namequickly.logistics.auth.application.service.AuthService;
import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.response.ResultCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authFeign")
@RequiredArgsConstructor
@Slf4j(topic = "AuthFeignController")
public class AuthFeignController {

    private final AuthService authService;

    /**
     * 실제 유저의 슬랙 아이디인지 체크
     */
    @GetMapping("/{slackId}")
    public CommonResponse<String> checkUserExistsBySlackId(@RequestParam String slackId) {
        if (!authService.checkUserExistsbySlackId(slackId)) {
            throw new GlobalException(ResultCase.USER_NOT_FOUND);
        }

        return CommonResponse.success("존재하는 유저입니다");
    }
}
