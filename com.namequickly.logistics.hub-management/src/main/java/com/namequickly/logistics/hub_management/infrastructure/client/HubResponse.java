package com.namequickly.logistics.hub_management.infrastructure.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubResponse {
    private UUID hubId;
    private String name;
}
