package com.dipak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/welcome")
    public String getWelcome(){
        return "Welcome to Dipak Universe";
    }
}
