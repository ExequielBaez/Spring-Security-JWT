package com.jwt.springsecurity.service.impl;

import com.jwt.springsecurity.dto.ProductDto;
import com.jwt.springsecurity.exception.ObjectNotFoundException;
import com.jwt.springsecurity.persistence.entity.CategoryEntity;
import com.jwt.springsecurity.persistence.entity.ProductEntity;
import com.jwt.springsecurity.persistence.repository.ProductRepository;
import com.jwt.springsecurity.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductEntity> findProductById(Long idProduct) {
        return productRepository.findById(idProduct);
    }

    @Override
    public ProductEntity createProduct(ProductDto productDto) {

        //aca mejorar con mappers
        ProductEntity product = new ProductEntity();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStatus(ProductEntity.ProductStatus.ENABLED);

        CategoryEntity category = new CategoryEntity();
        category.setIdCategory(productDto.getIdCategory());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public ProductEntity disabledOneById(Long idProduct) {
        ProductEntity product = productRepository.findById(idProduct)
                .orElseThrow( () -> new ObjectNotFoundException("Product not found with id " + idProduct));

                product.setStatus(ProductEntity.ProductStatus.DISABLED);

        return productRepository.save(product);
    }
}
