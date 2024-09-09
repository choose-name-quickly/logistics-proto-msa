package com.namequickly.logistics.domain.sample.dto.response;

import java.util.UUID;

public record CreateSampleRes(
    UUID id,
    String name,
    Long money
) {

}
