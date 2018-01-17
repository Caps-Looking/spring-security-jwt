package com.example.demo.controllers;

import com.example.demo.models.entities.ApplicationUser;
import com.example.demo.models.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("v1")
public class UserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("users/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        ApplicationUser userDB = applicationUserRepository.findByUsername(user.getUsername());
        if (userDB == null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            applicationUserRepository.save(user);
        } else {
            throw new RuntimeException("User already exists");
        }
    }

    @PostMapping("users/login")
    public String login(@RequestBody ApplicationUser user) {
        ApplicationUser userDTO = new ApplicationUser();
        userDTO.builder().username(user.getUsername()).password(user.getPassword()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8080/login", userDTO, String.class);
    }

}
