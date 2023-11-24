package com.jwt.springsecurity.controller;


import com.jwt.springsecurity.dto.request.CategoryDto;
import com.jwt.springsecurity.persistence.entity.CategoryEntity;
import com.jwt.springsecurity.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')")
    @GetMapping
    public ResponseEntity<Page<CategoryEntity>> findAll(Pageable pageable){

        Page<CategoryEntity> categoryPage = categoryService.findAll(pageable);

        if(categoryPage.hasContent()){

            return ResponseEntity.ok(categoryPage);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('READ_ONE_CATEGORY'")
    @GetMapping("/{idCategory}")
    public ResponseEntity<?> findCategorybyId(@PathVariable Long idCategory){

        Optional<CategoryEntity> category = categoryService.findCategoryById(idCategory);

        if(category.isPresent()){

            return ResponseEntity.ok(category.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDto categoryDto){

        CategoryEntity category = categoryService.createCategory(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @PreAuthorize("hasAuthority('DISABLE_ONE_CATEGORY')")
    @PutMapping("/{idCategory}/disabled")
    public ResponseEntity<?> disabledOneById(@PathVariable Long idCategory){

        CategoryEntity category = categoryService.disabledOneById(idCategory);

        return ResponseEntity.ok(category);
    }
}
