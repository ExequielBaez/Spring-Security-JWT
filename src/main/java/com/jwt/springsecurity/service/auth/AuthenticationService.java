package com.jwt.springsecurity.service.auth;

import com.jwt.springsecurity.dto.request.UserDto;
import com.jwt.springsecurity.dto.response.RegisteredUserDto;
import com.jwt.springsecurity.persistence.entity.UserEntity;
import com.jwt.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public RegisteredUserDto registerOneUser(UserDto userDto) {

        UserEntity userEntity = userService.registerOneUser(userDto);

        RegisteredUserDto registeredUserDto = new RegisteredUserDto();
        registeredUserDto.setIdUser(userEntity.getIdUser());
        registeredUserDto.setName(userEntity.getName());
        registeredUserDto.setUserName(userEntity.getUsername());
        registeredUserDto.setRole(userEntity.getRole().name());

        String jwt = jwtService.generateToken(userEntity, generateExtraClaims(userEntity));
        registeredUserDto.setTokenJwt(jwt);

        return registeredUserDto;
    }

    private Map<String, Object> generateExtraClaims(UserEntity userEntity) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", userEntity.getName());
        extraClaims.put("role", userEntity.getRole().name());
        extraClaims.put("authorities", userEntity.getAuthorities());

        return extraClaims;
    }
}
