package com.jwt.springsecurity.dto.response;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError implements Serializable {

    private String backendMessage;

    private String message;

    private String url;

    private String method;

    private List<String> subMessages;

    private LocalDateTime timeStamp;
}
