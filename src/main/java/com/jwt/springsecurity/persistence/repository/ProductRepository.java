package com.jwt.springsecurity.persistence.repository;

import com.jwt.springsecurity.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
