package com.asmeduardo.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asmeduardo.dscatalog.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
