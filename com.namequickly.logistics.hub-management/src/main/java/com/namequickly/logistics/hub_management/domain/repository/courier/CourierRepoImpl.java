package com.namequickly.logistics.hub_management.domain.repository.courier;

import com.namequickly.logistics.hub_management.application.dto.CourierListResponse;
import com.namequickly.logistics.hub_management.domain.model.courier.Courier;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierSearch;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.namequickly.logistics.hub_management.domain.model.courier.QCourier.courier;

public class CourierRepoImpl implements CourierRepoCustom {

    private final JPAQueryFactory queryFactory;

    public CourierRepoImpl(JPAQueryFactory queryFactory) {

        this.queryFactory = queryFactory;
    }

    @Override
    public Page<CourierListResponse> searchCouriers(CourierSearch search, Pageable pageable) {

        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<Courier> results = queryFactory
                .selectFrom(courier)
                .where(
                        hubIdMatches(search.hubId()),
                        courierIdMatches(search.courierId()),
                        nameContains(search.name()),
                        statusMatches(search.status())
                )
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CourierListResponse> content = results.getResults().stream()
                .map(CourierListResponse::toResponse)
                .collect(Collectors.toList());

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression hubIdMatches(UUID hubId) {
        return hubId != null ? courier.hubId.eq(hubId) : null;
    }

    private BooleanExpression courierIdMatches(UUID courierId) {
        return courierId != null ? courier.courierId.eq(courierId) : null;
    }

    // searchDto에서 이름을 검색하지 않으면 따로 NULL로 반환해 무시하고 검색조건에서 빼버린다.
    private BooleanExpression nameContains(String name) {
        return name != null ? courier.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression statusMatches(CourierStatus status) {
        return status != null ? courier.status.eq(status) : null;
    }

    // 결과 순서
    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt" :
                        orders.add(new OrderSpecifier<>(direction, courier.createdAt));
                        break;
                    case "hubId" :
                        orders.add(new OrderSpecifier<>(direction, courier.hubId));
                        break;
                    case "courierId" :
                        orders.add(new OrderSpecifier<>(direction, courier.courierId));
                        break;
                    case "name" :
                        orders.add(new OrderSpecifier<>(direction, courier.name));
                        break;
                    case "status" :
                        orders.add(new OrderSpecifier<>(direction, courier.status));
                        break;
                }
            }
        }
        return orders;
    }
}