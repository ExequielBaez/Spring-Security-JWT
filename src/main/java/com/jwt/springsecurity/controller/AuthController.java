package com.jwt.springsecurity.controller;

import com.jwt.springsecurity.dto.request.AuthenticationRequest;
import com.jwt.springsecurity.dto.response.AuthenticationResponse;
import com.jwt.springsecurity.persistence.entity.UserEntity;
import com.jwt.springsecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){

        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest){

        AuthenticationResponse authResponse = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR','COSTUMER')")
    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findMyProfile(){
        UserEntity userEntity = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(userEntity);
    }
}
