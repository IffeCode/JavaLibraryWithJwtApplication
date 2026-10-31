package org.example.javalibrarywithjwt.controller;

import jakarta.validation.Valid;
import org.example.javalibrarywithjwt.model.LoginRequest;
import org.example.javalibrarywithjwt.model.LoginResponse;
import org.example.javalibrarywithjwt.model.User;
import org.example.javalibrarywithjwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Registrera en användare
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    //Användaren kan logga in
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }


}
