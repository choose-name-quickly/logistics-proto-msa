package com.namequickly.logistics.order.application.dto.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {

    private UUID affiliationId;
    private String role;
}
