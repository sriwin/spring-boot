package com.sriwin.rest.greetings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class GreetingsRestController {
    @GetMapping("/greet")
    public String greetUser() {
        return "Hello - " + UUID.randomUUID().toString();
    }
}