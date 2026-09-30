package com.juju.review.repository;

import com.juju.review.model.QTestEntity;
import com.juju.review.model.TestEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TestRepositoryImpl implements TestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TestEntity> findAllByNameQuerydsl(String name) {
        return queryFactory.selectFrom(QTestEntity.testEntity).fetch();
    }
}
