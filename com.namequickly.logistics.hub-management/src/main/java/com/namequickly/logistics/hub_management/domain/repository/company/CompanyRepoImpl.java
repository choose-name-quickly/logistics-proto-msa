package com.namequickly.logistics.hub_management.domain.repository.company;

import com.namequickly.logistics.hub_management.application.dto.CompanyListResponse;
import com.namequickly.logistics.hub_management.domain.model.company.Company;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanySearch;
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

import static com.namequickly.logistics.hub_management.domain.model.company.QCompany.company;

public class CompanyRepoImpl implements CompanyRepoCustom {

    private final JPAQueryFactory queryFactory;

    public CompanyRepoImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<CompanyListResponse> searchCompanies(CompanySearch search, Pageable pageable) {

        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<Company> results = queryFactory
                .selectFrom(company)
                .where(
                        hubIdMatches(search.hubId()),
                        companyIdMatches(search.companyId()),
                        nameContains(search.name())
                )
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CompanyListResponse> content = results.getResults().stream()
                .map(CompanyListResponse::toResponse)
                .collect(Collectors.toList());

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression hubIdMatches(UUID hubId) {
        return hubId != null ? company.hubId.eq(hubId) : null;
    }

    private BooleanExpression companyIdMatches(UUID companyId) {
        return companyId != null ? company.companyId.eq(companyId) : null;
    }

    private BooleanExpression nameContains(String name) {
        return name != null ? company.name.containsIgnoreCase(name) : null;
    }

    // 결과 순서
    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt" :
                        orders.add(new OrderSpecifier<>(direction, company.createdAt));
                        break;
                    case "hubId":
                        orders.add(new OrderSpecifier<>(direction, company.hubId));
                        break;
                    case "companyId" :
                        orders.add(new OrderSpecifier<>(direction, company.companyId));
                        break;
                    case "name" :
                        orders.add(new OrderSpecifier<>(direction, company.name));
                        break;
                }
            }
        }
        return orders;
    }
}
