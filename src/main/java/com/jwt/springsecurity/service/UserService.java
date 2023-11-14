package com.jwt.springsecurity.service;

import com.jwt.springsecurity.dto.request.UserDto;
import com.jwt.springsecurity.persistence.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    public UserEntity registerOneUser(UserDto userDto);

    public Optional<UserEntity> findOneByUserName(String userName);
}
