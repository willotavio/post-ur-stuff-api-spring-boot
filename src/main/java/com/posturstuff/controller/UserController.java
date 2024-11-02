package com.posturstuff.controller;

import com.posturstuff.dto.users.UserLoginDto;
import com.posturstuff.dto.users.UserRegisterDto;
import com.posturstuff.dto.users.UserViewDto;
import com.posturstuff.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posturstuff-api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String getAll(@RequestBody @Valid UserLoginDto user) {
        return userService.verify(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> add(@RequestBody @Valid UserRegisterDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

    @GetMapping
    public ResponseEntity<List<UserViewDto>> getAll() {
        List<UserViewDto> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
