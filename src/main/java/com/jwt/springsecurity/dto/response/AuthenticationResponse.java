package com.jwt.springsecurity.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class AuthenticationResponse implements Serializable {

    private String jwt;

}
