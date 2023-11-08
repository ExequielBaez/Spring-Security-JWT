package com.jwt.springsecurity.controller;


import com.jwt.springsecurity.dto.CategoryDto;
import com.jwt.springsecurity.dto.ProductDto;
import com.jwt.springsecurity.persistence.entity.CategoryEntity;
import com.jwt.springsecurity.persistence.entity.ProductEntity;
import com.jwt.springsecurity.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryEntity>> findAll(Pageable pageable){

        Page<CategoryEntity> categoryPage = categoryService.findAll(pageable);

        if(categoryPage.hasContent()){

            return ResponseEntity.ok(categoryPage);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<?> findCategorybyId(@PathVariable Long idCategory){

        Optional<CategoryEntity> category = categoryService.findProductbyId(idCategory);

        if(category.isPresent()){

            return ResponseEntity.ok(category.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDto categoryDto){

        CategoryEntity category = categoryService.createCategory(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @PutMapping("/{idCategory}/disabled")
    public ResponseEntity<?> disabledOneById(@PathVariable Long idCategory){

        CategoryEntity category = categoryService.disabledOneById(idCategory);

        return ResponseEntity.ok(category);
    }
}
