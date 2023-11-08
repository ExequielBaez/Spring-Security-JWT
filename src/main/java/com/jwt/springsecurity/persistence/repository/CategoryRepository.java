package com.jwt.springsecurity.persistence.repository;

import com.jwt.springsecurity.persistence.entity.CategoryEntity;
import com.jwt.springsecurity.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
