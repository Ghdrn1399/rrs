package com.juju.review.api;


import org.springframework.web.bind.annotation.*;

@RestController
public class TestApi {

    @GetMapping("/hello/world")
    public String helloWorld() {
        return "[GET] hello world";
    }

    @PostMapping("/hello/world")
    public String postHelloWorld() {
        return "[POST] hello world";
    }

    @PutMapping("/hello/world")
    public String putHelloWorld() {
        return "[PUT] hello world";
    }

    @DeleteMapping("/hello/world")
    public String deleteHelloWorld() {
        return "[DELETE] hello world";
    }
}
