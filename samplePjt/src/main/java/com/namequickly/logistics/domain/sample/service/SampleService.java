package com.namequickly.logistics.domain.sample.service;

import com.namequickly.logistics.domain.model.Money;
import com.namequickly.logistics.domain.sample.domain.Sample;
import com.namequickly.logistics.domain.sample.dto.request.CreateSampleReq;
import com.namequickly.logistics.domain.sample.dto.response.CreateSampleRes;
import com.namequickly.logistics.domain.sample.dto.response.GetSampleRes;
import com.namequickly.logistics.domain.sample.mapper.SampleMapper;
import com.namequickly.logistics.domain.sample.repository.SampleRepository;
import com.namequickly.logistics.global.common.response.ResultCase;
import com.namequickly.logistics.global.exception.GlobalException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleMapper mapper;
    private final SampleRepository sampleRepository;

    /**
     * 샘플 단건 조회
     */
    @Transactional(readOnly = true)
    public GetSampleRes getSample(UUID id) {

        Sample sample = sampleRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));

        return mapper.toGetSampleRes(sample);
    }

    /**
     * 샘플 리스트 조회
     * TODO : 페이징 처리를 List, Slice, Page 중 어떤 것을 사용할지 고민
     */
    @Transactional(readOnly = true)
    public Slice<GetSampleRes> getSampleList(Pageable pageable) {

        return sampleRepository.findAll(pageable)
            .map(mapper::toGetSampleRes);
    }

    /**
     * 샘플 생성
     */
    @Transactional
    public CreateSampleRes createSample(CreateSampleReq request) {
        Sample sample = Sample.create(request.name(), new Money(request.money()));
        Sample savedSample = sampleRepository.save(sample);
        return mapper.toCreateSampleRes(savedSample);
    }
}
