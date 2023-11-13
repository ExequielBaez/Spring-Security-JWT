package com.jwt.springsecurity.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisteredUserDto implements Serializable {

    private Long idUser;

    private String userName;

    private String name;

    private String role;

    private String tokenJwt;

}
