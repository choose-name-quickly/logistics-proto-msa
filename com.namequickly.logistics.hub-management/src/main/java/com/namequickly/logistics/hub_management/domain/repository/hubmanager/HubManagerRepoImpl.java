package com.namequickly.logistics.hub_management.domain.repository.hubmanager;

import com.namequickly.logistics.hub_management.application.dto.HubManagerResponse;
import com.namequickly.logistics.hub_management.domain.model.hubmanager.HubManager;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerSearch;
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

import static com.namequickly.logistics.hub_management.domain.model.hubmanager.QHubManager.hubManager;

public class HubManagerRepoImpl implements HubManagerRepoCustom {

    private final JPAQueryFactory queryFactory;

    public HubManagerRepoImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<HubManagerResponse> searchHubManagers(HubManagerSearch search, Pageable pageable) {

        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<HubManager> results = queryFactory
                .selectFrom(hubManager)
                .where(
                        hubIdMatches(search.hubId()),
                        managerIdMatches(search.managerId()),
                        nameContains(search.name())
                )
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                // 반드시 DB 한번에 전체 조회하면 안됨
                // 서버 다운될 수 있음
                .limit(pageable.getPageSize())
                .fetchResults();

        List<HubManagerResponse> content = results.getResults().stream()
                .map(HubManagerResponse::toResponse)
                .collect(Collectors.toList());

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression hubIdMatches(UUID hubId) {
        return hubId != null ? hubManager.hubId.eq(hubId) : null;
    }

    private BooleanExpression managerIdMatches(UUID managerId) {
        return managerId != null ? hubManager.managerId.eq(managerId) : null;
    }

    // searchDto에서 이름을 검색하지 않으면 따로 NULL로 반환해 무시하고 검색조건에서 빼버린다.
    private BooleanExpression nameContains(String name) {
        return name != null ? hubManager.name.containsIgnoreCase(name) : null;
    }

    // 결과 순서
    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt" :
                        orders.add(new OrderSpecifier<>(direction, hubManager.createdAt));
                        break;
                    case "hubId" :
                        orders.add(new OrderSpecifier<>(direction, hubManager.hubId));
                        break;
                    case "managerId" :
                        orders.add(new OrderSpecifier<>(direction, hubManager.managerId));
                        break;
                    case "name" :
                        orders.add(new OrderSpecifier<>(direction, hubManager.name));
                        break;
                }
            }
        }
        return orders;
    }
}
