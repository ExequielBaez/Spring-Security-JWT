package com.jwt.springsecurity.service.impl;

import com.jwt.springsecurity.dto.request.CategoryDto;
import com.jwt.springsecurity.exception.ObjectNotFoundException;
import com.jwt.springsecurity.persistence.entity.CategoryEntity;
import com.jwt.springsecurity.persistence.repository.CategoryRepository;
import com.jwt.springsecurity.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Page<CategoryEntity> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<CategoryEntity> findCategoryById(Long idCategory) {
        return categoryRepository.findById(idCategory);
    }

    @Override
    public CategoryEntity createCategory(CategoryDto categoryDto) {

        //aca mejorar con mappers
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryDto.getName());
        category.setStatus(CategoryEntity.CategoryStatus.ENABLED);

        return categoryRepository.save(category);
    }

    @Override
    public CategoryEntity disabledOneById(Long idCategory) {
        CategoryEntity category = categoryRepository.findById(idCategory)
                .orElseThrow( () -> new ObjectNotFoundException("Category not found with id " + idCategory));

                category.setStatus(CategoryEntity.CategoryStatus.DISABLED);

        return categoryRepository.save(category);
    }
}
