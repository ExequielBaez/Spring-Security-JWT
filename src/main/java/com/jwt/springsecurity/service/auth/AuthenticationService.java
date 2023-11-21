package com.jwt.springsecurity.service.auth;

import com.jwt.springsecurity.dto.request.AuthenticationRequest;
import com.jwt.springsecurity.dto.request.UserDto;
import com.jwt.springsecurity.dto.response.AuthenticationResponse;
import com.jwt.springsecurity.dto.response.RegisteredUserDto;
import com.jwt.springsecurity.exception.ObjectNotFoundException;
import com.jwt.springsecurity.persistence.entity.UserEntity;
import com.jwt.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserName(), authenticationRequest.getPassword());

            authenticationManager.authenticate(authentication);

            UserDetails user = userService.findOneByUserName(authenticationRequest.getUserName()).get();

            String jwt = jwtService.generateToken(user, generateExtraClaims((UserEntity) user));

            AuthenticationResponse authResponse = new AuthenticationResponse();
            authResponse.setJwt(jwt);

            return authResponse;
    }

    public boolean validateToken(String jwt) {

        try {
            jwtService.extractUserName(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public UserEntity findLoggedInUser() {
        Authentication auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        //auth instanceof UsernamePasswordAuthenticationToken authToken) {//aca esta parseando
            //si auth es una instancia de usernamepassatuhtoken
            //parsea auth a auhtToken
            String userName = (String) auth.getPrincipal();

            return  userService.findOneByUserName(userName)
                    .orElseThrow( () -> new ObjectNotFoundException("User not found. Username: " +userName));
        }

}
