package com.namequickly.logistics.domain.sample.dto.response;

import java.util.UUID;

public record GetSampleRes(
    UUID id,
    String name,
    Long money
) {

}
