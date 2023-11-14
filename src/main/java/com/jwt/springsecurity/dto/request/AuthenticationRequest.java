package com.jwt.springsecurity.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class AuthenticationRequest implements Serializable {

    private String userName;

    private String password;
}
