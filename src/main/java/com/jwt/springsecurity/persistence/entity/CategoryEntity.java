package com.jwt.springsecurity.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CategoryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;
    private String name;
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    public static enum CategoryStatus{
        ENABLED, DISABLED;
    }

}
