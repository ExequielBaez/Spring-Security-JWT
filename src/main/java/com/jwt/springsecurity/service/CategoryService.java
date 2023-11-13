package com.jwt.springsecurity.service;

import com.jwt.springsecurity.dto.request.CategoryDto;
import com.jwt.springsecurity.persistence.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<CategoryEntity> findAll(Pageable pageable);

    Optional<CategoryEntity> findCategoryById(Long idCategory);

    CategoryEntity createCategory(CategoryDto categoryDto);

    CategoryEntity disabledOneById(Long idCategory);
}
