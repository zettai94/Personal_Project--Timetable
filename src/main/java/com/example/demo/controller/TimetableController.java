package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// This annotation tells Spring that this class is a REST controller
@RestController
public class TimetableController {

    // A simple endpoint to test that the controller is working
    @GetMapping("/")
    public String welcome() {
        return "Hello World!";
    }
}
