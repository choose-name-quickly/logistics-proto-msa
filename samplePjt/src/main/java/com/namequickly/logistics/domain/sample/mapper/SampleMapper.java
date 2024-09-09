package com.namequickly.logistics.domain.sample.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.domain.sample.domain.Sample;
import com.namequickly.logistics.domain.sample.dto.response.CreateSampleRes;
import com.namequickly.logistics.domain.sample.dto.response.GetSampleRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface SampleMapper {

    @Mapping(target = "money", source = "money.amount")
    GetSampleRes toGetSampleRes(Sample sample);

    @Mapping(target = "money", source = "money.amount")
    CreateSampleRes toCreateSampleRes(Sample sample);
}
