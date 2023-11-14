package com.jwt.springsecurity.service.impl;

import com.jwt.springsecurity.dto.request.UserDto;
import com.jwt.springsecurity.exception.InvalidPasswordException;
import com.jwt.springsecurity.persistence.entity.UserEntity;
import com.jwt.springsecurity.persistence.repository.UserRepository;
import com.jwt.springsecurity.persistence.util.Role;
import com.jwt.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserEntity registerOneUser(UserDto userDto) {

        validatePassword(userDto);

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setName(userDto.getName());
        userEntity.setUserName(userDto.getUserName());

        userEntity.setRole(Role.COSTUMER);

        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findOneByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    private void validatePassword(UserDto userDto) {

        if(!StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }

        if(!userDto.getPassword().equals(userDto.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }
    }
}
