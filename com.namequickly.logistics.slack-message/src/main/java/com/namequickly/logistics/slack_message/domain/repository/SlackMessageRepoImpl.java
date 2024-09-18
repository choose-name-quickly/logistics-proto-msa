package com.namequickly.logistics.slack_message.domain.repository;

import com.namequickly.logistics.slack_message.application.dto.SlackMessageResponse;
import com.namequickly.logistics.slack_message.domain.model.SlackMessage;
import com.namequickly.logistics.slack_message.presentation.dto.SlackMessageSearch;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.namequickly.logistics.slack_message.domain.model.QSlackMessage.slackMessage;

public class SlackMessageRepoImpl implements SlackMessageRepoCustom {

    private final JPAQueryFactory queryFactory;

    public SlackMessageRepoImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<SlackMessageResponse> searchMessages(SlackMessageSearch search, Pageable pageable) {

        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<SlackMessage> results = queryFactory
                .selectFrom(slackMessage)
                .where(
                        slackIdMatches(search.slackId()),
                        titleContains(search.title()),
                        contentContains(search.content()),
                        sendAtMatches(search.sendAt())
                )
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SlackMessageResponse> content = results.getResults().stream()
                .map(SlackMessageResponse::toResponse)
                .collect(Collectors.toList());

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression slackIdMatches(String slackId) {
        return slackId != null ? slackMessage.slackId.eq(slackId) : null;
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? slackMessage.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? slackMessage.content.containsIgnoreCase(content) : null;
    }

    private BooleanExpression sendAtMatches(Timestamp sendAt) {
        return sendAt != null ? slackMessage.sendAt.eq(sendAt) : null;
    }

    // 결과 순서
    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt":
                        orders.add(new OrderSpecifier<>(direction, slackMessage.createdAt));
                        break;
                    case "slackId":
                        orders.add(new OrderSpecifier<>(direction, slackMessage.slackId));
                        break;
                    case "title":
                        orders.add(new OrderSpecifier<>(direction, slackMessage.title));
                        break;
                }
            }
        }
        return orders;
    }
}
