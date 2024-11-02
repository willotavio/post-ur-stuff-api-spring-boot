package com.posturstuff.service;

import com.posturstuff.dto.users.UserLoginDto;
import com.posturstuff.dto.users.UserRegisterDto;
import com.posturstuff.dto.users.UserViewDto;
import com.posturstuff.enums.AccountVisibility;
import com.posturstuff.mapper.UserMapper;
import com.posturstuff.model.Users;
import com.posturstuff.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserViewDto register(UserRegisterDto user) {
        Users newUser = userRepository.save(new Users(
                user.username(),
                user.displayName(),
                user.email(),
                passwordEncoder.encode(user.password()),
                LocalDate.now(),
                user.birthDate() != null ? user.birthDate() : null,
                AccountVisibility.PUBLIC,
                null,
                null
        ));
        return userMapper.userToUserViewDto(newUser);
    }

    public Optional<String> verify(UserLoginDto user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));
        if(authentication.isAuthenticated()) {
            return Optional.of(jwtService.generateToken(user.username()));
        }
        return Optional.empty();
    }

    public List<UserViewDto> getAll() {
        List<Users> users = userRepository.findAll();
        List<UserViewDto> usersDto = new ArrayList<>();
        for(Users user : users) {
            usersDto.add(userMapper.userToUserViewDto(user));
        }
        return usersDto;
    }

    public Optional<UserViewDto> getById(String id) {
        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userMapper.userToUserViewDto(user.get()));
    }

    public Optional<UserViewDto> deleteById(String id) {
        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return Optional.empty();
        }
        userRepository.deleteById(id);
        return Optional.of(userMapper.userToUserViewDto(user.get()));
    }

}
