package com.jwt.springsecurity.controller;


import com.jwt.springsecurity.dto.request.ProductDto;
import com.jwt.springsecurity.persistence.entity.ProductEntity;
import com.jwt.springsecurity.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<Page<ProductEntity>> findAll(Pageable pageable){

        Page<ProductEntity> productsPage = productService.findAll(pageable);

        if(productsPage.hasContent()){

            return ResponseEntity.ok(productsPage);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @GetMapping("/{idProduct}")
    public ResponseEntity<?> findProductById(@PathVariable Long idProduct){

        Optional<ProductEntity> product = productService.findProductById(idProduct);

        if(product.isPresent()){

            return ResponseEntity.ok(product.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDto productDto){

        ProductEntity product = productService.createProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{idProduct}/disabled")
    public ResponseEntity<?> disabledOneById(@PathVariable Long idProduct){

        ProductEntity product = productService.disabledOneById(idProduct);

        return ResponseEntity.ok(product);
    }
}
