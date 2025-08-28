package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;

@Service
public class UsersService {
    private final UsersRepository usersRepo;
    
    @Autowired
    public UsersService(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public Users authentication(Users user)
    {
        System.out.println("Received Email: " + user.getEmail());
        System.out.println("Received Password: " + user.getPassword());
        
        //for testing purposes only
        if("test@gmail.com".equals(user.getEmail()) && "password123".equals(user.getPassword()))
        {
            return user;
        }
    
        return null;
    }

    // public boolean usernamePassValidation(Users user)
    // {
    //     //validate username and password
    //     if(user.getUsername()==null || user.getUsername().isBlank() 
    //             || user.getUsername().length() > 255)
    //     {
    //         return false;
    //     }
    //     else if(user.getPassword().length() < 4)
    //     {
    //         return false;
    //     }
    //     return true;
    // }

    // public boolean userExists(String username)
    // {
    //     //validate user exists in database
    //     if(usersRepo.findByUsername(username)!=null)
    //     {
    //         return true;
    //     }
    //     return false;

    // }
}
