package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DataSqlTest 
{
    @Autowired
    private UsersRepository usersRepository;

    Users admin;

    @BeforeEach
    public void setUp() {
        admin = new Users();
        admin.setUsername("admin");
        admin.setPassword("password123");
        admin.setEmail("test@gmail.com");
        admin.setStatus(true);
        usersRepository.save(admin);
    }

    @AfterEach
    public void tearDown() {
        usersRepository.deleteAll(); // Clean up after each test
    }

    @Test
    public void adminExists(){
        Users admin = usersRepository.findByUsername("admin");
        assertTrue(admin.getUsername().equals("admin"));
        assertTrue(admin.getPassword().equals("password123"));
    }
    
    
}
