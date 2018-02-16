package com.newspaper.controller;

import com.newspaper.model.User;
import com.newspaper.model.UserSession;
import com.newspaper.model.web.LoginRequest;
import com.newspaper.model.web.RegistrationRequest;
import com.newspaper.repository.UserRepository;
import com.newspaper.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @PostMapping("/register")
    public UserSession register(@RequestBody RegistrationRequest request) {
        User user = userRepository.createUser(request);
        return userSessionRepository.create(
                UUID.randomUUID().toString(),
                user
        );
    }

    @PostMapping("/login")
    public UserSession login(@RequestBody LoginRequest request) {
        User user = userRepository.getByUsernameAndPassword(request);
        if (user == null) {
            throw new UsernameNotFoundException("Login unabled, user not found");
        }
        return userSessionRepository.create(
                UUID.randomUUID().toString(),
                user
        );
    }

    @PutMapping("/logout")
    public void logout(@RequestHeader("Authorization") String sessionId) {
        userSessionRepository.invalidateSession(sessionId);
    }

    @GetMapping("/get")
    public List<User> getAll() {
        return userRepository.getAllUsers();
    }

    @GetMapping("/getInfo/{userId}")
    public User getUserInfo(@PathVariable(value = "userId") Integer userId) {
        return userRepository.getById(userId);
    }

    @GetMapping("/promote/{username}")
    public User promoteUser(@PathVariable("username") String username){
        return userRepository.promoteUser(username);
    }

    @GetMapping("/demote/{username}")
    public User demoteUser(@PathVariable("username") String username){
        return userRepository.demoteUser(username);
    }


}