package com.example.demo.configs.security.controller;

import com.example.demo.models.entities.ApplicationUser;
import com.example.demo.models.repositories.ApplicationUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Api(description = "User management API")
@RestController
@RequestMapping("v1")
public class UserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @ApiOperation(value = "Sign up to the application")
    @PostMapping("users/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        if (user.getPassword().length() < 8)
            throw new RuntimeException("The password needs to have at least 8 characters");
        ApplicationUser userDB = applicationUserRepository.findByUsername(user.getUsername());
        if (userDB == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            applicationUserRepository.save(user);
        } else {
            throw new RuntimeException("User already exists");
        }
    }

    @ApiOperation(value = "Return the token", response = ApplicationUser.class)
    @PostMapping("users/login")
    public String login(@RequestBody ApplicationUser user) {
        ApplicationUser userDTO = new ApplicationUser();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8080/login", userDTO, String.class);
    }

}
