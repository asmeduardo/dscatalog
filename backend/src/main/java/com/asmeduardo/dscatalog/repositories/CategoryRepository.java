package com.asmeduardo.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asmeduardo.dscatalog.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
