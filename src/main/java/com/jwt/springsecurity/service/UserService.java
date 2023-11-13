package com.jwt.springsecurity.service;

import com.jwt.springsecurity.dto.request.UserDto;
import com.jwt.springsecurity.persistence.entity.UserEntity;

public interface UserService {
    public UserEntity registerOneUser(UserDto userDto);
}
