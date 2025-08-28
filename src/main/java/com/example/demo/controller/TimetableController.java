package com.example.demo.controller;

import com.example.demo.service.UsersService;
import com.example.demo.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

// This annotation tells Spring that this class is a REST controller
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class TimetableController {

    @Autowired
    private UsersService usersService;

    // A simple endpoint to test that the controller is working
    @GetMapping("/")
    public String welcome() {
        return "Hello World!";
    }

    @PostMapping("/login")
    public ResponseEntity<String> postLogin(@RequestBody Users user)
    {
        Users loginUser = usersService.authentication(user);
        if(loginUser != null)
        {
            return ResponseEntity.ok("Login successful");
        }
        else
        {
            return ResponseEntity.status(401).body("Login failed");
        }

    }
}
