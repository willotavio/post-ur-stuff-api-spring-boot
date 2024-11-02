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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posturstuff-api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> getAll(@RequestBody @Valid UserLoginDto user) {
        HttpStatus responseStatus = null;
        Map<String, String> responseBody = new HashMap<>();
        Optional<String> token = userService.verify(user);
        if(token.isEmpty()) {
            responseStatus = HttpStatus.UNAUTHORIZED;
            responseBody.put("message", "Invalid credentials");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("token", token.get());
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") String id) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<UserViewDto> user = userService.getById(id);
        if(user.isEmpty()) {
            responseStatus = HttpStatus.NOT_FOUND;
            responseBody.put("message", "User not found");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("user", user);
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<UserViewDto> user = userService.deleteById(id);
        if(user.isEmpty()) {
            responseStatus = HttpStatus.NOT_FOUND;
            responseBody.put("message", "User not found");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("user", user);
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

}
