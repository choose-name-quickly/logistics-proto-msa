package com.namequickly.logistics.hub_management.domain.repository.courier;

import com.namequickly.logistics.hub_management.application.dto.CourierResponse;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepoCustom {

    Page<CourierResponse> searchCouriers(CourierSearch search, Pageable pageable);
}
