package com.jwt.springsecurity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
@Data
public class CategoryDto implements Serializable {
    @NotBlank
    private String name;
}
