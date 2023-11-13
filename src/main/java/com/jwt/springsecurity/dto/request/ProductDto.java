package com.jwt.springsecurity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class ProductDto implements Serializable {

    @NotBlank
    private String name;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero ")
    private BigDecimal price;

    @Min(value =1)
    private Long idCategory;
}
