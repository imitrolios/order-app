package org.orderapi.user.controllers;

import org.orderapi.common.exception.ApplicationBadInputException;
import org.orderapi.user.controllers.dto.UserDto;
import org.orderapi.user.mappers.UserMapper;
import org.orderapi.user.model.User;
import org.orderapi.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final String USER_BY_ID_NOT_FOUND = "the user with the specific id does not exist";
    private final String USER_BY_USERNAME_NOT_FOUND = "the user with the specific email does not exist";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto fetchUser(@PathVariable UUID userId) {
        User user =
                userRepository.findById(userId).orElseThrow(
                        () -> new ApplicationBadInputException(USER_BY_ID_NOT_FOUND));
        return userMapper.toDto(user);
    }

    @GetMapping(path = "/search/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserByEmail(@PathVariable String email) {
        User user =
                userRepository.findByEmail(email).orElseThrow(
                        () -> new ApplicationBadInputException(USER_BY_USERNAME_NOT_FOUND));
        return userMapper.toDto(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userMapper.toDto(userRepository.saveAndFlush(userMapper.toDomain(userDto)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto submitUser(@RequestBody UserDto userDto) {

        return userMapper.toDto(userRepository.saveAndFlush(userMapper.toDomain(userDto)));
    }
}
