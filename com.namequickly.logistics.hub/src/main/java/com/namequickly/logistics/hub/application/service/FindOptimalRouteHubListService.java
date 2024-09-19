package com.namequickly.logistics.hub.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.hub.application.dto.RouteHubResponseDto;
import com.namequickly.logistics.hub.application.mapper.RouteHubMapper;
import com.namequickly.logistics.hub.domain.model.Hub;
import com.namequickly.logistics.hub.domain.model.RouteHub;
import com.namequickly.logistics.hub.domain.repository.HubRepository;
import com.namequickly.logistics.hub.domain.repository.RouteHubRepository;
import com.namequickly.logistics.hub.infrastructure.client.HubClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "findOptimalRouteHubService")
public class FindOptimalRouteHubListService {

    private final RouteHubRepository routeHubRepository;
    private final HubRepository hubRepository;
    private final RouteHubMapper routeHubMapper;
    private final HubClient hubClient; // feignClient

    // TODO : 최적화 경로를 구현하는 서비스 (도전 기능)

    // 허브 간 이동 경로 순서
    private final List<String> hubOrder = Arrays.asList(
        "서울특별시 센터", "경기 북부 센터", "경기 남부 센터", "부산광역시 센터",
        "대구광역시 센터", "인천광역시 센터", "광주광역시 센터", "대전광역시 센터",
        "울산광역시 센터", "세종특별자치시 센터", "강원특별자치도 센터", "충청북도 센터",
        "충청남도 센터", "전북특별자치도 센터", "전라남도 센터", "경상북도 센터", "경상남도 센터"
    );

    /**
     * (Feign) 출발허브 ID, 도착허브 ID 에 매칭되는 <`RouteHubId, CourierId`> 리스트 생성
     */
    public List<Map<UUID, UUID>> findRoute(UUID startHubId, UUID destinationHubId) {
        Hub startHub = hubRepository.findById(startHubId)
            .orElseThrow(() -> new GlobalException(ResultCase.START_HUB_NOT_FOUND));
        Hub destinationHub = hubRepository.findById(destinationHubId)
            .orElseThrow(() -> new GlobalException(ResultCase.DESTINATION_HUB_NOT_FOUND));

        log.info("startHub, DestinationHub, {} {}", startHub.toString(), destinationHub.toString());

        // 출발 허브와 도착 허브의 인덱스를 허브 순서 리스트에서 찾기
        int startIndex = hubOrder.indexOf(startHub.getHubName());
        int endIndex = hubOrder.indexOf(destinationHub.getHubName());

        if (startIndex == -1 || endIndex == -1 || startIndex > endIndex) {
            throw new GlobalException(ResultCase.ROUTE_NOT_INVALID);
        }

        // 경유해야 할 허브들을 순차적으로 경로에 추가
        List<RouteHub> routeHubs = new ArrayList<>();
        for (int i=startIndex; i<endIndex; i++) {
            Hub currentHub = hubRepository.findByHubName(hubOrder.get(i))
                .orElseThrow(() -> new GlobalException(ResultCase.HUB_NOT_FOUND));
            Hub nextHub = hubRepository.findByHubName(hubOrder.get(i + 1))
                .orElseThrow(() -> new GlobalException(ResultCase.HUB_NOT_FOUND));

            final int orderInRoute = i - startIndex + 1;

            // 허브 간 경로 정보 추가
            RouteHub routeHub = routeHubRepository.findByHubIdAndNextHubId(currentHub.getHubId(), nextHub.getHubId())
                .orElseGet(() -> RouteHub.builder()
                    .routeId(UUID.randomUUID())
                    .hubId(currentHub.getHubId())
                    .nextHubId(nextHub.getHubId())
                    .orderInRoute(orderInRoute)
                    .distanceToNextHub(calculateDistance(currentHub, nextHub))
                    .timeToNextHub(calculateTime(currentHub, nextHub))
                    .isDelete(false)
                    .build());

            routeHubs.add(routeHub);
        }

        // routeHubs 저장
        List<RouteHub> savedRoutehubs = routeHubRepository.saveAll(routeHubs);
        log.info("routeHubs 저장결과 {}", savedRoutehubs);

        // routeHubsDto 로 변환후 routeHubId 만 추출
        List<RouteHubResponseDto> routeHubDtos = routeHubMapper.toDTOs(routeHubs);
        List<UUID> routeHubIds = routeHubDtos.stream()
            .map(RouteHubResponseDto::getRouteHubId)
            .collect(Collectors.toList());
        log.info("routeHubIds {}", routeHubIds);

        // Courier-service 에 List<RouteHubId> 를 보내서 List<MAP<RouteHubId,CourierId>> 를 받아 리턴
        List<Map<UUID, UUID>> result = hubClient.getHubIdAndCourierList(routeHubIds);
        log.info("routeHubIds ❤ courierIds {}", result);

        return result;
    }

    // 두 허브 간 거리를 계산하는 메서드 (위도와 경도를 사용하여 계산)
    private double calculateDistance(Hub currentHub, Hub nextHub) {
        double lat1 = Double.parseDouble(currentHub.getLocationLatitude());
        double lon1 = Double.parseDouble(currentHub.getLocationLongitude());
        double lat2 = Double.parseDouble(nextHub.getLocationLatitude());
        double lon2 = Double.parseDouble(nextHub.getLocationLongitude());
        return haversine(lat1, lon1, lat2, lon2);
    }

    // 허브 간 거리를 위도/경도를 사용하여 Haversine 공식을 통해 계산
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (단위: km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 두 지점 간의 거리 (단위: km)
    }

    // 허브 간 거리를 바탕으로 소요 시간을 계산 (임의의 속도를 설정하여 계산)
    private double calculateTime(Hub currentHub, Hub nextHub) {
        double distance = calculateDistance(currentHub, nextHub);
        double speed = 60.0; // 예: 60km/h 속도로 계산
        return distance / speed;
    }
}
