package com.posturstuff.controller;

import com.posturstuff.dto.users.*;
import com.posturstuff.model.UserPrincipal;
import com.posturstuff.service.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto user) {
        Map<String, String> responseBody = new HashMap<>();
        Optional<String> token = userService.login(user);
        if(token.isEmpty()) {
            responseBody.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
        else {
            responseBody.put("token", token.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseBody);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> register(@RequestBody @Valid UserRegisterDto user) {
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

    @GetMapping("/username/{username}")
    public ResponseEntity<Object> getByUsername(@PathVariable("username") String username) {
    	HttpStatus responseStatus = null;
	Map<String, Object> responseBody = new HashMap<>();
	Optional<UserViewDto> user = userService.getByUsername(username);
	if(user.isEmpty()) {
	    responseStatus = HttpStatus.NOT_FOUND;
	    responseBody.put("message", "User not found");	
	}
	else {
	    responseStatus = HttpStatus.OK;
	    responseBody.put("user", user.get());
	}
	return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getOwn(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<UserViewDto> user = userService.getById(userPrincipal.getId());
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

    @PatchMapping
    public ResponseEntity<Object> update(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<UserViewDto> user = userService.update(userPrincipal.getId(), userUpdateDto);
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

    @PatchMapping("/password")
    public ResponseEntity<Object> updatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PasswordUpdateDto passwordUpdateDto) {
        Map<String, Object> responseBody = new HashMap<>();
        Optional<UserViewDto> user = userService.updatePassword(userPrincipal.getId(), passwordUpdateDto);
        responseBody.put("user", user.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<UserViewDto> user = userService.deleteById(userPrincipal.getId());
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
