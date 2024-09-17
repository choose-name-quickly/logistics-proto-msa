package com.namequickly.logistics.order.application.dto.client;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    private UUID companyId;
    private CompanyType type;
    private UUID hubId;
    private String name;
    private String address;
    private String phone;
    private Timestamp createdAt;
    private UUID createdBy;
    private Timestamp updatedAt;
    private UUID updatedBy;
    private Timestamp deletedAt;
    private UUID deletedBy;
    private Boolean isDelete;

}