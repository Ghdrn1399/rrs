package com.juju.review.service;

import com.juju.review.model.TestEntity;
import com.juju.review.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;

    public void create(String name, Integer age) {
        TestEntity testEntity = new TestEntity(name, age);
        testRepository.save(testEntity);
    }

    public void delete(Long id) {
        TestEntity testEntity = testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다: " + id));
        testRepository.delete(testEntity);
    }

    @Transactional
    public void update(Long id, String name, Integer age) {
        TestEntity testEntity = testRepository.findById(id).orElseThrow();
        testEntity.changeNameAndAge(name, age);
        testRepository.save(testEntity);
    }

    public List<TestEntity> findAllByNameJPA(String name) {
        return testRepository.findAllByName(name);
    }

    public List<TestEntity> findAllByNameByQuerydsl(String name) {
        return testRepository.findAllByNameQuerydsl(name);
    }
}
