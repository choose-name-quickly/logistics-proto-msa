package com.namequickly.logistics.hub_management.domain.repository.hubmanager;

import com.namequickly.logistics.hub_management.application.dto.HubManagerResponse;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubManagerRepoCustom {

    Page<HubManagerResponse> searchHubManagers(HubManagerSearch search, Pageable pageable);
}
