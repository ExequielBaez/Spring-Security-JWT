package com.jwt.springsecurity.controller;

import com.jwt.springsecurity.dto.request.UserDto;
import com.jwt.springsecurity.dto.response.RegisteredUserDto;
import com.jwt.springsecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> registerOne(@RequestBody @Valid UserDto userDto){

        RegisteredUserDto registeredUser = authenticationService.registerOneUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

}
