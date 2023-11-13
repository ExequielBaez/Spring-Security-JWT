package com.jwt.springsecurity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserDto implements Serializable {

    @Size(min = 4)
    private String name;

    private String userName;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String repeatedPassword;
}
