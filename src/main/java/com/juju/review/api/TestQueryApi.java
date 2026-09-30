package com.juju.review.api;

import com.juju.review.model.TestEntity;
import com.juju.review.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestQueryApi {
    private final TestService testService;

    // JPA 방법
    @GetMapping("/test/query/jpa")
    public List<TestEntity> queryJpa(
            @RequestParam("name") String name
    ) {
        return testService.findAllByNameJPA(name);
    }

    // Querydsl 방법
    @GetMapping("/test/query/querydsl")
    public List<TestEntity> queryQuerydsl() {
        return testService.findAllByNameByQuerydsl("juju");
    }
}
