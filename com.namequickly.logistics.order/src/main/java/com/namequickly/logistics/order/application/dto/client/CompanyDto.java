package com.namequickly.logistics.order.application.dto.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private UUID companyId;
    private UUID hubId;
    private String companyName;
    private String type;
    private String address;
    private String phone;

}
