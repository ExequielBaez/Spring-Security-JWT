package com.jwt.springsecurity.service;

import com.jwt.springsecurity.dto.ProductDto;
import com.jwt.springsecurity.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<ProductEntity> findAll(Pageable pageable);

    Optional<ProductEntity> findProductById(Long idProduct);

    ProductEntity createProduct(ProductDto productDto);

    ProductEntity disabledOneById(Long idProduct);
}
