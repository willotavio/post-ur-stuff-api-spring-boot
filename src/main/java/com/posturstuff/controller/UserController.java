package com.posturstuff.controller;

import com.posturstuff.dto.users.UserLoginDto;
import com.posturstuff.dto.users.UserRegisterDto;
import com.posturstuff.dto.users.UserUpdateDto;
import com.posturstuff.dto.users.UserViewDto;
import com.posturstuff.model.UserPrincipal;
import com.posturstuff.service.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<String> token = userService.verify(user);
        if(token.isEmpty()) {
            responseBody.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
        else {
            ResponseCookie cookie = ResponseCookie.from("jwt", token.get())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .sameSite("Lax")
                    .maxAge(3600 * 24 * 7)
                    .build();
            ResponseCookie clientCookie = ResponseCookie.from("isLoggedIn", "true")
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .sameSite("Lax")
                    .maxAge(3600 * 24 * 7)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString(), clientCookie.toString())
                    .build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(0)
                .build();
        ResponseCookie clientCookie = ResponseCookie.from("isLoggedIn", "")
                .httpOnly(false)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(0)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString(), clientCookie.toString())
                .build();
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
