package com.namequickly.logistics.hub.presentation.FeingController;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.hub.application.service.FindOptimalRouteHubListService;
import com.namequickly.logistics.hub.application.service.RouteHubService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routesFeign")
@RequiredArgsConstructor
@Slf4j(topic = "RouteFeignController")
public class RouteFeignController {

    private final RouteHubService routeHubService;
    private final FindOptimalRouteHubListService findOptimalRouteHubListService;

    /**
     * (구현중) 출발 허브와 도착 허브를 입력하면 최적 경로를 반환하는 로직
     */
    @GetMapping("/find-route")
    public CommonResponse<List<Map<UUID, UUID>>> findOptimalRoute(
        @RequestParam UUID originHubId,
        @RequestParam UUID destinationHubId
    ) {

        // 여기 최적화 경로 찾는 탐색 알고리즘 넣어야함
        List<Map<UUID, UUID>> routeHubAndCourierIds = findOptimalRouteHubListService.findRoute(originHubId, destinationHubId);
        return CommonResponse.success(routeHubAndCourierIds);
    }
}
