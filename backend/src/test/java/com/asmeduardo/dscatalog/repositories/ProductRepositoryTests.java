package com.asmeduardo.dscatalog.repositories;

import com.asmeduardo.dscatalog.models.Product;
import com.asmeduardo.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 30L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);

        product = productRepository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteProductShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existingId);

        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteProductShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            productRepository.deleteById(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnANonEmptyOptionalWhenIdExists() {
        Optional<Product> product = productRepository.findById(existingId);

        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(existingId, product.get().getId());
    }

    @Test
    public void findByIdShouldReturnAnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Product> product = productRepository.findById(nonExistingId);

        Assertions.assertTrue(product.isEmpty());
    }
}
