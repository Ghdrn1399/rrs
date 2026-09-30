package com.juju.review.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLombokApi {


    @GetMapping("/test/lombok")
    public TestlombokResponseBody testLombok() {
        return new TestlombokResponseBody("backend", 30);
    }

    @Getter
    @AllArgsConstructor
    public static class TestlombokResponseBody{
        String name;
        Integer age;
    }
}
