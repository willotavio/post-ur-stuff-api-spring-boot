package com.posturstuff.service;

import com.posturstuff.dto.users.UserLoginDto;
import com.posturstuff.dto.users.UserRegisterDto;
import com.posturstuff.dto.users.UserUpdateDto;
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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

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
                null,
                null
        ));
        return userMapper.userToUserViewDto(newUser);
    }

    public Optional<String> verify(UserLoginDto user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));
        if(authentication.isAuthenticated()) {
            Users authenticatedUser = userRepository.findByUsername(user.username());
            return Optional.of(jwtService.generateToken(authenticatedUser.getId()));
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

    public Optional<UserViewDto> update(String id, UserUpdateDto userUpdateDto) {
        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return Optional.empty();
        }
        if(userUpdateDto.username() != null) {
            user.get().setUsername(userUpdateDto.username());
        }
        if(userUpdateDto.displayName() != null) {
            user.get().setDisplayName(userUpdateDto.displayName());
        }
        if(userUpdateDto.email() != null) {
            user.get().setEmail(userUpdateDto.email());
        }
        if(userUpdateDto.password() != null && userUpdateDto.passwordConfirmation() != null
            && userUpdateDto.password().equals(userUpdateDto.passwordConfirmation())) {
            user.get().setPassword(passwordEncoder.encode(userUpdateDto.password()));
        }
        if(userUpdateDto.birthDate() != null) {
            user.get().setBirthDate(userUpdateDto.birthDate());
        }
        if(userUpdateDto.accountVisibility() > 0
                && AccountVisibility.valueOf(userUpdateDto.accountVisibility()).getCode() > 0) {
            user.get().setAccountVisibility(AccountVisibility.valueOf(userUpdateDto.accountVisibility()));
        }
        if(userUpdateDto.profilePicture() != null) {
            user.get().setProfilePicture(userUpdateDto.profilePicture());
        }
        if(userUpdateDto.profileCover() != null) {
            user.get().setProfileCover(userUpdateDto.profileCover());
        }
        userRepository.save(user.get());
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
