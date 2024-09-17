package com.namequickly.logistics.product_company.application.dto.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HubDto {

    private UUID hubId;
    private String hubName;
    private String hubAddress;
}
