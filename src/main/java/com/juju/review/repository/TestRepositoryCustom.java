package com.juju.review.repository;

import com.juju.review.model.TestEntity;

import java.util.List;

public interface TestRepositoryCustom {

    public List<TestEntity> findAllByNameQuerydsl(String name);
}
